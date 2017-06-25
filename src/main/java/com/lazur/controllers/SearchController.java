package com.lazur.controllers;

import com.lazur.models.products.ProductViewBasicModel;
import com.lazur.models.products.ProductViewDetailsModel;
import com.lazur.services.ProductService;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.krysalis.barcode4j.BarcodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class SearchController {

    private static final String PRODUCTS = "products";
    private static final String PRODUCT = "product";
    private static final String SKU = "SKU";
    private static final String NAME = "NAME";
    private static final String SEARCHED_WORD = "searchedWord";
    private static final String SEARCH_OPTIONS = "searchOptions";
    private static final int BARCODE_SIZE = 13;
    private static final int PAGE_SIZE = 9;

    @Autowired
    private ProductService productService;

    @GetMapping("/products/search")
    public String getProducts(@RequestParam(value = SEARCHED_WORD, required = false) String searchedWord,
                              @RequestParam(value = SEARCH_OPTIONS, required = false) String searchOptions,
                              @PageableDefault(size = PAGE_SIZE) Pageable pageable, Model model) throws BarcodeException, ConfigurationException, IOException {

        Page<ProductViewBasicModel> productViewBasicModels = null;
        if (searchOptions.equalsIgnoreCase(SKU)) {
            productViewBasicModels = this.productService.findAllBySku(searchedWord,pageable);
        }else if(searchOptions.equalsIgnoreCase(NAME)) {
            productViewBasicModels = this.productService.findAllByName(searchedWord, pageable);
        }else if(searchedWord.length() == BARCODE_SIZE){
            ProductViewDetailsModel productViewDetailsModel = this.productService.findProductByBarcodeNumber(searchedWord);
            if (productViewDetailsModel != null){
                model.addAttribute(PRODUCT, productViewDetailsModel);
                return "/products/product-details";
            }
        }

        model.addAttribute(PRODUCTS, productViewBasicModels);

        return "/products/products";
    }
}
