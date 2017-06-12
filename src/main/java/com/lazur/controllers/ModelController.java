package com.lazur.controllers;

import com.lazur.entities.Category;
import com.lazur.entities.Model;
import com.lazur.models.view.*;
import com.lazur.services.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ModelController {

    @Autowired
    private ModelService modelService;

    @PostMapping("/models/add")
    public String addModel(@ModelAttribute ModelBindingModel modelBindingModel,
                           Model model){
        this.modelService.save(modelBindingModel);

        return "redirect:/categories";
    }

    @GetMapping("/models/{category}")
    public ResponseEntity<ModelViewModel> getModels(@PathVariable("category") String category){
        List<ModelViewModel> modelViewModels = this.modelService.findAllModelsByCategory(category);
        if(modelViewModels == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        ResponseEntity<ModelViewModel> responseEntity
                = new ResponseEntity(modelViewModels, HttpStatus.OK);

        return responseEntity;
    }


    @PostMapping("/models/update/{id}")
    public String addModel(@PathVariable("id") Long modelId,
                           @ModelAttribute CategoryAndModelUpdateModel categoryAndModelUpdateModel,
                           Model model){
        this.modelService.update(modelId,categoryAndModelUpdateModel);

        return "redirect:/categories";
    }

}
