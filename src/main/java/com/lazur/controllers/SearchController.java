//package com.lazur.controllers;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.web.PageableDefault;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//public class SearchController {
//
//    @Autowired
//    private ProductService productService;
//
//    @GetMapping("/products/search")
//    public String getProducts(@RequestParam(value = "searchedWord", required = true) String searchedWord,
//                              @PageableDefault(size = 9) Pageable pageable, Model model){
//        try {
//            Long productId = Long.valueOf(searchedWord);
//            ProductViewBasicModel productViewModel = this.productService.findOneById(productId);
//
//            model.addAttribute("product", productViewModel);
//            return "product-details";
//        }catch (NumberFormatException e){}
//
//        Page<ProductViewBasicModel> productViewModelList = this.productService.findAllByName(searchedWord,pageable);
//        model.addAttribute("products", productViewModelList);
//        return "product-type";
//    }
//}
