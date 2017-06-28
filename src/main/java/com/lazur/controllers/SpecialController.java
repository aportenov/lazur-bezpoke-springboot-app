package com.lazur.controllers;

import com.lazur.exeptions.SpecialSubMaterialNotFound;
import com.lazur.massages.Errors;
import com.lazur.models.materials.SpecialSubMaterialBindingModel;
import com.lazur.models.materials.SpecificBindingModel;
import com.lazur.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
public class SpecialController {

    private static final String ID = "id";
    private static final String ERROR = "error";
    private static final String SPECIAL = "special";
    private static final String PRODUCT = "product";
    private static final String MANUFACTURER = "manufacturer";
    private static final String COLOR = "color";
    private static final String MANUF_CODE = "manufcode";
    private static final String COMA_SPLIT = ",";
    private static final int ARRAY_SIZE_ZERO = 0;
    private static final String EMPTY_STRING = "";

    private final SpecificMaterialService specificMaterialService;
    private final SpecificProductService specificProductService;
    private final ColorService colorService;
    private final ManufacturerService manufacturerService;
    private final ManufCodeService manufCodeService;

    @Autowired
    public SpecialController(SpecificMaterialService specificMaterialService,
                             SpecificProductService specificProductService,
                             ColorService colorService,
                             ManufacturerService manufacturerService,
                             ManufCodeService manufCodeService) {
        this.specificMaterialService = specificMaterialService;
        this.specificProductService = specificProductService;
        this.colorService = colorService;
        this.manufacturerService = manufacturerService;
        this.manufCodeService = manufCodeService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/special/add")
    public String addSpecial(@Valid @ModelAttribute SpecificBindingModel specificBindingModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (hasErrors(specificBindingModel) || bindingResult.hasErrors()) {
            if (hasErrors(specificBindingModel)) {
                redirectAttributes.addFlashAttribute(ERROR, Errors.INVALID_ENTRY);
            }else {
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.special", bindingResult);
                redirectAttributes.addFlashAttribute(SPECIAL, specificBindingModel);
            }

            return String.format("redirect:/materials/special");
        }

        this.specificMaterialService.save(specificBindingModel);
        return "redirect:/materials/special";

    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/special/update/{id}")
    public String editSpecial(@PathVariable(ID) Long id,
                              @Valid @ModelAttribute SpecificBindingModel specificBindingModel,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            specificBindingModel.setId(id);
            specificBindingModel.setSpecificProductName(specificBindingModel.getSpecificProductName().replace(COMA_SPLIT,EMPTY_STRING));
            specificBindingModel.setColorName(specificBindingModel.getColorName().replace(COMA_SPLIT,EMPTY_STRING));
            specificBindingModel.setManufacturerName(specificBindingModel.getManufacturerName().replace(COMA_SPLIT,EMPTY_STRING));
            specificBindingModel.setManufCodeName(specificBindingModel.getManufCodeName().replace(COMA_SPLIT,EMPTY_STRING));
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.special", bindingResult);
            redirectAttributes.addFlashAttribute(SPECIAL, specificBindingModel);
            return String.format("redirect:/materials/special/edit/%d", id);
        }

        this.specificMaterialService.update(id, specificBindingModel);
        return "redirect:/materials/special";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/special/delete/{id}")
    public String deleteSpecial(@PathVariable(ID) Long id) {
        this.specificMaterialService.delete(id);
        return "redirect:/materials/special";
    }


    @PostMapping("/special/add/{special}")
    public String addSpecialSubMaterial(@PathVariable(SPECIAL) String special,
                                        @Valid
                                        @ModelAttribute SpecialSubMaterialBindingModel specialSubMaterialBindingModel,
                                        BindingResult bindingResult,
                                        RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.special", bindingResult);
            redirectAttributes.addFlashAttribute(SPECIAL, specialSubMaterialBindingModel);
            return String.format("redirect:/materials/special/%s", special);
        }

        SubMaterialService subMaterialService = getService(special);
        subMaterialService.save(specialSubMaterialBindingModel);
        return "redirect:/materials/special";
    }

    @PostMapping("/special/update/{special}/{id}")
    public String addSpecialSubMaterial(@PathVariable(SPECIAL) String special,
                                        @PathVariable(ID) Long id,
                                        @Valid
                                        @ModelAttribute SpecialSubMaterialBindingModel specialSubMaterialBindingModel,
                                        BindingResult bindingResult,
                                        RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            specialSubMaterialBindingModel.setId(id);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.special", bindingResult);
            redirectAttributes.addFlashAttribute(SPECIAL, specialSubMaterialBindingModel);
            return String.format("redirect:/materials/special/edit/%s/%d", special,id);
        }

        SubMaterialService subMaterialService = getService(special);
        subMaterialService.update(id, specialSubMaterialBindingModel);
        return "redirect:/materials/special";
    }


    @PostMapping("/special/delete/{special}/{id}")
    public String deleteSpecialSubMaterial(@PathVariable(SPECIAL) String special,
                                           @PathVariable(ID) Long id,
                                           @ModelAttribute SpecialSubMaterialBindingModel specialSubMaterialBindingModel) {
        SubMaterialService subMaterialService = getService(special);
        subMaterialService.delete(id);
        return "redirect:/materials/special";
    }


    private SubMaterialService getService(String special) {
        switch (special.toLowerCase()) {
            case PRODUCT:
                return this.specificProductService;
            case COLOR:
                return this.colorService;
            case MANUFACTURER:
                return this.manufacturerService;
            case MANUF_CODE:
                return this.manufCodeService;
            default:
                throw new SpecialSubMaterialNotFound();
        }
    }


    private boolean hasErrors(SpecificBindingModel specificBindingModel) {
        return specificBindingModel.getSpecificProductName().split(COMA_SPLIT).length == ARRAY_SIZE_ZERO ||
                specificBindingModel.getManufCodeName().split(COMA_SPLIT).length == ARRAY_SIZE_ZERO ||
                specificBindingModel.getColorName().split(COMA_SPLIT).length == ARRAY_SIZE_ZERO ||
                specificBindingModel.getManufacturerName().split(COMA_SPLIT).length == ARRAY_SIZE_ZERO;
    }
}
