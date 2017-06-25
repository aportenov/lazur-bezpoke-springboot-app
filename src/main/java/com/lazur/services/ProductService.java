package com.lazur.services;

//import com.google.zxing.WriterException;
import com.google.zxing.WriterException;
import com.lazur.entities.materials.Material;
import com.lazur.entities.Product;
import com.lazur.models.products.ProductViewBasicModel;
import com.lazur.models.products.ProductBiningModel;
import com.lazur.models.products.ProductViewDetailsModel;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.krysalis.barcode4j.BarcodeException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface ProductService {

    void save(ProductBiningModel productBiningModel);

    Page<ProductViewBasicModel> findAllByTypeAndCategory(String modelName, String type, Pageable pageable);

    Page<ProductViewBasicModel> findByCategory(String product, Pageable pageable);

    Page<ProductViewBasicModel> findAllByTypeCategoryAndFamily(String family, String modelName, String currCategory, Pageable pageable);

    ProductViewDetailsModel findProductById(Long productId, HttpServletRequest request) throws IOException, BarcodeException, ConfigurationException, WriterException;

    void udpdate(long productId, ProductBiningModel productBiningModel);

    void delete(long productId);

    Page<ProductViewBasicModel> findAllBySku(String searchedWord, Pageable pageable);

    Page<ProductViewBasicModel> findAllByName(String searchedWord, Pageable pageable);

    Material getMaterial(String material, String type);

    Product findBySku(String sku);

    ProductBiningModel findProductViewById(Long productId);

    String addSpecificMaterialName(long specificMaterialId);

    ProductViewDetailsModel findProductByBarcodeNumber(String searchedWord) throws BarcodeException, ConfigurationException, IOException;
}









