package com.bid90.wgui.controllers;

import com.bid90.wgui.models.dto.ServerDTO;
import com.bid90.wgui.services.ActionsService;
import com.bid90.wgui.services.ServerService;
import com.bid90.wgui.services.UserService;
import com.bid90.wgui.utils.IPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ServerController {
    private static Logger logger = LoggerFactory.getLogger(ServerController.class);
    @Autowired
    ServerService serverService;

    @Autowired
    UserService userService;

    @Autowired
    ActionsService actionsService;

    @GetMapping("/server")
    public String server(Model model) {
        var user = userService.getAutenticatedUser();
        var server = serverService.getServer();
        model.addAttribute("activeMenu", "server");
        model.addAttribute("server", server);
        model.addAttribute("user", user);

        return "server";
    }

    @PostMapping("/server")
    public String server(@ModelAttribute("settings") ServerDTO serverDTO,
                         RedirectAttributes redirectAttributes) {
        serverService.update(serverDTO);
        actionsService.updateWgConfFile();
        actionsService.updateWgServer();
        redirectAttributes.addFlashAttribute("notificationMessage", "Server updated successfully.");
        return "redirect:/server";
    }
}
