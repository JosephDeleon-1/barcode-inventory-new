package com.barcodeproject.barcodescanner.service;


import com.github.sarxos.webcam.Webcam;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Service
public class WebcamService {

    public BufferedImage captureImage() {
        Webcam webcam = Webcam.getDefault();
        if (webcam != null) {
            webcam.open();
            BufferedImage image = webcam.getImage();
            webcam.close();
            return image;
        }
        return null;
    }

    public String decodeBarcode(BufferedImage image) {
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            return new MultiFormatReader().decode(bitmap).getText();
        } catch (NotFoundException e) {
            return "Error: invalid barcode";
        }
    }
}


