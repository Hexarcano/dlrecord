package com.hexarcano.dlrecord.devicetype.infrastructure.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.hexarcano.dlrecord.config.WebSecurityConfig;
import com.hexarcano.dlrecord.config.token.JwtAuthFilter;
import com.hexarcano.dlrecord.config.token.JwtService;
import com.hexarcano.dlrecord.devicetype.application.port.in.command.CreateDeviceTypeCommand;
import com.hexarcano.dlrecord.devicetype.application.port.in.command.UpdateDeviceTypeCommand;
import com.hexarcano.dlrecord.devicetype.application.service.DeviceTypeService;
import com.hexarcano.dlrecord.devicetype.domain.model.DeviceType;
import com.hexarcano.dlrecord.devicetype.infrastructure.controller.dto.CreateDeviceTypeRequest;
import com.hexarcano.dlrecord.devicetype.infrastructure.controller.dto.UpdateDeviceTypeRequest;

import tools.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = DeviceTypeController.class)
@Import({ WebSecurityConfig.class, JwtAuthFilter.class })
class DeviceTypeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private DeviceTypeService deviceTypeService;

	@MockitoBean
	private JwtService jwtService;

	@MockitoBean
	private UserDetailsService userDetailsService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@WithMockUser(username = "user")
	void shouldReturn201_WhenUserIsAuthenticated() throws Exception {
		CreateDeviceTypeRequest request = new CreateDeviceTypeRequest("Smartphone");
		DeviceType createdDeviceType = new DeviceType("uuid-123", "Smartphone");

		when(deviceTypeService.createDeviceType(any(CreateDeviceTypeCommand.class)))
				.thenReturn(createdDeviceType);

		mockMvc.perform(post("/api/v1/device-types")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.uuid").value("uuid-123"))
				.andExpect(jsonPath("$.name").value("Smartphone"));
	}

	@Test
	void shouldReturn401_WhenUserIsNotAuthenticated() throws Exception {
		CreateDeviceTypeRequest request = new CreateDeviceTypeRequest("Smartphone");

		mockMvc.perform(post("/api/v1/device-types")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(username = "user")
	void shouldReturn400_WhenDataIsInvalid() throws Exception {
		CreateDeviceTypeRequest request = new CreateDeviceTypeRequest("");

		when(deviceTypeService.createDeviceType(any(CreateDeviceTypeCommand.class)))
				.thenThrow(new IllegalArgumentException("Invalid name"));

		mockMvc.perform(post("/api/v1/device-types")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "user")
	void shouldReturn200_WhenGettingAllDeviceTypes() throws Exception {
		DeviceType dt1 = new DeviceType("1", "Smartphone");
		DeviceType dt2 = new DeviceType("2", "Tablet");

		when(deviceTypeService.findAll()).thenReturn(Arrays.asList(dt1, dt2));

		mockMvc.perform(get("/api/v1/device-types"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(2))
				.andExpect(jsonPath("$[0].name").value("Smartphone"));
	}

	@Test
	void shouldReturn401_WhenGettingAllDeviceTypesUnauthenticated() throws Exception {
		mockMvc.perform(get("/api/v1/device-types"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(username = "user")
	void shouldReturn200_WhenDeviceTypeIsFound() throws Exception {
		DeviceType dt = new DeviceType("1", "Smartphone");

		when(deviceTypeService.findById("1")).thenReturn(Optional.of(dt));

		mockMvc.perform(get("/api/v1/device-types/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Smartphone"));
	}

	@Test
	@WithMockUser(username = "user")
	void shouldReturn404_WhenDeviceTypeIsNotFound() throws Exception {
		when(deviceTypeService.findById("1")).thenReturn(Optional.empty());

		mockMvc.perform(get("/api/v1/device-types/1"))
				.andExpect(status().isNotFound());
	}

	@Test
	void shouldReturn401_WhenFindingDeviceTypeByIdUnauthenticated() throws Exception {
		mockMvc.perform(get("/api/v1/device-types/1"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(username = "user")
	void shouldReturn200_WhenUpdateDeviceTypeIsSuccessful() throws Exception {
		UpdateDeviceTypeRequest request = new UpdateDeviceTypeRequest("Smartphone Updated");
		DeviceType updatedDt = new DeviceType("1", "Smartphone Updated");

		when(deviceTypeService.updateDeviceType(eq("1"), any(UpdateDeviceTypeCommand.class)))
				.thenReturn(Optional.of(updatedDt));

		mockMvc.perform(put("/api/v1/device-types/1")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Smartphone Updated"));
	}

	@Test
	@WithMockUser(username = "user")
	void shouldReturn404_WhenUpdatingDeviceTypeThatDoesNotExist() throws Exception {
		UpdateDeviceTypeRequest request = new UpdateDeviceTypeRequest("Smartphone Updated");

		when(deviceTypeService.updateDeviceType(eq("1"), any(UpdateDeviceTypeCommand.class)))
				.thenReturn(Optional.empty());

		mockMvc.perform(put("/api/v1/device-types/1")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isNotFound());
	}

	@Test
	void shouldReturn401_WhenUpdatingDeviceTypeUnauthenticated() throws Exception {
		UpdateDeviceTypeRequest request = new UpdateDeviceTypeRequest("Updated");

		mockMvc.perform(put("/api/v1/device-types/1")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(username = "user")
	void shouldReturn204_WhenDeleteDeviceTypeIsSuccessful() throws Exception {
		when(deviceTypeService.deleteDeviceType("1")).thenReturn(true);

		mockMvc.perform(delete("/api/v1/device-types/1")
				.with(csrf()))
				.andExpect(status().isNoContent());
	}

	@Test
	@WithMockUser(username = "user")
	void shouldReturn404_WhenDeletingDeviceTypeThatDoesNotExist() throws Exception {
		when(deviceTypeService.deleteDeviceType("1")).thenReturn(false);

		mockMvc.perform(delete("/api/v1/device-types/1")
				.with(csrf()))
				.andExpect(status().isNotFound());
	}

	@Test
	void shouldReturn401_WhenDeletingDeviceTypeUnauthenticated() throws Exception {
		mockMvc.perform(delete("/api/v1/device-types/1")
				.with(csrf()))
				.andExpect(status().isUnauthorized());
	}
}
