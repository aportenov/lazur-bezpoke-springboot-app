package com.lazur.controllers;

import com.lazur.models.view.ProductViewBasicModel;
import com.lazur.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products/search")
    public String getProducts(@RequestParam(value = "searchedWord", required = false) String searchedWord,
                              @RequestParam(value = "searchOptions", required = false) String searchOptions,
                              @PageableDefault(size = 9) Pageable pageable, Model model){

        Page<ProductViewBasicModel> productViewBasicModels = null;
        if (searchOptions.equalsIgnoreCase("SKU")) {
            productViewBasicModels = this.productService.findAllBySku(searchedWord,pageable);
        }else if(searchOptions.equalsIgnoreCase("NAME")) {
            productViewBasicModels = this.productService.findAllByName(searchedWord, pageable);
        }

        model.addAttribute("products", productViewBasicModels);

        return "/products/products";
    }
}
