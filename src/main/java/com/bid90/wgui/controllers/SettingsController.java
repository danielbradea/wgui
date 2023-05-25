package com.bid90.wgui.controllers;


import com.bid90.wgui.models.dto.WireguardSettingsDTO;
import com.bid90.wgui.services.SettingsService;
import com.bid90.wgui.services.UserService;
import com.bid90.wgui.utils.IPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SettingsController {
    @Autowired
    SettingsService settingsService;
    @Autowired
    UserService userService;

    @GetMapping("/settings")
    public String settings(Model model) {
        var user = userService.getAutenticatedUser();
        var settings = settingsService.get();
        model.addAttribute("activeMenu", "settings");
        model.addAttribute("user", user);
        model.addAttribute("settings", settings);
        model.addAttribute("publicIP", IPUtil.getPublicIP().orElse(""));
        return "settings";
    }

    @PostMapping("/settings")
    public String setSettings(@ModelAttribute("settings") WireguardSettingsDTO wireguardSettingsDTO,
                              RedirectAttributes redirectAttributes) {
        settingsService.update(wireguardSettingsDTO);
        redirectAttributes.addFlashAttribute("notificationMessage", "Settings updated successfully.");
        return "redirect:/settings";
    }


}
