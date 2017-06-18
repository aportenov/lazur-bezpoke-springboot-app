package com.lazur.controllers;

import com.lazur.models.view.FamilyBidnignModel;
import com.lazur.models.view.FamilyViewModel;
import com.lazur.models.view.ViewTypeModel;
import com.lazur.services.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
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
    public String addFamily(@Valid @ModelAttribute FamilyBidnignModel familyBidnignModel,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("family", familyBidnignModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.family", bindingResult);
            return String.format("redirect:/categories/%s/%s", familyBidnignModel.getCategory(),familyBidnignModel.getType());
        }

        this.familyService.save(familyBidnignModel);
        return "redirect:/categories";
    }

    @PostMapping("/families/update/{familyId}")
    public String updateFamily(@PathVariable("familyId") Long familyId,
                               @Valid @ModelAttribute FamilyViewModel familyViewModel,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){
        this.familyService.update(familyId, familyViewModel);
        return "redirect:/categories";
    }

    @PostMapping("/families/delete/{familyId}")
    public String deleteFamily(@PathVariable("familyId") Long familyId){
        this.familyService.delete(familyId);
        return "redirect:/categories";
    }
}
