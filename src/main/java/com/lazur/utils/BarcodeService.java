package com.lazur.utils;

import com.lazur.entities.Product;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.krysalis.barcode4j.BarcodeException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public interface BarcodeService {

    String getEAN13Barcode(String barcode, String sku) throws IOException, BarcodeException, ConfigurationException;

    String getSKUNumber(Product product);

}
