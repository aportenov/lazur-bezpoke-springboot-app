package com.lazur.controllers;

import com.lazur.models.materials.*;
import com.lazur.services.TopService;
import com.lazur.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class TopController {

    private static final String CURR_MATERIAL = "currMaterial";
    private static final String MATERIAL_ID = "materialId";
    private static final String MATERIAL = "material";
    private static final String TOP = "Top";
    private static final String TYPE = "type";
    private static final String PRODUCT = "product";
    private static final String SUB_MATERIAL = "subMaterial";

    private final TypeService typeService;
    private final TopService topService;

    @Autowired
    public TopController(TypeService typeService, TopService topService) {
        this.typeService = typeService;
        this.topService = topService;
    }


    @GetMapping("/top/{material}")
    public ResponseEntity<ViewTypeModel> getFrames(@PathVariable(MATERIAL) String material) {
        List<ViewTypeModel> viewTypeModels = this.typeService.findAllByMaterial(material);
        if (viewTypeModels == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        ResponseEntity<ViewTypeModel> responseEntity
                = new ResponseEntity(viewTypeModels, HttpStatus.OK);

        return responseEntity;
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/top/add")
    public String addFrame(@Valid @ModelAttribute MaterialBindingModel materialBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(CURR_MATERIAL, materialBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.currMaterial", bindingResult);
            return String.format("redirect:/materials/%s", TOP);
        }

        this.topService.save(materialBindingModel);
        return "redirect:/materials";
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/top/add/{product}")
    public String addToTop(@PathVariable(PRODUCT) String product,
                           @Valid
                           @ModelAttribute TypeBindingModel typeBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(SUB_MATERIAL, typeBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.subMaterial", bindingResult);
            return String.format("redirect:/materials/%s/%s", TOP, product);
        }

        this.typeService.save(product, typeBindingModel);
        return "redirect:/materials";
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/top/update/{materialId}")
    public String updateTopMaterialPage(@PathVariable(MATERIAL_ID) Long materialId,
                                        @Valid
                                        @ModelAttribute MaterialUpdateModel materialUpdateModel,
                                        BindingResult bindingResult,
                                        RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            materialUpdateModel.setId(materialId);
            redirectAttributes.addFlashAttribute(MATERIAL, materialUpdateModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.material", bindingResult);
            return String.format("redirect:/materials/edit/%s/%d", TOP, materialId);
        }

        this.topService.update(materialId, materialUpdateModel);
        return "redirect:/materials";
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/top/delete/{materialId}")
    public String deleteTopMaterialPage(@PathVariable(MATERIAL_ID) Long materialId,
                                        Model model) {
        this.topService.delete(materialId);
        return "redirect:/materials";
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/top/update/{type}/{materialId}")
    public String updateTopMaterialTypePage(@PathVariable(MATERIAL_ID) Long materialId,
                                            @PathVariable(TYPE) String type,
                                            @Valid @ModelAttribute TypeUpdateModel typeUpdateModel,
                                            BindingResult bindingResult,
                                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            typeUpdateModel.setId(materialId);
            redirectAttributes.addFlashAttribute(MATERIAL, typeUpdateModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.material", bindingResult);
            return String.format("redirect:/materials/edit/%s/%s/%d", TOP, type, materialId);
        }

        this.typeService.update(materialId, typeUpdateModel);
        return "redirect:/materials";
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/top/delete/{type}/{materialId}")
    public String deleteTopMaterialTypePage(@PathVariable(MATERIAL_ID) Long materialId,
                                            @PathVariable(TYPE) String type,
                                            Model model) {
        this.typeService.delete(materialId);
        return "redirect:/materials";
    }


}