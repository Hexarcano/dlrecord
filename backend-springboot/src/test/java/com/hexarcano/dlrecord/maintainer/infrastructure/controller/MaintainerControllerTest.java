package com.hexarcano.dlrecord.maintainer.infrastructure.controller;

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
import com.hexarcano.dlrecord.maintainer.application.port.in.command.CreateMaintainerCommand;
import com.hexarcano.dlrecord.maintainer.application.port.in.command.UpdateMaintainerCommand;
import com.hexarcano.dlrecord.maintainer.application.service.MaintainerService;
import com.hexarcano.dlrecord.maintainer.domain.model.Maintainer;
import com.hexarcano.dlrecord.maintainer.infrastructure.controller.dto.CreateMaintainerRequest;
import com.hexarcano.dlrecord.maintainer.infrastructure.controller.dto.UpdateMaintainerRequest;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@Import({ WebSecurityConfig.class, JwtAuthFilter.class })
class MaintainerControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private MaintainerService maintainerService;

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
    void createMaintainer_ShouldReturn201_WhenUserIsAuthenticated() throws Exception {
        CreateMaintainerRequest request = new CreateMaintainerRequest("jdoe", "jdoe@example.com", "password123", false);
        Maintainer createdMaintainer = new Maintainer("uuid-123", "jdoe", "jdoe@example.com", "password123", false);

        when(maintainerService.createMaintainer(any(CreateMaintainerCommand.class))).thenReturn(createdMaintainer);

        mockMvc.perform(post("/api/v1/maintainers")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.uuid").value("uuid-123"))
                .andExpect(jsonPath("$.username").value("jdoe"));
    }

    @Test
    void createMaintainer_ShouldReturn401_WhenUserIsNotAuthenticated() throws Exception {
        CreateMaintainerRequest request = new CreateMaintainerRequest("jdoe", "jdoe@example.com", "password123", false);

        mockMvc.perform(post("/api/v1/maintainers")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user")
    void createMaintainer_ShouldReturn400_WhenDataIsInvalid() throws Exception {
        CreateMaintainerRequest request = new CreateMaintainerRequest("", "invalid-email", "", false);

        when(maintainerService.createMaintainer(any(CreateMaintainerCommand.class)))
                .thenThrow(new IllegalArgumentException("Invalid data"));

        mockMvc.perform(post("/api/v1/maintainers")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "user")
    void getAllMaintainers_ShouldReturn200_WhenUserIsAuthenticated() throws Exception {
        Maintainer m1 = new Maintainer("1", "jdoe", "jdoe@example.com", "pass", false);
        Maintainer m2 = new Maintainer("2", "jane", "jane@example.com", "pass", true);

        when(maintainerService.findAll()).thenReturn(Arrays.asList(m1, m2));

        mockMvc.perform(get("/api/v1/maintainers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].username").value("jdoe"));
    }

    @Test
    void getAllMaintainers_ShouldReturn401_WhenUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(get("/api/v1/maintainers"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user")
    void findMaintainerById_ShouldReturn200_WhenFound() throws Exception {
        Maintainer m = new Maintainer("1", "jdoe", "jdoe@example.com", "pass", false);

        when(maintainerService.findById("1")).thenReturn(Optional.of(m));

        mockMvc.perform(get("/api/v1/maintainers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("jdoe"));
    }

    @Test
    @WithMockUser(username = "user")
    void findMaintainerById_ShouldReturn404_WhenNotFound() throws Exception {
        when(maintainerService.findById("1")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/maintainers/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findMaintainerById_ShouldReturn401_WhenUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(get("/api/v1/maintainers/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user")
    void updateMaintainer_ShouldReturn200_WhenSuccessful() throws Exception {
        UpdateMaintainerRequest request = new UpdateMaintainerRequest("jdoe_updated", "jdoe@example.com", "newpass",
                false);
        Maintainer updatedM = new Maintainer("1", "jdoe_updated", "jdoe@example.com", "newpass", false);

        when(maintainerService.updateMaintainer(eq("1"), any(UpdateMaintainerCommand.class)))
                .thenReturn(Optional.of(updatedM));

        mockMvc.perform(put("/api/v1/maintainers/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("jdoe_updated"));
    }

    @Test
    @WithMockUser(username = "user")
    void updateMaintainer_ShouldReturn404_WhenNotFound() throws Exception {
        UpdateMaintainerRequest request = new UpdateMaintainerRequest("jdoe_updated", "jdoe@example.com", "newpass",
                false);

        when(maintainerService.updateMaintainer(eq("1"), any(UpdateMaintainerCommand.class)))
                .thenReturn(Optional.empty());

        mockMvc.perform(put("/api/v1/maintainers/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateMaintainer_ShouldReturn401_WhenUserIsNotAuthenticated() throws Exception {
        UpdateMaintainerRequest request = new UpdateMaintainerRequest("user", "email@test.com", "pass", false);

        mockMvc.perform(put("/api/v1/maintainers/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user")
    void deleteMaintainer_ShouldReturn204_WhenSuccessful() throws Exception {
        when(maintainerService.deleteMaintainer("1")).thenReturn(true);

        mockMvc.perform(delete("/api/v1/maintainers/1")
                .with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "user")
    void deleteMaintainer_ShouldReturn404_WhenNotFound() throws Exception {
        when(maintainerService.deleteMaintainer("1")).thenReturn(false);

        mockMvc.perform(delete("/api/v1/maintainers/1")
                .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteMaintainer_ShouldReturn401_WhenUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(delete("/api/v1/maintainers/1")
                .with(csrf()))
                .andExpect(status().isUnauthorized());
    }
}
