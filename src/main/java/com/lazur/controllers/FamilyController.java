package com.lazur.controllers;

import com.lazur.models.families.FamilyBidnignModel;
import com.lazur.models.families.FamilyViewModel;
import com.lazur.services.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    private static final String CATEGORY = "category";
    private static final String MODEL = "model";
    private static final String MODEL_NAME = "modelName";
    private static final String FAMILY = "family";
    private static final String CURR_FAMILY = "currFamily";
    private static final String FAMILY_NAME = "familyName";
    private static final String FAMILY_ID = "familyId";

    @Autowired
    private FamilyService familyService;

    @GetMapping("/families/{category}/{model}")
    public ResponseEntity<FamilyViewModel> getFamilies(@PathVariable(CATEGORY) String category,
                                                       @PathVariable(MODEL) String model){
        List<FamilyViewModel> familyViewModels = this.familyService.findAllByCategoryAndModel(category, model);
        if(familyViewModels == null){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        ResponseEntity<FamilyViewModel> responseEntity
                = new ResponseEntity(familyViewModels, HttpStatus.OK);

        return responseEntity;
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/family/add")
    public String addFamily(@Valid @ModelAttribute FamilyBidnignModel familyBidnignModel,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute(FAMILY, familyBidnignModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.family", bindingResult);
            return String.format("redirect:/categories/%s/%s", familyBidnignModel.getCategory(),familyBidnignModel.getType());
        }

        this.familyService.save(familyBidnignModel);
        return "redirect:/categories";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/families/update/{category}/{modelName}/{familyName}/{familyId}")
    public String updateFamily(@PathVariable(FAMILY_ID) Long familyId,
                               @PathVariable(CATEGORY) String category,
                               @PathVariable(MODEL_NAME) String modelName,
                               @PathVariable(FAMILY_NAME) String familyName,
                               @Valid @ModelAttribute FamilyViewModel familyViewModel,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            familyViewModel.setId(familyId);
            redirectAttributes.addFlashAttribute(CURR_FAMILY, familyViewModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.currFamily", bindingResult);
            return String.format("redirect:/categories/edit/{category}/{modelName}/{familyName}/{familyId}",
                    category, modelName, familyName, familyId);
        }
        this.familyService.update(familyId, familyViewModel);
        return "redirect:/categories";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/families/delete/{familyId}")
    public String deleteFamily(@PathVariable(FAMILY_ID) Long familyId){
        this.familyService.delete(familyId);
        return "redirect:/categories";
    }
}
