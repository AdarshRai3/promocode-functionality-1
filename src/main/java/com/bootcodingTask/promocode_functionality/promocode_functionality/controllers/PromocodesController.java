package com.bootcodingTask.promocode_functionality.promocode_functionality.controllers;

import com.bootcodingTask.promocode_functionality.promocode_functionality.dtos.reponse.PromocodesResponse;
import com.bootcodingTask.promocode_functionality.promocode_functionality.dtos.request.PromocodesRequests;
import com.bootcodingTask.promocode_functionality.promocode_functionality.services.PromocodesServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/promocodes")
public class PromocodesController {

    @Autowired
    private PromocodesServices promocodesServices;

    // Create
    @PostMapping
    public ResponseEntity<PromocodesResponse> createPromocode(@Valid @RequestBody PromocodesRequests promocodeRequests) {
        return new ResponseEntity<>(promocodesServices.createPromocode(promocodeRequests), HttpStatus.CREATED);
    }

    // Read - Get All
    @GetMapping
    public ResponseEntity<List<PromocodesResponse>> getAllPromocodes() {
        return ResponseEntity.ok(promocodesServices.getAllPromocodes());
    }

    // Read - Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<PromocodesResponse> getPromocodeById(@PathVariable("id") Long promocodeId) {
        return ResponseEntity.ok(promocodesServices.getPromocodeById(promocodeId));
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<PromocodesResponse> updatePromocode(
            @PathVariable("id") Long promocodeId,
            @Valid @RequestBody PromocodesRequests promocodeRequests) {
        return ResponseEntity.ok(promocodesServices.updatePromocode(promocodeId, promocodeRequests));
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromocode(@PathVariable("id") Long promocodeId) {
        promocodesServices.deletePromocode(promocodeId);
        return ResponseEntity.noContent().build();
    }
}