package ru.kata.spring.boot_security.demo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping
    public String getUser(ModelMap model, Principal principal) {
        String userEmail = principal.getName();
        model.addAttribute("user", userService.findByEmail(userEmail));
        model.addAttribute("roles", roleService.findAll());
        return "/user/user";
    }
}