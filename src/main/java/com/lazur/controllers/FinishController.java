package com.lazur.controllers;

import com.lazur.models.materials.*;
import com.lazur.services.FinishService;
import com.lazur.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class FinishController {

    private static final String CURR_MATERIAL = "currMaterial";
    private static final String MATERIAL_ID = "materialId";
    private static final String MATERIAL = "material";
    private static final String FINISH = "Finish";
    private static final String TYPE = "type";
    private static final String PRODUCT = "product";
    private static final String SUB_MATERIAL = "subMaterial";

    private final TypeService typeService;
    private final FinishService finishService;

    @Autowired
    public FinishController(TypeService typeService, FinishService finishService) {
        this.typeService = typeService;
        this.finishService = finishService;
    }


    @GetMapping("/finish/{material}")
    public ResponseEntity<ViewTypeModel> getFrames(@PathVariable(MATERIAL) String material) {
        List<ViewTypeModel> viewTypeModels = this.typeService.findAllByMaterial(material);
        if (viewTypeModels == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        ResponseEntity<ViewTypeModel> responseEntity
                = new ResponseEntity(viewTypeModels, HttpStatus.OK);

        return responseEntity;
    }

    @PostMapping("/finish/add")
    public String addFinish(@Valid @ModelAttribute MaterialBindingModel materialBindingModel,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(CURR_MATERIAL, materialBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.currMaterial", bindingResult);
            return String.format("redirect:/materials/%s",FINISH);
        }

        this.finishService.save(materialBindingModel);
        return "redirect:/materials";
    }

    @PostMapping("/finish/add/{product}")
    public String addToFinish(@PathVariable(PRODUCT) String product,
                              @Valid @ModelAttribute TypeBindingModel typeBindingModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(SUB_MATERIAL, typeBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.subMaterial", bindingResult);
            return String.format("redirect:/materials/%s/%s",FINISH, product);
        }
        this.typeService.save(product, typeBindingModel);
        return "redirect:/materials";
    }


    @PostMapping("/finish/update/{materialId}")
    public String updateFinishMaterialPage(@PathVariable(MATERIAL_ID) Long materialId,
                                           @Valid
                                           @ModelAttribute MaterialUpdateModel materialUpdateModel,
                                           BindingResult bindingResult,
                                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            materialUpdateModel.setId(materialId);
            redirectAttributes.addFlashAttribute(MATERIAL, materialUpdateModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.material", bindingResult);
            return String.format("redirect:/materials/edit/%s/%d", FINISH,materialId);
        }

        this.finishService.update(materialId, materialUpdateModel);
        return "redirect:/materials";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/finish/delete/{materialId}")
    public String deleteFinishMaterialPage(@PathVariable(MATERIAL_ID) Long materialId,
                                           Model model) {
        this.finishService.delete(materialId);
        return "redirect:/materials";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/finish/update/{type}/{materialId}")
    public String updateFinishMaterialTypePage(@PathVariable(MATERIAL_ID) Long materialId,
                                               @PathVariable(TYPE) String type,
                                               @Valid
                                               @ModelAttribute TypeUpdateModel typeUpdateModel,
                                               BindingResult bindingResult,
                                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            typeUpdateModel.setId(materialId);
            redirectAttributes.addFlashAttribute(MATERIAL, typeUpdateModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.material", bindingResult);
            return String.format("redirect:/materials/edit/%s/%s/%d",FINISH,type,materialId);
        }

        this.typeService.update(materialId, typeUpdateModel);
        return "redirect:/materials";
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/finish/delete/{type}/{materialId}")
    public String deleteFinishMaterialTypePage(@PathVariable(MATERIAL_ID) Long materialId,
                                               @PathVariable(TYPE) String type,
                                               Model model) {
        this.typeService.delete(materialId);
        return "redirect:/materials";
    }


}
