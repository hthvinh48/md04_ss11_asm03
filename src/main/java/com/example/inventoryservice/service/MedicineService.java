package com.example.inventoryservice.service;

import com.example.inventoryservice.alert.AlertMessage;
import com.example.inventoryservice.alert.AlertPublisher;
import com.example.inventoryservice.entity.Medicine;
import com.example.inventoryservice.repository.MedicineRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicineService {
    private final AlertPublisher alertPublisher;
    private final MedicineRepository medicineRepository;

    @Cacheable(value = "medicines", key = "#id")
    public Medicine getById(String id) {
        log.info("Cache MISS: truy vấn database cho thuốc {}", id);
        return medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thuốc " + id));
    }

    @CacheEvict(value = "medicines", key = "#id")
    @Transactional
    public Medicine updatePrice(String id, double newPrice) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thuốc " + id));
        medicine.setPrice(newPrice);
        log.info("Cập nhật giá thuốc {} -> {}, xóa cache", id, newPrice);
        return medicineRepository.save(medicine);
    }

    @CacheEvict(value = "medicines", key = "#medicine.id")
    public Medicine update(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    @CacheEvict(value = "medicines", key = "#id")
    @Transactional
    public Medicine importStock(String id, int quantity) {
        Medicine m = medicineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thuốc " + id));
        m.setStock(m.getStock() + quantity);
        Medicine saved = medicineRepository.save(m);
        alertPublisher.publish(new AlertMessage("IMPORT",
                "Đã nhập " + quantity + " hộp " + m.getName()));
        return saved;
    }
}