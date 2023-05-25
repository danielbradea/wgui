package com.bid90.wgui.controllers;

import com.bid90.wgui.exceptions.CustomException;
import com.bid90.wgui.models.UserRole;
import com.bid90.wgui.models.dto.AddUserDTO;
import com.bid90.wgui.models.dto.UpdateUserDTO;
import com.bid90.wgui.services.ClientService;
import com.bid90.wgui.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    ClientService clientService;


    @GetMapping("/profile")
    public String profile(Model model) {
        var user = userService.getAutenticatedUser();
        model.addAttribute("user", user);
        model.addAttribute("roles", UserRole.values());
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute("user") UpdateUserDTO updateUserDTO,
                                RedirectAttributes redirectAttributes)  {
        var user = userService.getAutenticatedUser();
        userService.updateUser(user, updateUserDTO);
        redirectAttributes.addFlashAttribute("notificationMessage", "Profile updated successfully.");
        return "redirect:/profile";
    }

    @GetMapping("/users")
    public String list(Model model) {
        var user = userService.getAutenticatedUser();
        var users = userService.getAllUsers();
        model.addAttribute("activeMenu", "users");
        model.addAttribute("users", users);
        model.addAttribute("user", user);
        return "user-list";
    }

    @GetMapping("/users/add")
    public String add(Model model) {
        var user = userService.getAutenticatedUser();
        model.addAttribute("user", user);
        model.addAttribute("roles", UserRole.values());
        return "add-user";
    }

    @PostMapping("/users")
    public String addUser(@ModelAttribute("user") AddUserDTO userDTO,
                          RedirectAttributes redirectAttributes) {
        userService.addUser(userDTO);
        redirectAttributes.addFlashAttribute("notificationMessage", "User added successfully.");
        return "redirect:/users/add";
    }

    @GetMapping("/users/{userId}/edit")
    public String edit(@PathVariable("userId") UUID userId, Model model) {
        var user = userService.getUserById(userId)
                .orElseThrow(() -> new CustomException("User with id: " + userId + " not found"));
        model.addAttribute("user", user);
        model.addAttribute("roles", UserRole.values());
        return "edit-user";
    }





    @PostMapping("/users/update")
    public String editUser(@ModelAttribute("user") UpdateUserDTO updateUserDTO,
                           RedirectAttributes redirectAttributes) {
        var user = userService.getUserById(updateUserDTO.getId())
                .orElseThrow(() -> new CustomException("User with id: " + updateUserDTO.getId() + " not found"));
        userService.updateUser(user, updateUserDTO);
        redirectAttributes.addFlashAttribute("notificationMessage", "User updated successfully.");
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/delete")
    public String delete(@PathVariable("id") UUID id) {
        var user = userService.getUserById(id)
                .orElseThrow(() -> new CustomException("User with id: " + id + " not found"));
        var clients = clientService.getClientsByUserId(user.getId());
        clientService.delete(clients);
        userService.deleteUser(user);
        return "redirect:/users";
    }

}
