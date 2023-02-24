package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
public class UserController {

    private final UserService userService;

    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String getAdminPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/admin-page";
    }
    @GetMapping("/add")
    public String newUserPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/add-new-user";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("u") User user,@RequestParam("selectedRole")
                             String selectedRole, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "admin/add-new-user";
        user.getRoleSet().add(roleService.getRole(selectedRole));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/{id}/update")
    public String updateUser(@ModelAttribute("user") User user, Model model,
                             @RequestParam("selectedRole") String selectedRole, BindingResult bindingResult) {
        model.addAttribute("roles", roleService.getAllRoles());
        if (bindingResult.hasErrors())
            return "redirect:/{id}/update";
        user.getRoleSet().add(roleService.getRole(selectedRole));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("user")
    public String showUserInfo(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("user", user);
        return "users/user-page";
    }

}
