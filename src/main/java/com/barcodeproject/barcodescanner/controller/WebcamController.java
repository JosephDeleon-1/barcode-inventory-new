package com.barcodeproject.barcodescanner.controller;

import com.barcodeproject.barcodescanner.model.Barcode;
import com.barcodeproject.barcodescanner.model.Product;
import com.barcodeproject.barcodescanner.service.BarcodeService;
import com.barcodeproject.barcodescanner.service.WebcamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.List;

@Controller
public class WebcamController {

    private final WebcamService webcamService;
    private final BarcodeService barcodeService;

    @Autowired
    public WebcamController(WebcamService webcamService, BarcodeService barcodeService) {
        this.webcamService = webcamService;
        this.barcodeService = barcodeService;
    }

    @GetMapping("/scan")
    public String scanPage() {
        System.out.println("Accessing /scan endpoint");
        return "scan";
    }

    @PostMapping("/capture")
    public String captureImage(Model model) {
        BufferedImage image = webcamService.captureImage();
        String barcodeValue = webcamService.decodeBarcode(image);
        Barcode savedBarcode = barcodeService.saveBarcode(barcodeValue);
        model.addAttribute("barcode", barcodeValue);
        return "result";
    }

    @GetMapping("/barcodes")
    public String viewAllBarcodes(Model model) {
        List<Barcode> barcodes = barcodeService.getAllBarcodes();
        model.addAttribute("barcodes", barcodes);
        return "barcodes";
    }

    @PostMapping("/process-image")
    public ResponseEntity<String> processImage(@RequestParam("image") MultipartFile file) {
        try {
            BufferedImage image = ImageIO.read(file.getInputStream());
            String barcodeValue = webcamService.decodeBarcode(image);
            barcodeService.saveBarcode(barcodeValue);
            return ResponseEntity.ok(barcodeValue);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing image");
        }
    }

    @GetMapping("/product-entry")
    public String productEntryPage(Model model) {
        model.addAttribute("product", new Product());
        return "product-entry";
    }

    @PostMapping("/add-product")
    public String addProduct(Product product, Model model) {
        barcodeService.saveProduct(product);
        model.addAttribute("message", "Product added successfully!");
        return "product-entry";
    }

    @GetMapping("/product-list")
    public String productList(Model model) {
        List<Product> products = barcodeService.getAllProducts();
        model.addAttribute("products", products);
        return "product-list";
    }

    @GetMapping("/edit-product/{id}")
    public String editProductPage(@PathVariable Long id, Model model) {
        Product product = barcodeService.getProductById(id);
        if(product != null) {
            model.addAttribute("product", product);
            return "edit-product";
        }
        return "redirect:/product-list";
    }

    @PostMapping("/edit-product/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product updatedProduct) {
        barcodeService.updateProduct(id, updatedProduct);
        return "redirect:/product-list";
    }

    @GetMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable Long id) {
        barcodeService.deleteProductById(id);
        return "redirect:/product-list";
    }
}
