package com.lazur.controllers;

//import com.google.zxing.WriterException;
import com.google.zxing.WriterException;
import com.lazur.models.view.*;
import com.lazur.services.*;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.krysalis.barcode4j.BarcodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final MaterialService materialService;
    private final SpecificMaterialService specificMaterialService;

    @Autowired
    public ProductController(ProductService productService,
                             CategoryService categoryService,
                             MaterialService materialService, SpecificMaterialService specificMaterialService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.materialService = materialService;
        this.specificMaterialService = specificMaterialService;
    }

    @GetMapping("/products")
    public String getCategoryPage(Model model){
        List<CategoryViewModel> categoryList = this.categoryService.getCategories();
        model.addAttribute("categories", categoryList);
        return "/products/products";
    }

    @GetMapping("/products/create")
    public String addProductPage(Model model) {
        addMaterials(model);
        if (!model.containsAttribute("product")) {
            model.addAttribute("product" , new ProductBiningModel());
        }

        return "/products/add-product";

    }

    @GetMapping("/products/{category}")
    public String getProductByCategoryPage(@PathVariable("category") String category,
                                           @PageableDefault(size = 9) Pageable pageable, Model model){

        Page<ProductViewBasicModel> productPage = this.productService.findByCategory(category, pageable);
        List<CategoryViewModel> categoryList = this.categoryService.getCategories();
        model.addAttribute("categories", categoryList);
        model.addAttribute("products", productPage);
        return "/products/products";
    }

    @GetMapping("/categories/edit/{category}/{modelName}/{family}/{product}/{productId}")
    public String editProduct(@PathVariable("category")String category,
                              @PathVariable("modelName")String modelName,
                              @PathVariable("family")String family,
                              @PathVariable("product")String product,
                              @PathVariable("productId")Long productId,
                              HttpServletRequest request,
                              Model model) throws IOException, BarcodeException, ConfigurationException, WriterException {
        ProductViewDetailsModel productBiningModel = this.productService.findProductById(productId, request);
        addMaterials(model);
        model.addAttribute("product", productBiningModel);
        return "/products/edit-product";
    }

    @GetMapping("/categories/delete/{category}/{modelName}/{family}/{product}/{productId}")
    public String deleteProduct(@PathVariable("category")String category,
                              @PathVariable("modelName")String modelName,
                              @PathVariable("family")String family,
                              @PathVariable("product")String product,
                              @PathVariable("productId")Long productId,
                               HttpServletRequest request,
                               Model model) throws IOException, BarcodeException, ConfigurationException, WriterException {
        ProductViewDetailsModel productBiningModel = this.productService.findProductById(productId, request);
        addMaterials(model);
        model.addAttribute("product", productBiningModel);
        return "/products/delete-product";
    }


    @GetMapping("/products/{category}/{modelName}")
    public String getProductByModelPage(@PathVariable("modelName")String modelName,
                                        @PathVariable("category") String category,
                                        @PageableDefault(size = 9) Pageable pageable, Model model){
        Page<ProductViewBasicModel> productPage = this.productService.findAllByTypeAndCategory(modelName,category, pageable);
        List<CategoryViewModel> categoryList = this.categoryService.getCategories();
        model.addAttribute("categories", categoryList);
        model.addAttribute("products", productPage);
        return "/products/products";

    }


    @GetMapping("/products/{category}/{modelName}/{family}")
    public String getProductByModelPage(@PathVariable("modelName")String modelName,
                                        @PathVariable("category") String category,
                                        @PathVariable("family") String family,
                                        @PageableDefault(size = 9) Pageable pageable, Model model){
        Page<ProductViewBasicModel> productPage = this.productService.findAllByTypeCategoryAndFamily(modelName,category, family, pageable);
        List<CategoryViewModel> categoryList = this.categoryService.getCategories();
        model.addAttribute("categories", categoryList);
        model.addAttribute("products", productPage);
        return "/products/products";
    }


    @PostMapping("/products/add")
    public String addProduct(@RequestParam("file") MultipartFile file,
                             @Valid @ModelAttribute ProductBiningModel productBiningModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) throws IOException {
        if (!file.isEmpty()) {
            String image = "data:image/png;base64," + Base64.getEncoder().encodeToString(file.getBytes());
            productBiningModel.setImage(image);
        }

        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.product", bindingResult);
            redirectAttributes.addFlashAttribute("product", productBiningModel);
            return String.format("redirect:/products/create");
        }

        this.productService.save(productBiningModel);
        return "redirect:/products/products";
    }

    @GetMapping("/product/details/{productId}")
    public String getProductDetailsPage(@PathVariable("productId") long productId,
                                        HttpServletRequest request,
                                        Model model) throws IOException, BarcodeException, ConfigurationException, WriterException {
        ProductViewDetailsModel productViewDetailsModel = this.productService.findProductById(productId, request);
        model.addAttribute("product", productViewDetailsModel);

        return "products/product-details";
    }

    @PostMapping("/product/update/{productId}")
    public String updateProductPage(@PathVariable("productId") long productId,
                                    @RequestParam("file") MultipartFile file,
                                    @ModelAttribute ProductBiningModel productBiningModel) throws IOException {
       if (!file.isEmpty()) {
        String image = "data:image/png;base64," + Base64.getEncoder().encodeToString(file.getBytes());
        productBiningModel.setImage(image);
    }

//    if (productBiningModel has errors)

        this.productService.udpdate(productId, productBiningModel);
        return "redirect:/products/product-details";
    }

    @PostMapping("/product/delete/{productId}")
    public String updateProductPage(@PathVariable("productId") long productId){
        this.productService.delete(productId);
        return "redirect:/products/product-details";
    }



    private void addMaterials(Model model) {
        List<MaterialViewBasicModel> finishViewModels = this.materialService.findAllByMaterial("finish", "none");
        List<MaterialViewBasicModel> materialViewBasicModels = this.materialService.findAllByMaterial("frame", "none");
        List<MaterialViewBasicModel> topMaterialViewBasicModels = this.materialService.findAllByMaterial("top", "none");
        List<SpecificMaterialViewBasicModel> specificMaterialViewModels = this.specificMaterialService.findAll();
        List<CategoryViewModel> categoryList = this.categoryService.getCategories();

        model.addAttribute("categories", categoryList);
        model.addAttribute("finishes", finishViewModels);
        model.addAttribute("frames", materialViewBasicModels);
        model.addAttribute("tops", topMaterialViewBasicModels);
        model.addAttribute("specificMaterials", specificMaterialViewModels);
    }
}
