//package com.lazur.utils;
//
//import com.google.zxing.BarcodeFormat;
//import com.google.zxing.EncodeHintType;
//import com.google.zxing.Writer;
//import com.google.zxing.WriterException;
//import com.google.zxing.common.BitMatrix;
//import com.google.zxing.oned.Code128Writer;
//import com.google.zxing.oned.EAN13Writer;
//import com.google.zxing.qrcode.QRCodeWriter;
//import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
//import org.springframework.stereotype.Component;
//
//import javax.imageio.ImageIO;
//import javax.imageio.stream.ImageOutputStream;
//import javax.imageio.stream.MemoryCacheImageOutputStream;
//import javax.servlet.http.HttpServletRequest;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.Base64;
//import java.util.Hashtable;
//import java.util.StringJoiner;
//
//@Component
//public class BarcodeGenerator implements BarcodeService {
//
//    private final Integer REQUESTED_EAN13_WIDTH = 256;
//    private final Integer REQUESTED_EAN13_HEIGHT = 128;
//    private final Integer REQUESTED_QR_WIDTH = 256;
//    private final Integer REQUESTED_QR_HEIGHT = 256;
//
//    @Override
//    public String getEAN13Barcode(Product product) throws WriterException, IOException {
//        String barcodeNumber = String.valueOf(product.getBarcode());
//        String skuNumber = String.valueOf(product.getSku());
//        Hashtable hintMap = new Hashtable();
//        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
//        BitMatrix byteMatrix = new EAN13Writer().encode(barcodeNumber, BarcodeFormat.EAN_13, REQUESTED_EAN13_WIDTH, REQUESTED_EAN13_HEIGHT,hintMap);
//        BufferedImage image = createImage(byteMatrix, barcodeNumber, skuNumber);
//        String ean13ImageCode = getImage(image);
//
//        return ean13ImageCode;
//    }
//
//
//
//    @Override
//    public String getSKUNumber(Product product) {
//        StringJoiner stringJoiner = new StringJoiner(" ");
//        char productAbbreviation = product.getType().toUpperCase().charAt(0);
//        stringJoiner.add(String.valueOf(productAbbreviation));
//        stringJoiner.add(String.valueOf(product.getModelNumber()));
//        stringJoiner.add(product.getTop() == null ? "" : product.getTop().getAbbreviation());
//        stringJoiner.add(product.getFrame()  == null ? "" : product.getFrame().getAbbreviation());
//        stringJoiner.add(product.getFabric() == null ? "" : product.getFabric().getAbbreviation());
//        stringJoiner.add(product.getFinish() == null ? "" : product.getFinish().getAbbreviation());
//
//        return stringJoiner.toString();
//    }
//
//    @Override
//    public String getBarcode(Product product) throws WriterException, IOException {
//        String barcodeNumber = String.valueOf(product.getId());
//        String skuNumber = String.valueOf(product.getSku());
//        Hashtable hintMap = new Hashtable();
//        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
//        BitMatrix byteMatrix = new Code128Writer().encode(barcodeNumber, BarcodeFormat.CODE_128, REQUESTED_EAN13_WIDTH, REQUESTED_EAN13_HEIGHT,hintMap);
//        BufferedImage image = createImage(byteMatrix, barcodeNumber, skuNumber);
//        String code128Image = getImage(image);
//
//        return code128Image;
//    }
//
//    @Override
//    public String getQRCode(HttpServletRequest request) throws WriterException, IOException {
//        Hashtable hintMap = new Hashtable();
//        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
//        Writer qrCodeWriter = new QRCodeWriter();
//        String domain = request.getRequestURL().toString();
//        BitMatrix byteMatrix = qrCodeWriter.encode(domain,
//                BarcodeFormat.QR_CODE, REQUESTED_QR_WIDTH, REQUESTED_QR_HEIGHT, hintMap);
//        // Make the BufferedImage that are to hold the QRCode
//        BufferedImage image = createImage(byteMatrix, "", "");
//        String qrImageCode = getImage(image);
//
//        return qrImageCode;
//    }
//
//    private String getImage(BufferedImage image) throws IOException {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream ();
//        ImageOutputStream stream = new MemoryCacheImageOutputStream(baos);
//        ImageIO.write(image, "png", stream);
//        stream.close();
//        return "data:image/png;base64," + Base64.getEncoder().encodeToString(baos.toByteArray());
//    }
//
//
//    private BufferedImage createImage(BitMatrix byteMatrix, String barcodeNumber, String number) {
//        int matrixWidth = byteMatrix.getWidth();
//        int matrixHeight = byteMatrix.getHeight();
//        BufferedImage image = new BufferedImage(matrixWidth, matrixHeight+20,
//                BufferedImage.TYPE_INT_RGB);
//        image.createGraphics();
//
//        Graphics2D graphics = (Graphics2D) image.getGraphics();
//        graphics.setColor(Color.WHITE);
//        graphics.fillRect(0, 0, matrixWidth, matrixHeight+40);
//        // Paint and save the image using the ByteMatrix
//        graphics.setColor(Color.BLACK);
//        graphics.drawString(barcodeNumber , matrixWidth/2, matrixHeight+15);
//        graphics.drawString(number , matrixWidth/3, 15);
//
//        for (int i = 0; i < matrixWidth; i++) {
//            for (int j = 20; j < matrixHeight; j++) {
//                if (byteMatrix.get(i, j)) {
//                    graphics.fillRect(i, j, 1, 1);
//                }
//            }
//        }
//        return image;
//    }
//}
