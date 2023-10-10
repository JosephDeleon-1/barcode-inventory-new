package com.barcodeproject.barcodescanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.barcodeproject.barcodescanner.model")
public class BarcodeScannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BarcodeScannerApplication.class, args);
	}

}
