package com.lazur.controllers;

import com.lazur.exeptions.SpecialSubMaterialNotFound;
import com.lazur.models.materials.*;
import com.lazur.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class MaterialController {

    private static final String EMPTY_STRING = "";
    private static final String NAME = "name";
    private static final String TYPE = "type";
    private static final String MATERIALS = "materials";
    private static final String CURR_MATERIAL = "currMaterial";
    private static final String SPECIAL = "special";
    private static final String PRODUCT = "product";
    private static final String SUB_MATERIAL = "subMaterial";
    private static final String MATERIAL_ID = "materialId";
    private static final String MATERIAL = "material";
    private static final String TYPE_ID = "typeId";
    private static final String UPDATE_NAME = "updateName";
    private static final String ID = "id";
    private static final String SPECIAL_PRODUCT = "specialProduct";
    private static final String COLOR = "color";
    private static final String MANUFACTURER = "manufacturer";
    private static final String MANUF_CODE = "manufcode";
    private static final String FRAME = "frame";
    private static final String FINISH = "finish";
    private static final String TOP = "top";
    private static final String PRODUCTS = "products";
    private static final String TOPS = "tops";
    private static final String FRAMES = "frames";
    private static final String COLORS = "colors";
    private static final String MANUF_CODES = "manufCodes";
    private static final String SPECIFIC_PRODUCTS = "specificProducts";
    private static final String MANUFACTURERS = "manufacturers";
    private static final int PAGE_SIZE = 20;
    private static final String TITLE = "title";
    private static final String EDIT = "Edit";
    private static final String DELETE = "Delete";


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
        model.addAttribute(TITLE, MATERIALS);
        return "/materials/materials";
    }


    @GetMapping("/materials/{name}")
    public String getMaterialsPage(@PathVariable(NAME) String name,
                                  @PageableDefault(size = PAGE_SIZE) Pageable pageable, Model model) {

        Page<MaterialViewModel> materialViewModels = getMaterials(name, pageable);
        addMaterialsToModel(model);
        model.addAttribute(MATERIALS, materialViewModels);
        model.addAttribute(TYPE, name);
        if (!model.containsAttribute(CURR_MATERIAL)) {
            model.addAttribute(CURR_MATERIAL, new MaterialBindingModel());
        }

        model.addAttribute(TITLE, name);
        return "/materials/materials-create";

    }


    @GetMapping("/materials/special")
    public String getAddSpecialPage(@PageableDefault(size = PAGE_SIZE) Pageable pageable, Model model) {
        addMaterialsToModel(model);
        Page<SpecificMaterialViewBasicModel> materialViewModels = this.specificMaterialService.findAllPageable(pageable);
        model.addAttribute(MATERIALS, materialViewModels);
        getSpecificProductMaterials(model);
        if (!model.containsAttribute(SPECIAL)){
            model.addAttribute(SPECIAL, new SpecificBindingModel());
        }

        model.addAttribute(TITLE, MATERIALS);
        return "/materials/materials-special";

    }


    @GetMapping("/materials/{name}/{product}")
    public String getAddToMaterialPage(@PathVariable(NAME) String name,
                                       @PathVariable(PRODUCT) String product,
                                       @PageableDefault(size = PAGE_SIZE) Pageable pageable, Model model) {
        Page<MaterialViewModel> materialViewModels = getMaterialTypes(name, product, pageable);
        addMaterialsToModel(model);
        model.addAttribute(MATERIALS, materialViewModels);
        model.addAttribute(PRODUCT, product);
        model.addAttribute(TYPE, name);
        if (!model.containsAttribute(SUB_MATERIAL)) {
            model.addAttribute(SUB_MATERIAL, new TypeBindingModel());
        }

        model.addAttribute(TITLE, product);
        return "/materials/materials-add";

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/materials/edit/{name}/{materialId}")
    public String editMaterialPage(@PathVariable(NAME) String name,
                                   @PathVariable(MATERIAL_ID) Long materialId,
                                   @PageableDefault(size = PAGE_SIZE) Pageable pageable, Model model) {
        MaterialUpdateModel materialViewModel = this.materialService.findMaterialById(materialId);
        Page<MaterialViewModel> materialViewModels = getMaterials(name, pageable);
        addMaterialsToModel(model);
        model.addAttribute(MATERIALS, materialViewModels);
        model.addAttribute(TYPE, name);
        model.addAttribute(UPDATE_NAME, materialViewModel.getName());
        if (!model.containsAttribute(MATERIAL)) {
            model.addAttribute(MATERIAL, materialViewModel);
        }

        model.addAttribute(TITLE,String.format("%s %s",EDIT,name));
        return "/materials/materials-edit";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/materials/delete/{name}/{materialId}")
    public String deleteMaterialPage(@PathVariable(NAME) String name,
                                     @PathVariable(MATERIAL_ID) Long materialId,
                                     @PageableDefault(size = PAGE_SIZE) Pageable pageable, Model model) {
        MaterialUpdateModel materialViewModel = this.materialService.findMaterialById(materialId);
        Page<MaterialViewModel> materialViewModels = getMaterials(name, pageable);
        addMaterialsToModel(model);
        model.addAttribute(MATERIAL, materialViewModel);
        model.addAttribute(MATERIALS, materialViewModels);
        model.addAttribute(TYPE, name);
        model.addAttribute(TITLE,String.format("%s %s",DELETE,name));
        return "/materials/materials-delete";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/materials/edit/{name}/{product}/{typeId}")
    public String editMaterialTypePage(@PathVariable(NAME) String name,
                                       @PathVariable(PRODUCT) String product,
                                       @PathVariable(TYPE_ID) Long typeId,
                                       @PageableDefault(size = PAGE_SIZE) Pageable pageable, Model model) {

        Page<MaterialViewModel> materialViewModels = getMaterialTypes(name, product, pageable);
        addMaterialsToModel(model);
        if (!model.containsAttribute(MATERIAL)) {
            TypeViewModel typeViewModel = this.typeService.findOneById(typeId);
            model.addAttribute(MATERIAL, typeViewModel);
        }
        model.addAttribute(MATERIALS, materialViewModels);
        model.addAttribute(TYPE, name);
        model.addAttribute(TITLE,String.format("%s %s",EDIT,product));
        return "/materials/materials-edit-type";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/materials/delete/{name}/{product}/{typeId}")
    public String deleteMaterialTypePage(@PathVariable(NAME) String name,
                                         @PathVariable(PRODUCT) String product,
                                         @PathVariable(TYPE_ID) Long typeId,
                                         @PageableDefault(size = PAGE_SIZE) Pageable pageable, Model model) {
        TypeViewModel typeViewModel = this.typeService.findOneById(typeId);
        Page<MaterialViewModel> materialViewModels = getMaterialTypes(name, product, pageable);
        addMaterialsToModel(model);
        model.addAttribute(MATERIAL, typeViewModel);
        model.addAttribute(MATERIALS, materialViewModels);
        model.addAttribute(TYPE, name);
        model.addAttribute(TITLE,String.format("%s %s",DELETE,product));
        return "/materials/materials-delete-type";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/materials/special/edit/{id}")
    public String getEditSpecialPage(@PathVariable(ID) Long id,
                                     @PageableDefault(size = PAGE_SIZE) Pageable pageable,
                                     Model model) {
        SpecificMaterialViewBasicModel specificMaterialViewBasicModel = this.specificMaterialService.findOneById(id);
        Page<SpecificMaterialViewBasicModel> materialViewModels = this.specificMaterialService.findAllPageable(pageable);
        model.addAttribute(MATERIALS, materialViewModels);
        getSpecificProductMaterials(model);
        addMaterialsToModel(model);
        if (! model.containsAttribute(SPECIAL)) {
            model.addAttribute(SPECIAL, specificMaterialViewBasicModel);
        }

        model.addAttribute(TITLE,EDIT);
        return "/materials/materials-special-edit";

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/materials/special/delete/{id}")
    public String getDeleteSpecialPage(@PathVariable(ID) Long id,
                                       @PageableDefault(size = PAGE_SIZE) Pageable pageable,
                                       Model model) {
        SpecificMaterialViewBasicModel specificMaterialViewBasicModel = this.specificMaterialService.findOneById(id);
        addMaterialsToModel(model);
        Page<SpecificMaterialViewBasicModel> materialViewModels = this.specificMaterialService.findAllPageable(pageable);
        model.addAttribute(SPECIAL, specificMaterialViewBasicModel);
        model.addAttribute(MATERIALS, materialViewModels);
        getSpecificProductMaterials(model);
        model.addAttribute(TITLE,DELETE);
        return "/materials/materials-special-delete";

    }


    @GetMapping("/materials/special/{product}")
    public String getSubMaterialPage(@PathVariable(PRODUCT) String product,
                                     @PageableDefault(size = PAGE_SIZE) Pageable pageable, Model model) {
        getSpecialMaterial(product, pageable, model);
        addMaterialsToModel(model);
        if (! model.containsAttribute(SPECIAL)){
            model.addAttribute(SPECIAL, new SpecialSubMaterialBindingModel());
        }

        model.addAttribute(TYPE, product);
        model.addAttribute(TITLE,product);
        return "/materials/materials-add-special";

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/materials/special/edit/{product}/{id}")
    public String editSubMaterialPage(@PathVariable(PRODUCT) String product,
                                      @PathVariable(ID) Long id,
                                      @PageableDefault(size = PAGE_SIZE) Pageable pageable, Model model) {
        SpecialSubMaterialViewModel specialSubMaterialViewModel = getSpecialSubMaterial(product, id);
        getSpecialMaterial(product, pageable, model);
        addMaterialsToModel(model);
        if (!model.containsAttribute(SPECIAL)) {
            model.addAttribute(SPECIAL, specialSubMaterialViewModel);
        }

        model.addAttribute(TYPE, product);
        model.addAttribute(TITLE,String.format("%s %s",EDIT,product));
        return "/materials/materials-edit-special";

    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping("/materials/special/delete/{product}/{id}")
    public String deleteSubMaterialPage(@PathVariable(PRODUCT) String product,
                                        @PathVariable(ID) Long id,
                                        @PageableDefault(size = PAGE_SIZE) Pageable pageable, Model model) {
        SpecialSubMaterialViewModel specialSubMaterialViewModel = getSpecialSubMaterial(product, id);
        getSpecialMaterial(product, pageable, model);
        addMaterialsToModel(model);
        model.addAttribute(SPECIAL_PRODUCT, specialSubMaterialViewModel);
        model.addAttribute(TYPE, product);
        model.addAttribute(TITLE,String.format("%s %s",DELETE,product));
        return "/materials/materials-delete-special";

    }

    private SpecialSubMaterialViewModel getSpecialSubMaterial(String product, Long id) {
        SpecialSubMaterialViewModel specialSubMaterialViewModel = null;
        switch (product.toLowerCase()) {
            case PRODUCT:
                specialSubMaterialViewModel = this.specificProductService.findById(id);
                break;
            case COLOR:
                specialSubMaterialViewModel = this.colorService.findById(id);
                break;
            case MANUFACTURER:
                specialSubMaterialViewModel = this.manufacturerService.findById(id);
                break;
            case MANUF_CODE:
                specialSubMaterialViewModel = this.manufCodeService.findById(id);
                break;
            default:
                throw new SpecialSubMaterialNotFound();
        }

        return specialSubMaterialViewModel;
    }


    private Page<MaterialViewModel> getMaterials(String name, Pageable pageable) {
        Page<MaterialViewModel> materialViewModels = null;
        switch (name.toLowerCase()) {
            case FRAME:
                materialViewModels = this.materialService.findAllByMaterialPage(name, pageable);
                break;
            case FINISH:
                materialViewModels = this.materialService.findAllByMaterialPage(name, pageable);
                break;
            case TOP:
                materialViewModels = this.materialService.findAllByMaterialPage(name, pageable);
                break;
            default:

        }
        return materialViewModels;
    }


    private Page<MaterialViewModel> getMaterialTypes(String name, String product, Pageable pageable) {
        Page<MaterialViewModel> materialViewModels = null;
        switch (name.toLowerCase()) {
            case FRAME:
                materialViewModels = this.materialService.findAllByMaterialAndTypePage(name, product, pageable);
                break;
            case FINISH:
                materialViewModels = this.materialService.findAllByMaterialAndTypePage(name, product, pageable);
                break;
            case TOP:
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
        model.addAttribute(COLORS, specialSubMaterialViewModels);
        model.addAttribute(MANUFACTURERS, manufacturerViewModels);
        model.addAttribute(MANUF_CODES, manufCodeViewModels);
        model.addAttribute(SPECIFIC_PRODUCTS, specialProductViewModels);
    }


    private void addMaterialsToModel(Model model) {
        List<MaterialViewBasicModel> finishMateriaLlist = getMaterials(FINISH);
        List<MaterialViewBasicModel> frameMateriaLlist = getMaterials(FRAME);
        List<MaterialViewBasicModel> topMateriaLlist = getMaterials(TOP);
        model.addAttribute(FRAMES, frameMateriaLlist);
        model.addAttribute(FINISH, finishMateriaLlist);
        model.addAttribute(TOPS, topMateriaLlist);
    }

    private List<MaterialViewBasicModel> getMaterials(String material) {
        return this.materialService.findAllByMaterial(material, EMPTY_STRING);

    }

    private void getSpecialMaterial(String material, Pageable pageable, Model model) {
        Page<SpecialSubMaterialViewModel> specialSubMaterialViewModel = null;
        switch (material.toLowerCase()) {
            case PRODUCT:
                specialSubMaterialViewModel = this.specificProductService.findAllPageable(pageable);
                break;
            case COLOR:
                specialSubMaterialViewModel = this.colorService.findAllPageable(pageable);
                break;
            case MANUFACTURER:
                specialSubMaterialViewModel = this.manufacturerService.findAllPageable(pageable);
                break;
            case MANUF_CODE:
                specialSubMaterialViewModel = this.manufCodeService.findAllPageable(pageable);
                break;
            default:
                throw new SpecialSubMaterialNotFound();
        }

        model.addAttribute(PRODUCTS, specialSubMaterialViewModel);
    }

}
