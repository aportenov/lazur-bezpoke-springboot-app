package com.lazur.controllers;

import com.lazur.models.view.FamilyBidnignModel;
import com.lazur.models.view.FamilyViewModel;
import com.lazur.models.view.ViewTypeModel;
import com.lazur.services.FamilyService;
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
public class FamilyController {

    @Autowired
    private FamilyService familyService;

    @GetMapping("/families/{category}/{model}")
    public ResponseEntity<FamilyViewModel> getFamilies(@PathVariable("category") String category,
                                                       @PathVariable("model") String model){
        List<FamilyViewModel> familyViewModels = this.familyService.findAllByCategoryAndModel(category, model);
        if(familyViewModels == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        ResponseEntity<FamilyViewModel> responseEntity
                = new ResponseEntity(familyViewModels, HttpStatus.OK);

        return responseEntity;
    }


    @PostMapping("/family/add")
    public String addFamily(@ModelAttribute FamilyBidnignModel familyBidnignModel){
//        if (true){
//
//        }

        this.familyService.save(familyBidnignModel);
        return "redirect:/categories";
    }
}
