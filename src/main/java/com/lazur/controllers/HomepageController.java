package com.lazur.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomepageController {

//    @Autowired
//    private FinishService finishService;
//
//    @Autowired
//    private AccessoriesServiceImpl productService;

    @Autowired
    public HomepageController(/*ProductService productService*/) {
       /* this.productService = productService;*/
    }


    @GetMapping("")
    public String getHomePage(Model model){
        //this.finishService.save();
        //this.productService.save();
//        List<ProductViewBasicModel> productViewBasicModels = this.productService.findAllProductImages();
//        int randomId = ThreadLocalRandom.current().nextInt(0, productViewBasicModels.size());
//        model.addAttribute("product", productViewBasicModels.get(randomId));
        return "login";

    }
}
