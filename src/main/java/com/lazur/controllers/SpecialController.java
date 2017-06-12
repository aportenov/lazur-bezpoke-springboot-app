package com.lazur.controllers;

import com.lazur.models.view.SpecialSubMaterialBindingModel;
import com.lazur.models.view.SpecificBindingModel;
import com.lazur.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class SpecialController {

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


    @PostMapping("/special/add")
    public String addSpecial(@ModelAttribute SpecificBindingModel specificBindingModel){
//        if (true){
//
//        }

        this.specificMaterialService.save(specificBindingModel);
        return "redirect:/materials/special";

    }

    @PostMapping("/special/update/{id}")
    public String editSpecial(@PathVariable("id") Long id, @ModelAttribute SpecificBindingModel specificBindingModel){
//        if (true){
//
//        }

        this.specificMaterialService.update(id,specificBindingModel);
        return "redirect:/materials/special";

    }

    @PostMapping("/special/delete/{id}")
    public String deleteSpecial(@PathVariable("id") Long id){
          this.specificMaterialService.delete(id);
          return "redirect:/materials/special";

    }


    @PostMapping("/special/add/{special}")
    public String addSpecialSubMaterial(@PathVariable("special") String special,
                                        @ModelAttribute SpecialSubMaterialBindingModel specialSubMaterialBindingModel){
//        if (true){
//
//        }

        SubMaterialService subMaterialService = getService(special);
        subMaterialService.save(specialSubMaterialBindingModel);
        return "redirect:/materials/special";

    }

    @PostMapping("/special/update/{special}/{id}")
    public String addSpecialSubMaterial(@PathVariable("special") String special,
                                        @PathVariable("id") Long id,
                                        @ModelAttribute SpecialSubMaterialBindingModel specialSubMaterialBindingModel){
//        if (true){
//
//        }

        SubMaterialService subMaterialService = getService(special);
        subMaterialService.update(id, specialSubMaterialBindingModel);
        return "redirect:/materials/special";
    }


    @PostMapping("/special/delete/{special}/{id}")
    public String deleteSpecialSubMaterial(@PathVariable("special") String special,
                                        @PathVariable("id") Long id,
                                        @ModelAttribute SpecialSubMaterialBindingModel specialSubMaterialBindingModel){
//        if (true){
//
//        }

        SubMaterialService subMaterialService = getService(special);
        subMaterialService.delete(id);
        return "redirect:/materials/special";
    }


    private SubMaterialService getService(String special) {
        switch (special.toLowerCase()){
            case "product":
                return this.specificProductService;
            case "color":
                return this.colorService;
            case "manufacturer":
                return this.manufacturerService;
            case "manufcode":
                return this.manufCodeService;
            default:
                //throw SpecialSubMaterialNotFound();
        }

        return null;
    }
}
