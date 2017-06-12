package com.lazur.services;

import com.lazur.models.view.ProductViewBasicModel;
import com.lazur.models.view.ProductBiningModel;
import com.lazur.models.view.ProductViewDetailsModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {

    void save(ProductBiningModel productBiningModel);

    Page<ProductViewBasicModel> findAllByTypeAndCategory(String modelName, String type, Pageable pageable);

    Page<ProductViewBasicModel> findByCategory(String product, Pageable pageable);

    Page<ProductViewBasicModel> findAllByTypeCategoryAndFamily(String family, String modelName, String currCategory, Pageable pageable);

    ProductViewDetailsModel findProductById(Long productId);

    void udpdate(long productId, ProductBiningModel productBiningModel);

    void delete(long productId);
}







