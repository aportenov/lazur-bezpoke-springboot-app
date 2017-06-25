package com.lazur.controllers;

import com.lazur.massages.Errors;
import com.lazur.models.users.RoleViewModel;
import com.lazur.models.users.UserBindingModel;
import com.lazur.models.users.UserViewModel;
import com.lazur.services.RoleService;
import com.lazur.services.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public String getUserPage(Model model) throws NotFoundException {

        addUserFields(model);
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserBindingModel());
        }

        return "users";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/users/add")
    public String addUser(@Valid @ModelAttribute UserBindingModel userBindingModel,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) throws RoleNotFoundException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            redirectAttributes.addFlashAttribute("user", userBindingModel);

            return "redirect:/users";
        }
        this.userService.createUser(userBindingModel);
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String getEditUserPage(@PathVariable(value = "id") Long id,
                                  Model model) throws NotFoundException {

        addUserFields(model);
        if (!model.containsAttribute("user")) {
            UserViewModel userViewModel = this.userService.findById(id);
            model.addAttribute("user", userViewModel);
        }

        return "users-edit";
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/users/edit/{id}")
    public String editUser(@PathVariable(value = "id") Long id,
                           @Valid @ModelAttribute UserBindingModel userBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) throws RoleNotFoundException {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
            redirectAttributes.addFlashAttribute("user", userBindingModel);

            return "redirect:/users/edit/" + id;
        }

        this.userService.updateUser(id, userBindingModel);
        return "redirect:/users";
    }

    @GetMapping("/users/delete/{id}")
    public String getDeleteUserPage(@PathVariable(value = "id") Long id,
                                    Model model) throws NotFoundException {
        addUserFields(model);
        UserViewModel userViewModel = this.userService.findById(id);
        model.addAttribute("user", userViewModel);
        return "users-delete";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") Long id) throws NotFoundException {
        this.userService.deleteUser(id);
        return "redirect:/users";
    }


    private void addUserFields(Model model) {
        List<UserViewModel> userViewModels = this.userService.getAllUsers();
        List<RoleViewModel> roleViewModelList = this.roleService.getAllRoles();
        model.addAttribute("roles", roleViewModelList);
        model.addAttribute("users", userViewModels);
    }
}
