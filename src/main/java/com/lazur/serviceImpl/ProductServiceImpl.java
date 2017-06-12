package com.lazur.serviceImpl;

import com.lazur.entities.*;
import com.lazur.entities.specific.SpecificMaterial;
import com.lazur.exeptions.*;
import com.lazur.models.view.ProductViewBasicModel;
import com.lazur.models.view.ProductBiningModel;
import com.lazur.models.view.ProductViewDetailsModel;
import com.lazur.repositories.ProductRepository;
import com.lazur.services.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ModelService modelService;
    private final MaterialService materialService;
    private final FamilyService familyService;
    private final SpecificMaterialService specificMaterialService;

    @Autowired
    public ProductServiceImpl(ModelMapper modelMapper,
                              ProductRepository productRepository,
                              CategoryService categoryService,
                              ModelService modelService,
                              MaterialService materialService,
                              FamilyService familyService,
                              SpecificMaterialService specificMaterialService) {
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.modelService = modelService;
        this.materialService = materialService;
        this.familyService = familyService;
        this.specificMaterialService = specificMaterialService;
    }

    @Override
    @Transactional
    public void save(ProductBiningModel productBiningModel) {
        Product product = new Product();
        product = mapProduct(product, productBiningModel);
        this.productRepository.save(product);
    }

    @Override
    public Page<ProductViewBasicModel> findAllByTypeCategoryAndFamily( String modelName, String category,String family, Pageable pageable) {
        Page<Product> productList = this.productRepository.findAllByFamilyModelAndCategory(family,modelName, category, pageable);
        List<ProductViewBasicModel> productViewBasicModels = new ArrayList<>();
        for (Product product : productList) {
            ProductViewBasicModel productViewBasicModel = this.modelMapper.map(product, ProductViewBasicModel.class);
            productViewBasicModels.add(productViewBasicModel);
        }

        return new PageImpl<>(productViewBasicModels, pageable, productList.getTotalElements());
    }

    @Override
    public ProductViewDetailsModel findProductById(Long productId) {
        Product product = this.productRepository.findOne(productId);
        if (product == null){
            //throw new ProductNotFoundExeption();
        }

        ProductViewDetailsModel productViewDetailsModel = this.modelMapper.map(product, ProductViewDetailsModel.class);
        Material finish = product.getFinish();
        Material frame = product.getFrame();
        Material top = product.getTop();
        SpecificMaterial specificMaterial = product.getSpecificMaterial();
        productViewDetailsModel.setFinishMaterial(finish.getMaterial());
        productViewDetailsModel.setFinishType(finish.getTypes().stream().findFirst().orElse(null).getName());
        productViewDetailsModel.setFrameMaterial(frame.getMaterial());
        productViewDetailsModel.setFrameType(frame.getTypes().stream().findFirst().orElse(null).getName());
        productViewDetailsModel.setTopMaterial(top.getMaterial());
        productViewDetailsModel.setTopType(top.getTypes().stream().findFirst().orElse(null).getName());
        String specificMaterialConcat = String.format("%s,  %s,  %s,  %s", specificMaterial.getSpecificProduct().getName(),
                specificMaterial.getColor().getName(),
                specificMaterial.getManufacturer().getName(),
                specificMaterial.getManufCode().getName());
        productViewDetailsModel.setSpecificMaterial(specificMaterialConcat);
        productViewDetailsModel.setSpecificMaterialId(specificMaterial.getId());
        return productViewDetailsModel;
    }

    @Override
    public void udpdate(long productId, ProductBiningModel productBiningModel) {
        Product product = this.productRepository.findOne(productId);
        if (product == null){
//            throw new ProductNotFoundExeption();
        }

        product = mapProduct(product, productBiningModel);
        this.productRepository.save(product);
    }

    @Override
    public void delete(long productId) {
        Product product = this.productRepository.findOne(productId);
        if (product == null){
//            throw new ProductNotFoundExeption();
        }

        this.productRepository.delete(product);
    }

    @Override
    public Page<ProductViewBasicModel> findAllByTypeAndCategory(String modelName, String category, Pageable pageable) {
        Page<Product> productList = this.productRepository.findAllByModelAndCategory(modelName,category, pageable);
        List<ProductViewBasicModel> productViewBasicModels = new ArrayList<>();
        for (Product product : productList) {
            ProductViewBasicModel productViewBasicModel = this.modelMapper.map(product, ProductViewBasicModel.class);
            productViewBasicModels.add(productViewBasicModel);
        }

        return new PageImpl<>(productViewBasicModels, pageable, productList.getTotalElements());
    }

    @Override
    public Page<ProductViewBasicModel> findByCategory(String category, Pageable pageable) {
        Page<Product> products = this.productRepository.findAllByCategory(category, pageable);
        List<ProductViewBasicModel> productViewBasicModels = new ArrayList<>();
        for (Product product : products) {
            ProductViewBasicModel productViewBasicModel = this.modelMapper.map(product, ProductViewBasicModel.class);
            productViewBasicModels.add(productViewBasicModel);
        }

        return new PageImpl<>(productViewBasicModels, pageable, products.getTotalElements());
    }


    private Material getMaterial(String material, String type) {
        return this.materialService.findByMaterialAndType(material, type);
    }


    private Product mapProduct(Product product, ProductBiningModel productBiningModel) {
        product.setName(productBiningModel.getName());
        product.setBarcodeEU(productBiningModel.getBarcodeEU());
        product.setBarcodeUS(productBiningModel.getBarcodeUS());
        product.setDescription(productBiningModel.getDescription());
        product.setHeight(productBiningModel.getHeight());
        product.setWidth(productBiningModel.getWidth());
        product.setWeight(productBiningModel.getWeight());
        product.setLength(productBiningModel.getLength());
        product.setImage(productBiningModel.getImage());
        Category category = this.categoryService.findCategory(productBiningModel.getCategoryName());
        product.setCategory(category);
        Model model = this.modelService.findByCategoryAndModel(productBiningModel.getCategoryName(), productBiningModel.getModelName());
        product.setModel(model);
        Family family = this.familyService.findFamily(productBiningModel.getFamilyName(),
                productBiningModel.getModelName(),
                productBiningModel.getCategoryName());
        product.setFamily(family);
        Material finish = getMaterial(productBiningModel.getFinishMaterial(), productBiningModel.getFinishType());
        product.setFinish(finish);
        Material frame = getMaterial(productBiningModel.getFrameMaterial(), productBiningModel.getFrameType());
        product.setFrame(frame);
        Material top = getMaterial(productBiningModel.getTopMaterial(), productBiningModel.getTopType());
        product.setTop(top);
        SpecificMaterial specificMaterial = this.specificMaterialService.findEntityById(productBiningModel.getSpecificMaterial());
        product.setSpecificMaterial(specificMaterial);

        return product;
    }

}
