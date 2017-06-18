package com.lazur.controllers;

import com.lazur.models.view.*;
import com.lazur.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class MaterialController {


    private final MaterialService materialService;
    private final ColorService colorService;
    private final ManufacturerService manufacturerService;
    private final ManufCodeService manufCodeService;
    private final SpecificProductService specificProductService;
    private final SpecificMaterialService specificMaterialService;
    private final TypeService typeService;

    @Autowired
    public MaterialController(MaterialService materialService,
                              ColorService colorService,
                              ManufacturerService manufacturerService,
                              ManufCodeService manufCodeService,
                              SpecificProductService specificProductService,
                              SpecificMaterialService specificMaterialService,
                              TypeService typeService) {
        this.materialService = materialService;
        this.colorService = colorService;
        this.manufacturerService = manufacturerService;
        this.manufCodeService = manufCodeService;
        this.specificProductService = specificProductService;

        this.specificMaterialService = specificMaterialService;
        this.typeService = typeService;
    }


    @GetMapping("/materials")
    public String getMaterialsPage(Model model) {
        addMaterialsToModel(model);
        return "/materials/materials";
    }


    @GetMapping("/materials/{name}")
    public String getAddFramePage(@PathVariable("name") String name,
                                  @PageableDefault(size = 20) Pageable pageable, Model model) {

        Page<MaterialViewModel> materialViewModels = getMaterials(name, pageable);
        addMaterialsToModel(model);
        model.addAttribute("materials", materialViewModels);
        model.addAttribute("type", name);
        if (! model.containsAttribute("currMaterial")){
            model.addAttribute("currMaterial", new MaterialBindingModel());
        }

        return "/materials/materials-create";

    }


    @GetMapping("/materials/special")
    public String getAddSpecialPage(@PageableDefault(size = 20) Pageable pageable, Model model) {
        addMaterialsToModel(model);
        Page<SpecificMaterialViewBasicModel> materialViewModels = this.specificMaterialService.findAllPageable(pageable);
        model.addAttribute("materials", materialViewModels);
        getSpecificProductMaterials(model);
        return "/materials/materials-special";

    }


    @GetMapping("/materials/{name}/{product}")
    public String getAddToMaterialPage(@PathVariable("name") String name,
                                       @PathVariable("product") String product,
                                       @PageableDefault(size = 20) Pageable pageable, Model model) {
        Page<MaterialViewModel> materialViewModels = getMaterialTypes(name, product, pageable);
        addMaterialsToModel(model);
        model.addAttribute("materials", materialViewModels);
        model.addAttribute("product", product);
        model.addAttribute("type", name);
        return "/materials/materials-add";

    }

    @GetMapping("/materials/edit/{name}/{materialId}")
    public String editMaterialPage(@PathVariable("name") String name,
                                   @PathVariable("materialId") Long materialId,
                                   @PageableDefault(size = 20) Pageable pageable, Model model) {
        MaterialUpdateModel materialViewModel = this.materialService.findMaterialById(materialId);
        Page<MaterialViewModel> materialViewModels = getMaterials(name, pageable);
        addMaterialsToModel(model);
        model.addAttribute("material", materialViewModel);
        model.addAttribute("materials", materialViewModels);
        model.addAttribute("type", name);
        return "/materials/materials-edit";
    }

    @GetMapping("/materials/delete/{name}/{materialId}")
    public String deleteMaterialPage(@PathVariable("name") String name,
                                   @PathVariable("materialId") Long materialId,
                                   @PageableDefault(size = 20) Pageable pageable, Model model) {
        MaterialUpdateModel materialViewModel = this.materialService.findMaterialById(materialId);
        Page<MaterialViewModel> materialViewModels = getMaterials(name, pageable);
        addMaterialsToModel(model);
        model.addAttribute("material", materialViewModel);
        model.addAttribute("materials", materialViewModels);
        model.addAttribute("type", name);
        return "/materials/materials-delete";
    }

    @GetMapping("/materials/edit/{name}/{product}/{typeId}")
    public String editMaterialTypePage(@PathVariable("name") String name,
                                       @PathVariable("product") String product,
                                       @PathVariable("typeId") Long typeId,
                                       @PageableDefault(size = 20) Pageable pageable, Model model) {
        TypeViewModel typeViewModel = this.typeService.findOneById(typeId);
        Page<MaterialViewModel> materialViewModels = getMaterialTypes(name,product, pageable);
        addMaterialsToModel(model);
        model.addAttribute("material", typeViewModel);
        model.addAttribute("materials", materialViewModels);
        model.addAttribute("type", name);
        return "/materials/materials-edit-type";
    }

    @GetMapping("/materials/delete/{name}/{product}/{typeId}")
    public String deleteMaterialTypePage(@PathVariable("name") String name,
                                       @PathVariable("product") String product,
                                       @PathVariable("typeId") Long typeId,
                                       @PageableDefault(size = 20) Pageable pageable, Model model) {
        TypeViewModel typeViewModel = this.typeService.findOneById(typeId);
        Page<MaterialViewModel> materialViewModels = getMaterialTypes(name,product, pageable);
        addMaterialsToModel(model);
        model.addAttribute("material", typeViewModel);
        model.addAttribute("materials", materialViewModels);
        model.addAttribute("type", name);
        return "/materials/materials-delete-type";
    }

    @GetMapping("/materials/special/edit/{id}")
    public String getEditSpecialPage(@PathVariable("id") Long id,
                                     @PageableDefault(size = 20) Pageable pageable,
                                     Model model) {
        SpecificMaterialViewBasicModel specificMaterialViewBasicModel = this.specificMaterialService.findOneById(id);
        addMaterialsToModel(model);
        Page<SpecificMaterialViewBasicModel> materialViewModels = this.specificMaterialService.findAllPageable(pageable);
        model.addAttribute("material", specificMaterialViewBasicModel);
        model.addAttribute("materials", materialViewModels);
        getSpecificProductMaterials(model);
        return "/materials/materials-special-edit";

    }

    @GetMapping("/materials/special/delete/{id}")
    public String getDeleteSpecialPage(@PathVariable("id") Long id,
                                     @PageableDefault(size = 20) Pageable pageable,
                                     Model model) {
        SpecificMaterialViewBasicModel specificMaterialViewBasicModel = this.specificMaterialService.findOneById(id);
        addMaterialsToModel(model);
        Page<SpecificMaterialViewBasicModel> materialViewModels = this.specificMaterialService.findAllPageable(pageable);
        model.addAttribute("material", specificMaterialViewBasicModel);
        model.addAttribute("materials", materialViewModels);
        getSpecificProductMaterials(model);
        return "/materials/materials-special-delete";

    }


    @GetMapping("/materials/special/{product}")
    public String getSubMaterialPage(@PathVariable("product") String product,
                                       @PageableDefault(size = 20) Pageable pageable, Model model) {
        getSpecialMaterial(product, pageable,model);
        addMaterialsToModel(model);
        model.addAttribute("type", product);
        return "/materials/materials-add-special";

    }

    @GetMapping("/materials/special/edit/{product}/{id}")
    public String editSubMaterialPage(@PathVariable("product") String product,
                                      @PathVariable("id") Long id,
                                      @PageableDefault(size = 20) Pageable pageable, Model model) {
        SpecialSubMaterialViewModel specialSubMaterialViewModel = getSpecialSubMaterial(product,id);
        getSpecialMaterial(product, pageable,model);
        addMaterialsToModel(model);
        model.addAttribute("specialProduct",specialSubMaterialViewModel);
        model.addAttribute("type", product);
        return "/materials/materials-edit-special";

    }

    @GetMapping("/materials/special/delete/{product}/{id}")
    public String deleteSubMaterialPage(@PathVariable("product") String product,
                                      @PathVariable("id") Long id,
                                      @PageableDefault(size = 20) Pageable pageable, Model model) {
        SpecialSubMaterialViewModel specialSubMaterialViewModel = getSpecialSubMaterial(product,id);
        getSpecialMaterial(product, pageable,model);
        addMaterialsToModel(model);
        model.addAttribute("specialProduct",specialSubMaterialViewModel);
        model.addAttribute("type", product);
        return "/materials/materials-delete-special";

    }

    private SpecialSubMaterialViewModel getSpecialSubMaterial(String product, Long id) {
        SpecialSubMaterialViewModel specialSubMaterialViewModel = null;
        switch (product.toLowerCase()){
            case "product":
                specialSubMaterialViewModel = this.specificProductService.findById(id);
                break;
            case "color":
                specialSubMaterialViewModel = this.colorService.findById(id);
                break;
            case "manufacturer":
                specialSubMaterialViewModel = this.manufacturerService.findById(id);
                break;
            case "manufcode":
                specialSubMaterialViewModel = this.manufCodeService.findById(id);
                break;
            default:
                //throw SpecialSubMaterialNotFound();
        }

        return specialSubMaterialViewModel;
    }


    private Page<MaterialViewModel> getMaterials(String name, Pageable pageable) {
        Page<MaterialViewModel> materialViewModels = null;
        switch (name.toLowerCase()) {
            case "frame":
                materialViewModels = this.materialService.findAllByMaterialPage(name, pageable);
                break;
            case "finish":
                materialViewModels = this.materialService.findAllByMaterialPage(name, pageable);
                break;
            case "top":
                materialViewModels = this.materialService.findAllByMaterialPage(name, pageable);
                break;
            default:

        }
        return materialViewModels;
    }


    private Page<MaterialViewModel> getMaterialTypes(String name, String product, Pageable pageable) {
        Page<MaterialViewModel> materialViewModels = null;
        switch (name.toLowerCase()) {
            case "frame":
                materialViewModels = this.materialService.findAllByMaterialAndTypePage(name, product, pageable);
                break;
            case "finish":
                materialViewModels = this.materialService.findAllByMaterialAndTypePage(name, product, pageable);
                break;
            case "top":
                materialViewModels = this.materialService.findAllByMaterialAndTypePage(name, product, pageable);
                break;
            default:
        }

        return materialViewModels;
    }

    private void getSpecificProductMaterials(Model model) {
        List<SpecialSubMaterialViewModel> specialSubMaterialViewModels = this.colorService.findAll();
        List<SpecialSubMaterialViewModel> specialProductViewModels = this.specificProductService.findAllProducts();
        List<SpecialSubMaterialViewModel> manufacturerViewModels = this.manufacturerService.findAllManufactorers();
        List<SpecialSubMaterialViewModel> manufCodeViewModels = this.manufCodeService.findAllManufCodes();
        model.addAttribute("colors", specialSubMaterialViewModels);
        model.addAttribute("manufactorers", manufacturerViewModels);
        model.addAttribute("manufCodes", manufCodeViewModels);
        model.addAttribute("specificProducts", specialProductViewModels);
    }


    private void addMaterialsToModel(Model model) {
        List<MaterialViewBasicModel> finishMateriaLlist = getMaterials("finish");
        List<MaterialViewBasicModel> frameMateriaLlist = getMaterials("frame");
        List<MaterialViewBasicModel> topMateriaLlist = getMaterials("top");
        model.addAttribute("frames", frameMateriaLlist);
        model.addAttribute("finish", finishMateriaLlist);
        model.addAttribute("tops", topMateriaLlist);
    }

    private List<MaterialViewBasicModel> getMaterials(String material) {
        return this.materialService.findAllByMaterial(material, "");

    }

    private void getSpecialMaterial(String material, Pageable pageable, Model model){
        Page<SpecialSubMaterialViewModel> specialSubMaterialViewModel = null;
        switch (material.toLowerCase()){
            case "product":
                specialSubMaterialViewModel = this.specificProductService.findAllPageable(pageable);
                break;
            case "color":
                specialSubMaterialViewModel = this.colorService.findAllPageable(pageable);
                break;
            case "manufacturer":
                specialSubMaterialViewModel = this.manufacturerService.findAllPageable(pageable);
                break;
            case "manufcode":
                specialSubMaterialViewModel = this.manufCodeService.findAllPageable(pageable);
                break;
            default:
                //throw SpecialSubMaterialNotFound();
        }

        model.addAttribute("products", specialSubMaterialViewModel);
    }

}
