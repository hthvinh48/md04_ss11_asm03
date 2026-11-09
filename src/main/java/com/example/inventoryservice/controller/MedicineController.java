package com.example.inventoryservice.controller;

import com.example.inventoryservice.dto.UpdatePriceRequest;
import com.example.inventoryservice.entity.Medicine;
import com.example.inventoryservice.service.MedicineService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/medicines")
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;

    @GetMapping("/{id}")
    public Medicine getById(@PathVariable String id) {
        return medicineService.getById(id);
    }

    @PutMapping("/{id}/price")
    public Medicine updatePrice(@PathVariable String id,
                                @RequestBody UpdatePriceRequest request) {
        return medicineService.updatePrice(id, request.getPrice());
    }

    @PostMapping("/{id}/import")
    public Medicine importStock(@PathVariable String id, @RequestParam int quantity) {
        return medicineService.importStock(id, quantity);
    }
}