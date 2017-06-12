package com.lazur.controllers;

import com.lazur.massages.Errors;
import com.lazur.models.view.RoleViewModel;
import com.lazur.models.view.UserBindingModel;
import com.lazur.models.view.UserEditBindingModel;
import com.lazur.models.view.UserViewModel;
import com.lazur.services.RoleService;
import com.lazur.services.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.management.relation.RoleNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/login")
    public String getLoginPage(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("error", Errors.INVALID_CREDENTIALS);
        }

        return "login";
    }

    @GetMapping("/users")
    public String getUserPage(@RequestParam(value = "id", required = false) Long id, Model model) throws NotFoundException {

        List<UserViewModel> userViewModels = this.userService.getAllUsers();
        List<RoleViewModel> roleViewModelList = this.roleService.getAllRoles();
        model.addAttribute("roles", roleViewModelList);
        model.addAttribute("users", userViewModels);

        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserBindingModel());
        }

        if (id != null) {
            UserViewModel userViewModel = this.userService.findById(id);
            model.addAttribute("user", userViewModel);
            return "users-edit";
        }

        return "users";

    }

    @PostMapping("/users/add")
    public String addUser(@Valid @ModelAttribute UserBindingModel userBindingModel,
                          BindingResult bindingResult,
                          Model model,
                          RedirectAttributes redirectAttributes) throws RoleNotFoundException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            redirectAttributes.addFlashAttribute("user", userBindingModel);

            return "redirect:/users";
        }
        this.userService.createUser(userBindingModel);

        return "redirect:/users";
    }

    @PostMapping("/users/edit/{id}")
    public String editUser(@PathVariable(value = "id") Long id,
                           @ModelAttribute UserEditBindingModel userEditBindingModel,
                           Model model) throws RoleNotFoundException {

        this.userService.updateUser(id, userEditBindingModel);

        return "/users";
    }

}
