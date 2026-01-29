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
import com.hexarcano.dlrecord.maintainer.domain.model.Maintainer;
import com.hexarcano.dlrecord.maintainer.infrastructure.controller.dto.CreateMaintainerRequest;
import com.hexarcano.dlrecord.maintainer.infrastructure.controller.dto.UpdateMaintainerRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/maintainers")
@RequiredArgsConstructor
public class MaintainerController {
    private final MaintainerService maintainerService;

    @PostMapping
    public ResponseEntity<Maintainer> createMaintainer(@RequestBody CreateMaintainerRequest request) {
        Maintainer createdMaintainer = maintainerService.createMaintainer(request.toCreateMaintainerCommand());
        return new ResponseEntity<>(createdMaintainer, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Maintainer> findMaintainerById(@PathVariable("id") String id) {
        return maintainerService.findById(id).map(maintainer -> new ResponseEntity<>(maintainer, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<Maintainer>> getAllMaintainers() {
        return new ResponseEntity<>(maintainerService.findAll(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Maintainer> updateMaintainer(@PathVariable("id") String id,
            @RequestBody UpdateMaintainerRequest request) {
        return maintainerService.updateMaintainer(id, request.toUpdateMaintainerCommand())
                .map(maintainer -> new ResponseEntity<>(maintainer, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaintainer(@PathVariable("id") String id) {
        return maintainerService.deleteMaintainer(id)
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
