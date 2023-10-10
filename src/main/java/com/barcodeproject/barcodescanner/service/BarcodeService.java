package com.barcodeproject.barcodescanner.service;

import com.barcodeproject.barcodescanner.model.Barcode;
import com.barcodeproject.barcodescanner.model.Product;
import com.barcodeproject.barcodescanner.repository.BarcodeRepository;
import com.barcodeproject.barcodescanner.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BarcodeService {

    private final BarcodeRepository barcodeRepository;
    private final ProductRepository productRepository;

    @Autowired
    public BarcodeService(BarcodeRepository barcodeRepository, ProductRepository productRepository) {
        this.barcodeRepository = barcodeRepository;
        this.productRepository = productRepository;
    }

    public Barcode saveBarcode(String value) {
        Barcode barcode = new Barcode();
        barcode.setBarcodeValue(value);
        barcode.setTimestamp(LocalDateTime.now());
        return barcodeRepository.save(barcode);
    }

    public List<Barcode> getAllBarcodes() {
        return barcodeRepository.findAll();
    }

    // Save a product to the database
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    // Retrieve all products from the database
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Fetch a product by its ID
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // Update a product
    public void updateProduct(Long id, Product updatedProduct) {
        if (productRepository.existsById(id)) {
            updatedProduct.setId(id);  // Ensure the ID remains the same
            productRepository.save(updatedProduct);
        }
    }

    // Delete a product by its ID
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }
}



