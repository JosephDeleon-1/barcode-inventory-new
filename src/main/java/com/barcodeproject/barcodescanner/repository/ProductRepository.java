package com.barcodeproject.barcodescanner.repository;

import com.barcodeproject.barcodescanner.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
