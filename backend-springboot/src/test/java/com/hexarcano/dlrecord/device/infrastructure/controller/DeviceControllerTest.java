package com.hexarcano.dlrecord.device.infrastructure.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.hexarcano.dlrecord.brand.domain.model.Brand;
import com.hexarcano.dlrecord.config.GlobalExceptionHandler;
import com.hexarcano.dlrecord.config.WebSecurityConfig;
import com.hexarcano.dlrecord.config.token.JwtAuthFilter;
import com.hexarcano.dlrecord.config.token.JwtService;
import com.hexarcano.dlrecord.device.application.port.in.command.CreateDeviceCommand;
import com.hexarcano.dlrecord.device.application.port.in.command.UpdateDeviceCommand;
import com.hexarcano.dlrecord.device.application.service.DeviceService;
import com.hexarcano.dlrecord.device.domain.exception.DeviceInvalidDataException;
import com.hexarcano.dlrecord.device.domain.model.Device;
import com.hexarcano.dlrecord.device.infrastructure.controller.dto.CreateDeviceRequest;
import com.hexarcano.dlrecord.device.infrastructure.controller.dto.UpdateDeviceRequest;
import com.hexarcano.dlrecord.devicemodel.domain.model.DeviceModel;
import com.hexarcano.dlrecord.devicetype.domain.model.DeviceType;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@Import({ WebSecurityConfig.class, JwtAuthFilter.class, GlobalExceptionHandler.class })
class DeviceControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private DeviceService deviceService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    private Device mockDevice;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        Brand brand = new Brand("brand-1", "Samsung");
        DeviceModel model = new DeviceModel("model-1", "Galaxy", brand);
        DeviceType type = new DeviceType("type-1", "Phone");

        mockDevice = new Device("uuid-1", "MyPhone", "192.168.1.1/24", null, null, brand, model, type, "inv-1");
    }

    @Test
    @WithMockUser(username = "user")
    void createDevice_ShouldReturn201_WhenSuccessful() throws Exception {
        CreateDeviceRequest request = new CreateDeviceRequest(
                "MyPhone", "192.168.1.1/24", null, null, "brand-1", "model-1", "type-1", "inv-1");

        when(deviceService.createDevice(any(CreateDeviceCommand.class))).thenReturn(mockDevice);

        mockMvc.perform(post("/api/v1/devices")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.uuid").value("uuid-1"))
                .andExpect(jsonPath("$.deviceName").value("MyPhone"));
    }

    @Test
    @WithMockUser(username = "user")
    void createDevice_ShouldReturn400_WhenDataIsInvalid() throws Exception {
        CreateDeviceRequest request = new CreateDeviceRequest(
                "", "", null, null, "brand-1", "model-1", "type-1", "inv-1");

        when(deviceService.createDevice(any(CreateDeviceCommand.class)))
                .thenThrow(new DeviceInvalidDataException("Device Name is required"));

        mockMvc.perform(post("/api/v1/devices")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Device Name is required"));
    }

    @Test
    void createDevice_ShouldReturn401_WhenNotAuthenticated() throws Exception {
        // Note: Device endpoints are permitted in WebSecurityConfig as per recent
        // changes (/api/v1/devices/**)
        // So this test might actually return 201 or 400 depending on content, NOT 401.
        // BUT wait, in WebSecurityConfig we DID enable permitAll for /api/v1/devices/**
        // temporarily.
        // However, standard expectation for POST often requires auth in real scenarios,
        // but strictly per CURRENT rules it allows it.
        // Let's verify standard behavior. If the user asked to make it public, it IS
        // public.
        // Let's skip checking 401 for now to match current config, OR check that it
        // WORKS without user.

        CreateDeviceRequest request = new CreateDeviceRequest(
                "MyPhone", "192.168.1.1/24", null, null, "brand-1", "model-1", "type-1", "inv-1");

        when(deviceService.createDevice(any(CreateDeviceCommand.class))).thenReturn(mockDevice);

        mockMvc.perform(post("/api/v1/devices")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    void getDevice_ShouldReturn200_WhenFound() throws Exception {
        when(deviceService.findById("uuid-1")).thenReturn(Optional.of(mockDevice));

        mockMvc.perform(get("/api/v1/devices/uuid-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deviceName").value("MyPhone"));
    }

    @Test
    @WithMockUser
    void getDevice_ShouldReturn404_WhenNotFound() throws Exception {
        when(deviceService.findById("unknown")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/devices/unknown"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void getAllDevices_ShouldReturn200() throws Exception {
        Page<Device> page = new PageImpl<>(Collections.singletonList(mockDevice));
        when(deviceService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/devices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].deviceName").value("MyPhone"))
                .andExpect(jsonPath("$.totalElements").value(1));
    }

    @Test
    @WithMockUser
    void updateDevice_ShouldReturn200_WhenSuccessful() throws Exception {
        UpdateDeviceRequest request = new UpdateDeviceRequest(
                "Updated", "192.168.1.1/24", null, null, "brand-1", "model-1", "type-1", "inv-1");

        when(deviceService.updateDevice(any(UpdateDeviceCommand.class))).thenReturn(mockDevice);

        mockMvc.perform(put("/api/v1/devices/uuid-1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void deleteDevice_ShouldReturn204_WhenSuccessful() throws Exception {
        when(deviceService.deleteDevice("uuid-1")).thenReturn(true);

        mockMvc.perform(delete("/api/v1/devices/uuid-1")
                .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void deleteDevice_ShouldReturn404_WhenNotFound() throws Exception {
        when(deviceService.deleteDevice("uuid-1")).thenReturn(false);

        mockMvc.perform(delete("/api/v1/devices/uuid-1")
                .with(csrf()))
                .andExpect(status().isNotFound());
    }
}
