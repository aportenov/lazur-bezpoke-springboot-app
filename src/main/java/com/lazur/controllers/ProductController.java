package com.lazur.controllers;

//import com.google.zxing.WriterException;

import com.google.zxing.WriterException;
import com.lazur.entities.specific.SpecificMaterial;
import com.lazur.models.categories.CategoryViewModel;
import com.lazur.models.materials.MaterialViewBasicModel;
import com.lazur.models.materials.SpecificMaterialViewBasicModel;
import com.lazur.models.products.ProductBiningModel;
import com.lazur.models.products.ProductViewBasicModel;
import com.lazur.models.products.ProductViewDetailsModel;
import com.lazur.models.view.*;
import com.lazur.services.*;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.krysalis.barcode4j.BarcodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
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

    private static final String PRODUCT = "product";
    private static final String PRODUCTS = "products";
    private static final String MODEL_NAME = "modelName";
    private static final String FINISH = "finish";
    private static final String FRAME = "frame";
    private static final String TOP = "top";
    private static final String CATEGORIES = "categories";
    private static final String NONE = "none";
    private static final String FINISHES = "finishes";
    private static final String TOPS = "tops";
    private static final String FILE = "file";
    private static final String FRAMES = "frames";
    private static final String SPECIFIC_MATERIALS = "specificMaterials";
    private static final String CATEGORY = "category";
    private static final String FAMILY = "family";
    private static final String PRODUCT_ID = "productId";
    private static final int PAGE_SIZE = 9;
    private static final int SIZE_ZERO = 0;
    private static final String BASE64_PNG = "data:image/png;base64,";

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
    public String getCategoryPage(Model model) {
        List<CategoryViewModel> categoryList = this.categoryService.getCategories();
        model.addAttribute(CATEGORIES, categoryList);
        return "/products/products";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/products/create")
    public String addProductPage(Model model) {
        addMaterials(model);
        if (!model.containsAttribute(PRODUCT)) {
            model.addAttribute(PRODUCT, new ProductBiningModel());
        }

        return "/products/add-product";

    }

    @GetMapping("/products/{category}")
    public String getProductByCategoryPage(@PathVariable(CATEGORY) String category,
                                           @PageableDefault(size = PAGE_SIZE) Pageable pageable, Model model) {

        Page<ProductViewBasicModel> productPage = this.productService.findByCategory(category, pageable);
        List<CategoryViewModel> categoryList = this.categoryService.getCategories();
        model.addAttribute(CATEGORIES, categoryList);
        model.addAttribute(PRODUCTS, productPage);
        return "/products/products";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/categories/edit/{category}/{modelName}/{family}/{product}/{productId}")
    public String editProduct(@PathVariable(CATEGORY) String category,
                              @PathVariable(MODEL_NAME) String modelName,
                              @PathVariable(FAMILY) String family,
                              @PathVariable(PRODUCT) String product,
                              @PathVariable(PRODUCT_ID) Long productId,
                              Model model) throws IOException, BarcodeException, ConfigurationException, WriterException {
        addMaterials(model);
        if (!model.containsAttribute(PRODUCT)) {
            ProductBiningModel productBiningModel = this.productService.findProductViewById(productId);
            model.addAttribute(PRODUCT, productBiningModel);
        }

        return "/products/edit-product";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/categories/delete/{category}/{modelName}/{family}/{product}/{productId}")
    public String deleteProduct(@PathVariable(CATEGORY) String category,
                                @PathVariable(MODEL_NAME) String modelName,
                                @PathVariable(FAMILY) String family,
                                @PathVariable(PRODUCT) String product,
                                @PathVariable(PRODUCT_ID) Long productId,
                                Model model) throws IOException, BarcodeException, ConfigurationException, WriterException {
        ProductBiningModel productBiningModel = this.productService.findProductViewById(productId);
        addMaterials(model);
        model.addAttribute(PRODUCT, productBiningModel);
        return "/products/delete-product";
    }


    @GetMapping("/products/{category}/{modelName}")
    public String getProductByModelPage(@PathVariable(MODEL_NAME) String modelName,
                                        @PathVariable(CATEGORY) String category,
                                        @PageableDefault(size = PAGE_SIZE) Pageable pageable, Model model) {
        Page<ProductViewBasicModel> productPage = this.productService.findAllByTypeAndCategory(modelName, category, pageable);
        List<CategoryViewModel> categoryList = this.categoryService.getCategories();
        model.addAttribute(CATEGORIES, categoryList);
        model.addAttribute(PRODUCTS, productPage);
        return "/products/products";

    }


    @GetMapping("/products/{category}/{modelName}/{family}")
    public String getProductByModelPage(@PathVariable(MODEL_NAME) String modelName,
                                        @PathVariable(CATEGORY) String category,
                                        @PathVariable(FAMILY) String family,
                                        @PageableDefault(size = PAGE_SIZE) Pageable pageable, Model model) {
        Page<ProductViewBasicModel> productPage = this.productService.findAllByTypeCategoryAndFamily(modelName, category, family, pageable);
        List<CategoryViewModel> categoryList = this.categoryService.getCategories();
        model.addAttribute(CATEGORIES, categoryList);
        model.addAttribute(PRODUCTS, productPage);
        return "/products/products";
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/products/add")
    public String addProduct(@RequestParam(FILE) MultipartFile file,
                             @Valid @ModelAttribute ProductBiningModel productBiningModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) throws IOException {
        if (!file.isEmpty()) {
            String image = BASE64_PNG + Base64.getEncoder().encodeToString(file.getBytes());
            productBiningModel.setImage(image);
        }

        if (bindingResult.hasErrors()) {
            if (productBiningModel.getSpecificMaterialId() != null) {
                String specificMaterial = this.productService.addSpecificMaterialName(productBiningModel.getSpecificMaterialId());
                productBiningModel.setSpecificMaterialName(specificMaterial);
            }

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.product", bindingResult);
            redirectAttributes.addFlashAttribute(PRODUCT, productBiningModel);
            return String.format("redirect:/products/create");
        }

        this.productService.save(productBiningModel);
        return "redirect:/products/products";
    }

    @GetMapping("/product/details/{productId}")
    public String getProductDetailsPage(@PathVariable(PRODUCT_ID) long productId,
                                        HttpServletRequest request,
                                        Model model) throws IOException, BarcodeException, ConfigurationException, WriterException {
        ProductViewDetailsModel productViewDetailsModel = this.productService.findProductById(productId, request);
        model.addAttribute(PRODUCT, productViewDetailsModel);
        return "products/product-details";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/product/update/{productId}")
    public String updateProductPage(@PathVariable(PRODUCT_ID) long productId,
                                    @RequestParam(FILE) MultipartFile file,
                                    @Valid @ModelAttribute ProductBiningModel productBiningModel,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) throws IOException {
        if (!file.isEmpty()) {
            String image = BASE64_PNG + Base64.getEncoder().encodeToString(file.getBytes());
            productBiningModel.setImage(image);
        }

        if (bindingResult.hasErrors()) {
            if (productBiningModel.getSpecificMaterialId() != SIZE_ZERO) {
                String specificMaterial = this.productService.addSpecificMaterialName(productBiningModel.getSpecificMaterialId());
                productBiningModel.setSpecificMaterialName(specificMaterial);
            }

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.product", bindingResult);
            redirectAttributes.addFlashAttribute(PRODUCT, productBiningModel);
            return String.format("redirect:/categories/edit/category/model/family/product/%d", productId);
        }

        this.productService.udpdate(productId, productBiningModel);
        return "redirect:/products/product-details";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/product/delete/{productId}")
    public String updateProductPage(@PathVariable(PRODUCT_ID) long productId) {
        this.productService.delete(productId);
        return "redirect:/products/product-details";
    }


    private void addMaterials(Model model) {
        List<MaterialViewBasicModel> finishViewModels = this.materialService.findAllByMaterial(FINISH, NONE);
        List<MaterialViewBasicModel> materialViewBasicModels = this.materialService.findAllByMaterial(FRAME, NONE);
        List<MaterialViewBasicModel> topMaterialViewBasicModels = this.materialService.findAllByMaterial(TOP, NONE);
        List<SpecificMaterialViewBasicModel> specificMaterialViewModels = this.specificMaterialService.findAll();
        List<CategoryViewModel> categoryList = this.categoryService.getCategories();

        model.addAttribute(CATEGORIES, categoryList);
        model.addAttribute(FINISHES, finishViewModels);
        model.addAttribute(FRAMES, materialViewBasicModels);
        model.addAttribute(TOPS, topMaterialViewBasicModels);
        model.addAttribute(SPECIFIC_MATERIALS, specificMaterialViewModels);
    }
}
