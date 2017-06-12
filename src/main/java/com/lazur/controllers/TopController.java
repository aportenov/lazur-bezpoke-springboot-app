package com.lazur.controllers;

import com.lazur.models.view.*;
import com.lazur.services.MaterialService;
import com.lazur.services.TopService;
import com.lazur.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TopController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private TopService topService;


    @GetMapping("/top/{material}")
    public ResponseEntity<ViewTypeModel> getFrames(@PathVariable("material") String material) {
        List<ViewTypeModel> viewTypeModels = this.typeService.findAllByMaterial(material);
        if (viewTypeModels == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        ResponseEntity<ViewTypeModel> responseEntity
                = new ResponseEntity(viewTypeModels, HttpStatus.OK);

        return responseEntity;
    }


    @PostMapping("/top/add")
    public String addFrame(@ModelAttribute MaterialBindingModel materialBindingModel, Model model) {
//        if (true) {
//
//        }

        this.topService.save(materialBindingModel);
        return "redirect:/materials";
    }


    @PostMapping("/top/add/{product}")
    public String addToTop(@PathVariable("product") String product,
                           @ModelAttribute TypeBindingModel typeBindingModel, Model model) {
//        if (true) {
//
//        }

        this.typeService.save(product, typeBindingModel);
        return "redirect:/materials";
    }


    @PostMapping("/top/update/{materialId}")
    public String updateTopMaterialPage(@PathVariable("materialId") Long materialId,
                                        @ModelAttribute MaterialUpdateModel materialUpdateModel,
                                        Model model) {
//        if (true) {
//
//        }

        this.topService.update(materialId, materialUpdateModel);
        return "redirect:/materials";
    }


    @PostMapping("/top/delete/{materialId}")
    public String deleteTopMaterialPage(@PathVariable("materialId") Long materialId,
                                        Model model) {
        this.topService.delete(materialId);
        return "redirect:/materials";
    }


    @PostMapping("/top/update/{type}/{materialId}")
    public String updateTopMaterialTypePage(@PathVariable("materialId") Long materialId,
                                              @PathVariable("type") String type,
                                              @ModelAttribute TypeUpdateModel typeUpdateModel,
                                              Model model) {
//        if (true) {
//
//        }

        this.typeService.update(materialId, typeUpdateModel);
        return "redirect:/materials";
    }


    @PostMapping("/top/delete/{type}/{materialId}")
    public String deleteTopMaterialTypePage(@PathVariable("materialId") Long materialId,
                                              @PathVariable("type") String type,
                                              Model model) {
        this.typeService.delete(materialId);
        return "redirect:/materials";
    }


}