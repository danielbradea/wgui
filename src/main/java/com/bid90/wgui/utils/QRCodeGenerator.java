package com.bid90.wgui.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for generating QR codes.
 */
public class QRCodeGenerator {


    /**
     * Generates a QR code as a Base64-encoded string.
     *
     * @param content The content to encode in the QR code.
     * @param width   The width of the QR code.
     * @param height  The height of the QR code.
     * @return The Base64-encoded string representation of the generated QR code.
     * @throws QRCodeGenerationException if an error occurs during QR code generation.
     */
    public static String generateQRCodeAsBase64(String content, int width, int height) {
        try {
            BitMatrix bitMatrix = generateBitMatrix(content, width, height);
            byte[] imageBytes = encodeBitMatrixToByteArray(bitMatrix);
            return encodeByteArrayToBase64(imageBytes);
        } catch (Exception e) {
            throw new QRCodeGenerationException("Error generating QR code: " + e.getMessage());
        }
    }

    /**
     * Generates a QR code as a byte array.
     *
     * @param content The content to encode in the QR code.
     * @param width   The width of the QR code.
     * @param height  The height of the QR code.
     * @return The byte array representation of the generated QR code.
     * @throws QRCodeGenerationException if an error occurs during QR code generation.
     */
    public static byte[] generateQRCode(String content, int width, int height) {
        try {
            BitMatrix bitMatrix = generateBitMatrix(content, width, height);
            return encodeBitMatrixToByteArray(bitMatrix);
        } catch (Exception e) {
            throw new QRCodeGenerationException("Error generating QR code: " + e.getMessage());
        }
    }

    private static BitMatrix generateBitMatrix(String content, int width, int height) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        return qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
    }

    private static byte[] encodeBitMatrixToByteArray(BitMatrix bitMatrix) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", baos);
        return baos.toByteArray();
    }

    private static String encodeByteArrayToBase64(byte[] imageBytes) {
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    /**
     * Exception thrown when an error occurs during QR code generation.
     */
    public static class QRCodeGenerationException extends RuntimeException {
        public QRCodeGenerationException(String message) {
            super(message);
        }
    }
}