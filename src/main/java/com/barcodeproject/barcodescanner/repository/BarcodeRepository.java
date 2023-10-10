package com.barcodeproject.barcodescanner.repository;

import com.barcodeproject.barcodescanner.model.Barcode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarcodeRepository extends JpaRepository<Barcode, Long> {
}
