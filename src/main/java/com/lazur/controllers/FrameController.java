package com.lazur.controllers;

import com.lazur.models.view.*;
import com.lazur.services.FrameService;
import com.lazur.services.MaterialService;
import com.lazur.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FrameController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private FrameService frameService;

    @GetMapping("/frame/{material}")
    public ResponseEntity<ViewTypeModel> getFrames(@PathVariable("material") String material) {
        List<ViewTypeModel> viewTypeModels = this.typeService.findAllByMaterial(material);
        if (viewTypeModels == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        ResponseEntity<ViewTypeModel> responseEntity
                = new ResponseEntity(viewTypeModels, HttpStatus.OK);

        return responseEntity;
    }

    @PostMapping("/frame/add")
    public String addFrame(@ModelAttribute MaterialBindingModel materialBindingModel, Model model) {
//        if (true) {
//
//        }


        this.frameService.save(materialBindingModel);
        return "redirect:/materials";
    }


    @PostMapping("/frame/add/{product}")
    public String addToFrame(@PathVariable("product") String product,
                             @ModelAttribute TypeBindingModel typeBindingModel, Model model) {
//        if (true) {
//
//        }

        this.typeService.save(product, typeBindingModel);
        return "redirect:/materials";
    }


    @PostMapping("/frame/update/{materialId}")
    public String updateFrameMaterialPage(@PathVariable("materialId") Long materialId,
                                          @ModelAttribute MaterialUpdateModel materialUpdateModel,
                                          Model model) {
//        if (true) {
//
//        }

        this.frameService.update(materialId, materialUpdateModel);
        return "redirect:/materials";
    }


    @PostMapping("/frame/delete/{materialId}")
    public String deleteFrameMaterialPage(@PathVariable("materialId") Long materialId,
                                          Model model) {
        this.frameService.delete(materialId);
        return "redirect:/materials";
    }


    @PostMapping("/frame/update/{type}/{materialId}")
    public String updateFrameMaterialTypePage(@PathVariable("materialId") Long materialId,
                                              @PathVariable("type") String type,
                                              @ModelAttribute TypeUpdateModel typeUpdateModel,
                                              Model model) {
//        if (true) {
//
//        }

        this.typeService.update(materialId, typeUpdateModel);
        return "redirect:/materials";
    }


    @PostMapping("/frame/delete/{type}/{materialId}")
    public String deleteFrameMaterialTypePage(@PathVariable("materialId") Long materialId,
                                              @PathVariable("type") String type,
                                              Model model) {
        this.typeService.delete(materialId);
        return "redirect:/materials";
    }


}

