package com.hexarcano.dlrecord.brand.infrastructure.controller;

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

import com.hexarcano.dlrecord.brand.application.port.in.command.CreateBrandCommand;
import com.hexarcano.dlrecord.brand.application.port.in.command.UpdateBrandCommand;
import com.hexarcano.dlrecord.brand.application.service.BrandService;
import com.hexarcano.dlrecord.brand.domain.model.Brand;
import com.hexarcano.dlrecord.brand.infrastructure.controller.dto.CreateBrandRequest;
import com.hexarcano.dlrecord.brand.infrastructure.controller.dto.UpdateBrandRequest;
import com.hexarcano.dlrecord.config.WebSecurityConfig;
import com.hexarcano.dlrecord.config.token.JwtAuthFilter;
import com.hexarcano.dlrecord.config.token.JwtService;

import tools.jackson.databind.ObjectMapper;

@SpringBootTest
@Import({ WebSecurityConfig.class, JwtAuthFilter.class })
class BrandControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private BrandService brandService;

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
    void createBrand_ShouldReturn201_WhenUserIsAuthenticated() throws Exception {
        CreateBrandRequest request = new CreateBrandRequest("Samsung");
        Brand createdBrand = new Brand("uuid-123", "Samsung");

        when(brandService.createBrand(any(CreateBrandCommand.class))).thenReturn(createdBrand);

        mockMvc.perform(post("/api/v1/brands")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.uuid").value("uuid-123"))
                .andExpect(jsonPath("$.name").value("Samsung"));
    }

    @Test
    void createBrand_ShouldReturn401_WhenUserIsNotAuthenticated() throws Exception {
        CreateBrandRequest request = new CreateBrandRequest("Samsung");

        mockMvc.perform(post("/api/v1/brands")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user")
    void getAllBrands_ShouldReturn200_WhenUserIsAuthenticated() throws Exception {
        Brand brand1 = new Brand("1", "Samsung");
        Brand brand2 = new Brand("2", "Apple");

        when(brandService.findAll()).thenReturn(Arrays.asList(brand1, brand2));

        mockMvc.perform(get("/api/v1/brands"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Samsung"))
                .andExpect(jsonPath("$[1].name").value("Apple"));
    }

    @Test
    void getAllBrands_ShouldReturn401_WhenUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(get("/api/v1/brands"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user")
    void findBrandById_ShouldReturn200_WhenFound() throws Exception {
        Brand brand = new Brand("1", "Samsung");

        when(brandService.findById("1")).thenReturn(Optional.of(brand));

        mockMvc.perform(get("/api/v1/brands/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Samsung"));
    }

    @Test
    @WithMockUser(username = "user")
    void findBrandById_ShouldReturn404_WhenNotFound() throws Exception {
        when(brandService.findById("1")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/brands/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void findBrandById_ShouldReturn401_WhenUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(get("/api/v1/brands/1"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user")
    void updateBrand_ShouldReturn200_WhenSuccessful() throws Exception {
        UpdateBrandRequest request = new UpdateBrandRequest("Samsung Updated");
        Brand updatedBrand = new Brand("1", "Samsung Updated");

        when(brandService.updateBrand(eq("1"), any(UpdateBrandCommand.class))).thenReturn(Optional.of(updatedBrand));

        mockMvc.perform(put("/api/v1/brands/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Samsung Updated"));
    }

    @Test
    @WithMockUser(username = "user")
    void updateBrand_ShouldReturn404_WhenNotFound() throws Exception {
        UpdateBrandRequest request = new UpdateBrandRequest("Samsung Updated");

        when(brandService.updateBrand(eq("1"), any(UpdateBrandCommand.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/v1/brands/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateBrand_ShouldReturn401_WhenUserIsNotAuthenticated() throws Exception {
        UpdateBrandRequest request = new UpdateBrandRequest("Samsung Updated");

        mockMvc.perform(put("/api/v1/brands/1")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "user")
    void deleteBrand_ShouldReturn200_WhenSuccessful() throws Exception {
        when(brandService.deleteBrand("1")).thenReturn(true);

        mockMvc.perform(delete("/api/v1/brands/1")
                .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user")
    void deleteBrand_ShouldReturn404_WhenNotFound() throws Exception {
        when(brandService.deleteBrand("1")).thenReturn(false);

        mockMvc.perform(delete("/api/v1/brands/1")
                .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBrand_ShouldReturn401_WhenUserIsNotAuthenticated() throws Exception {
        mockMvc.perform(delete("/api/v1/brands/1")
                .with(csrf()))
                .andExpect(status().isUnauthorized());
    }
}
