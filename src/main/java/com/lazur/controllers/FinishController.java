package com.lazur.controllers;

import com.lazur.models.view.*;
import com.lazur.services.FinishService;
import com.lazur.services.MaterialService;
import com.lazur.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class FinishController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private FinishService finishService;


    @GetMapping("/finish/{material}")
    public ResponseEntity<ViewTypeModel> getFrames(@PathVariable("material") String material) {
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

        }

        this.finishService.save(materialBindingModel);
        return "redirect:/materials";
    }

    @PostMapping("/finish/add/{product}")
    public String addToFinish(@PathVariable("product") String product,
                              @Valid @ModelAttribute TypeBindingModel typeBindingModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()){
        }

        this.typeService.save(product, typeBindingModel);
        return "redirect:/materials";
    }


    @PostMapping("/finish/update/{materialId}")
    public String updateFinishMaterialPage(@PathVariable("materialId") Long materialId,
                                           @ModelAttribute MaterialUpdateModel materialUpdateModel,
                                           Model model) {
//        if (true){
//
//        }

        this.finishService.update(materialId, materialUpdateModel);
        return "redirect:/materials";
    }

    @PostMapping("/finish/delete/{materialId}")
    public String deleteFinishMaterialPage(@PathVariable("materialId") Long materialId,
                                           Model model) {
        this.finishService.delete(materialId);
        return "redirect:/materials";
    }

    @PostMapping("/finish/update/{type}/{materialId}")
    public String updateFinishMaterialTypePage(@PathVariable("materialId") Long materialId,
                                               @PathVariable("type") String type,
                                               @ModelAttribute TypeUpdateModel typeUpdateModel,
                                               Model model) {
//        if (true) {
//
//        }

        this.typeService.update(materialId, typeUpdateModel);
        return "redirect:/materials";
    }


    @PostMapping("/finish/delete/{type}/{materialId}")
    public String deleteFinishMaterialTypePage(@PathVariable("materialId") Long materialId,
                                               @PathVariable("type") String type,
                                               Model model) {
        this.typeService.delete(materialId);
        return "redirect:/materials";
    }


}
