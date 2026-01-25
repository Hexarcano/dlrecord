package com.hexarcano.dlrecord.brand.infrastructure.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.hexarcano.dlrecord.brand.application.service.BrandService;
import com.hexarcano.dlrecord.brand.domain.model.Brand;
import com.hexarcano.dlrecord.brand.infrastructure.controller.dto.CreateBrandRequest;
import com.hexarcano.dlrecord.brand.infrastructure.controller.dto.UpdateBrandRequest;

import lombok.RequiredArgsConstructor;

/**
 * Primary (Driving) Adapter that exposes brand use cases via a RESTful API.
 * It handles incoming HTTP requests and delegates them to the application
 * service layer.
 */
@RestController
@RequestMapping("/api/v1/brands")
@RequiredArgsConstructor
public class BrandController {
    private final BrandService brandService;

    /**
     * REST endpoint to create a new brand.
     * 
     * @param brand The brand data sent in the request body.
     * @return A {@link ResponseEntity} with the created brand and an HTTP status of
     *         201 (Created).
     */
    @PostMapping()
    public ResponseEntity<Brand> createBrand(@RequestBody CreateBrandRequest request) {
        Brand createdBrand = brandService.createBrand(request.toCreateBrandCommand());

        return new ResponseEntity<Brand>(createdBrand, HttpStatus.CREATED);
    }

    /**
     * REST endpoint to retrieve all brands.
     * 
     * @return A {@link ResponseEntity} with a list of all brands and an HTTP status
     *         of 200 (OK).
     */
    @GetMapping()
    public ResponseEntity<List<Brand>> getAllBrands() {
        List<Brand> list = brandService.findAll();

        return new ResponseEntity<List<Brand>>(list, HttpStatus.OK);
    }

    /**
     * REST endpoint to find a single brand by its unique ID.
     * 
     * @param id The unique identifier of the brand, passed in the URL path.
     * @return A {@link ResponseEntity} with the found brand and HTTP status 200
     *         (OK), or HTTP status 404 (Not Found) if the brand does not exist.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Brand> findBrandById(@PathVariable String id) {
        return brandService.findById(id)
                .map(brand -> new ResponseEntity<Brand>(brand, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Brand> updateBrand(@PathVariable String id, @RequestBody UpdateBrandRequest request) {
        return brandService.updateBrand(id, request.toUpdateBrandCommand())
                .map(brand -> new ResponseEntity<Brand>(brand, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * REST endpoint to delete a brand by its unique ID.
     * 
     * @param id The unique identifier of the brand to delete, passed in the URL
     *           path.
     * @return A {@link ResponseEntity} with HTTP status 200 (OK) if deletion was
     *         successful, or HTTP status 404 (Not Found) if the brand does not
     *         exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteBrand(@PathVariable String id) {
        return (brandService.deleteBrand(id)) ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
