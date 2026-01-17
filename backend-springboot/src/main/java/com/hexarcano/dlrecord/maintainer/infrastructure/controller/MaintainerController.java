package com.hexarcano.dlrecord.maintainer.infrastructure.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexarcano.dlrecord.maintainer.application.service.MaintainerService;
import com.hexarcano.dlrecord.maintainer.infrastructure.controller.dto.UpdateMaintainerRequest;
import com.hexarcano.dlrecord.maintainer.model.Maintainer;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/maintainers")
@RequiredArgsConstructor
public class MaintainerController {
    private final MaintainerService maintainerService;

    @PostMapping
    public ResponseEntity<Maintainer> createMaintainer(@RequestBody Maintainer maintainer) {
        Maintainer createdMaintainer = maintainerService.createMaintainer(maintainer);
        return new ResponseEntity<>(createdMaintainer, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Maintainer> findMaintainerById(@PathVariable String id) {
        return maintainerService.findById(id)
                .map(maintainer -> new ResponseEntity<>(maintainer, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Maintainer>> getAllMaintainers() {
        List<Maintainer> list = maintainerService.findAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Maintainer> updateMaintainer(@PathVariable String id,
            @RequestBody UpdateMaintainerRequest updateMaintainerRequest) {
        return maintainerService.updateMaintainer(id, updateMaintainerRequest)
                .map(updatedMaintainer -> new ResponseEntity<>(updatedMaintainer, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaintainer(@PathVariable String id) {
        return maintainerService.deleteMaintainer(id)
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}
