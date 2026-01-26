package com.hexarcano.dlrecord.devicetype.infrastructure.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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

@SpringBootTest
@Import({ WebSecurityConfig.class, JwtAuthFilter.class })
class DeviceTypeControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@MockitoBean
	private DeviceTypeService deviceTypeService;

	@MockitoBean
	private JwtService jwtService;

	@MockitoBean
	private UserDetailsService userDetailsService;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}

	@Test
	@WithMockUser(username = "user")
	void createDeviceType_ShouldReturn201_WhenUserIsAuthenticated() throws Exception {
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
	void createDeviceType_ShouldReturn401_WhenUserIsNotAuthenticated() throws Exception {
		CreateDeviceTypeRequest request = new CreateDeviceTypeRequest("Smartphone");

		mockMvc.perform(post("/api/v1/device-types")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(username = "user")
	void createDeviceType_ShouldReturn400_WhenDataIsInvalid() throws Exception {
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
	void getAllDeviceTypes_ShouldReturn200_WhenUserIsAuthenticated() throws Exception {
		DeviceType dt1 = new DeviceType("1", "Smartphone");
		DeviceType dt2 = new DeviceType("2", "Tablet");

		when(deviceTypeService.findAll()).thenReturn(Arrays.asList(dt1, dt2));

		mockMvc.perform(get("/api/v1/device-types"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(2))
				.andExpect(jsonPath("$[0].name").value("Smartphone"));
	}

	@Test
	void getAllDeviceTypes_ShouldReturn401_WhenUserIsNotAuthenticated() throws Exception {
		mockMvc.perform(get("/api/v1/device-types"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(username = "user")
	void findDeviceTypeById_ShouldReturn200_WhenFound() throws Exception {
		DeviceType dt = new DeviceType("1", "Smartphone");

		when(deviceTypeService.findById("1")).thenReturn(Optional.of(dt));

		mockMvc.perform(get("/api/v1/device-types/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Smartphone"));
	}

	@Test
	@WithMockUser(username = "user")
	void findDeviceTypeById_ShouldReturn404_WhenNotFound() throws Exception {
		when(deviceTypeService.findById("1")).thenReturn(Optional.empty());

		mockMvc.perform(get("/api/v1/device-types/1"))
				.andExpect(status().isNotFound());
	}

	@Test
	void findDeviceTypeById_ShouldReturn401_WhenUserIsNotAuthenticated() throws Exception {
		mockMvc.perform(get("/api/v1/device-types/1"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(username = "user")
	void updateDeviceType_ShouldReturn200_WhenSuccessful() throws Exception {
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
	void updateDeviceType_ShouldReturn404_WhenNotFound() throws Exception {
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
	void updateDeviceType_ShouldReturn401_WhenUserIsNotAuthenticated() throws Exception {
		UpdateDeviceTypeRequest request = new UpdateDeviceTypeRequest("Updated");

		mockMvc.perform(put("/api/v1/device-types/1")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(username = "user")
	void deleteDeviceType_ShouldReturn204_WhenSuccessful() throws Exception {
		when(deviceTypeService.deleteDeviceType("1")).thenReturn(true);

		mockMvc.perform(delete("/api/v1/device-types/1")
				.with(csrf()))
				.andExpect(status().isNoContent());
	}

	@Test
	@WithMockUser(username = "user")
	void deleteDeviceType_ShouldReturn404_WhenNotFound() throws Exception {
		when(deviceTypeService.deleteDeviceType("1")).thenReturn(false);

		mockMvc.perform(delete("/api/v1/device-types/1")
				.with(csrf()))
				.andExpect(status().isNotFound());
	}

	@Test
	void deleteDeviceType_ShouldReturn401_WhenUserIsNotAuthenticated() throws Exception {
		mockMvc.perform(delete("/api/v1/device-types/1")
				.with(csrf()))
				.andExpect(status().isUnauthorized());
	}
}
