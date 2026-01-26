package com.hexarcano.dlrecord.devicemodel.infrastructure.controller;

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

import com.hexarcano.dlrecord.brand.domain.model.Brand;
import com.hexarcano.dlrecord.config.WebSecurityConfig;
import com.hexarcano.dlrecord.config.token.JwtAuthFilter;
import com.hexarcano.dlrecord.config.token.JwtService;
import com.hexarcano.dlrecord.devicemodel.application.port.in.command.CreateDeviceModelCommand;
import com.hexarcano.dlrecord.devicemodel.application.port.in.command.UpdateDeviceModelCommand;
import com.hexarcano.dlrecord.devicemodel.application.service.DeviceModelService;
import com.hexarcano.dlrecord.devicemodel.domain.model.DeviceModel;
import com.hexarcano.dlrecord.devicemodel.infrastructure.controller.dto.CreateDeviceModelRequest;
import com.hexarcano.dlrecord.devicemodel.infrastructure.controller.dto.UpdateDeviceModelRequest;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@Import({ WebSecurityConfig.class, JwtAuthFilter.class })
class DeviceModelControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@MockitoBean
	private DeviceModelService deviceModelService;

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
	void createDeviceModel_ShouldReturn201_WhenUserIsAuthenticated() throws Exception {
		CreateDeviceModelRequest request = new CreateDeviceModelRequest("Galaxy S23", "brand-123");
		Brand brand = new Brand("brand-123", "Samsung");
		DeviceModel createdDeviceModel = new DeviceModel("uuid-123", "Galaxy S23", brand);

		when(deviceModelService.createDeviceModel(any(CreateDeviceModelCommand.class)))
				.thenReturn(createdDeviceModel);

		mockMvc.perform(post("/api/v1/device-models")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.uuid").value("uuid-123"))
				.andExpect(jsonPath("$.name").value("Galaxy S23"))
				.andExpect(jsonPath("$.brand.uuid").value("brand-123"));
	}

	@Test
	void createDeviceModel_ShouldReturn401_WhenUserIsNotAuthenticated() throws Exception {
		CreateDeviceModelRequest request = new CreateDeviceModelRequest("Galaxy S23", "brand-123");

		mockMvc.perform(post("/api/v1/device-models")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(username = "user")
	void createDeviceModel_ShouldReturn400_WhenDataIsInvalid() throws Exception {
		CreateDeviceModelRequest request = new CreateDeviceModelRequest("", "brand-123");

		when(deviceModelService.createDeviceModel(any(CreateDeviceModelCommand.class)))
				.thenThrow(new IllegalArgumentException("Invalid name"));

		mockMvc.perform(post("/api/v1/device-models")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isBadRequest());
	}

	@Test
	@WithMockUser(username = "user")
	void getAllDeviceModels_ShouldReturn200_WhenUserIsAuthenticated() throws Exception {
		Brand brand = new Brand("brand-123", "Samsung");
		DeviceModel dm1 = new DeviceModel("1", "Galaxy S23", brand);
		DeviceModel dm2 = new DeviceModel("2", "Galaxy S22", brand);

		when(deviceModelService.findAll()).thenReturn(Arrays.asList(dm1, dm2));

		mockMvc.perform(get("/api/v1/device-models"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(2))
				.andExpect(jsonPath("$[0].name").value("Galaxy S23"));
	}

	@Test
	void getAllDeviceModels_ShouldReturn401_WhenUserIsNotAuthenticated() throws Exception {
		mockMvc.perform(get("/api/v1/device-models"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(username = "user")
	void findDeviceModelById_ShouldReturn200_WhenFound() throws Exception {
		Brand brand = new Brand("brand-123", "Samsung");
		DeviceModel dm = new DeviceModel("1", "Galaxy S23", brand);

		when(deviceModelService.findById("1")).thenReturn(Optional.of(dm));

		mockMvc.perform(get("/api/v1/device-models/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Galaxy S23"));
	}

	@Test
	@WithMockUser(username = "user")
	void findDeviceModelById_ShouldReturn404_WhenNotFound() throws Exception {
		when(deviceModelService.findById("1")).thenReturn(Optional.empty());

		mockMvc.perform(get("/api/v1/device-models/1"))
				.andExpect(status().isNotFound());
	}

	@Test
	void findDeviceModelById_ShouldReturn401_WhenUserIsNotAuthenticated() throws Exception {
		mockMvc.perform(get("/api/v1/device-models/1"))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(username = "user")
	void updateDeviceModel_ShouldReturn200_WhenSuccessful() throws Exception {
		UpdateDeviceModelRequest request = new UpdateDeviceModelRequest("Galaxy S23 Updated", "brand-123");
		Brand brand = new Brand("brand-123", "Samsung");
		DeviceModel updatedDm = new DeviceModel("1", "Galaxy S23 Updated", brand);

		when(deviceModelService.updateDeviceModel(eq("1"), any(UpdateDeviceModelCommand.class)))
				.thenReturn(Optional.of(updatedDm));

		mockMvc.perform(put("/api/v1/device-models/1")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("Galaxy S23 Updated"));
	}

	@Test
	@WithMockUser(username = "user")
	void updateDeviceModel_ShouldReturn404_WhenNotFound() throws Exception {
		UpdateDeviceModelRequest request = new UpdateDeviceModelRequest("Galaxy S23 Updated", "brand-123");

		when(deviceModelService.updateDeviceModel(eq("1"), any(UpdateDeviceModelCommand.class)))
				.thenReturn(Optional.empty());

		mockMvc.perform(put("/api/v1/device-models/1")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isNotFound());
	}

	@Test
	void updateDeviceModel_ShouldReturn401_WhenUserIsNotAuthenticated() throws Exception {
		UpdateDeviceModelRequest request = new UpdateDeviceModelRequest("Updated", "brand-123");

		mockMvc.perform(put("/api/v1/device-models/1")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isUnauthorized());
	}

	@Test
	@WithMockUser(username = "user")
	void deleteDeviceModel_ShouldReturn204_WhenSuccessful() throws Exception {
		when(deviceModelService.deleteDeviceModel("1")).thenReturn(true);

		mockMvc.perform(delete("/api/v1/device-models/1")
				.with(csrf()))
				.andExpect(status().isNoContent());
	}

	@Test
	@WithMockUser(username = "user")
	void deleteDeviceModel_ShouldReturn404_WhenNotFound() throws Exception {
		when(deviceModelService.deleteDeviceModel("1")).thenReturn(false);

		mockMvc.perform(delete("/api/v1/device-models/1")
				.with(csrf()))
				.andExpect(status().isNotFound());
	}

	@Test
	void deleteDeviceModel_ShouldReturn401_WhenUserIsNotAuthenticated() throws Exception {
		mockMvc.perform(delete("/api/v1/device-models/1")
				.with(csrf()))
				.andExpect(status().isUnauthorized());
	}
}
