package com.bid90.wgui.controllers;


import com.bid90.wgui.exceptions.CustomException;
import com.bid90.wgui.models.*;
import com.bid90.wgui.models.dto.*;
import com.bid90.wgui.services.*;
import com.bid90.wgui.utils.*;
import org.modelmapper.ModelMapper;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;


@Controller
public class ClientsController {

    private static Logger logger = LoggerFactory.getLogger(ClientsController.class);


    @Autowired
    UserService userService;

    @Autowired
    ClientService clientService;

    @Autowired
    ServerService serverService;

    @Autowired
    KeyPairService keyPairService;

    @Autowired
    ActionsService actionsService;

    @Autowired
    ModelMapper modelMapper;

    @GetMapping(value = "/clients")
    public String clients(@RequestParam(value = "userId", required = false) UUID userId, Model model) {
        var user = userService.getAutenticatedUser();
        List<ClientDTO> clients;
        if (userId != null) {
            clients = clientService.getClientsByUserId(userId).stream().map(clientService::toDTO).toList();

        } else {
            clients = clientService.getAllClients().stream().map(clientService::toDTO).toList();
        }
        model.addAttribute("clients", clients);
        model.addAttribute("activeMenu", "clients");
        model.addAttribute("user", user);
        return "clients";
    }



    @GetMapping(value = "/clients/add")
    public String addClient(Model model) {
        var user = userService.getAutenticatedUser();
        var users = userService.getAllUsers();
        var serverAddress = serverService.getServer().getAddress();
        var ips = clientService.getAllClients().stream().map(Client::getAddress).toList();
        model.addAttribute("user", user);
        model.addAttribute("users", users);
        model.addAttribute("clientIp", IPUtil.findAvailableIpAddress(serverAddress, ips));
        return "add-client";
    }


    @PostMapping(value = "/clients/add")
    public String saveClient(@ModelAttribute("client") AddClientDTO addClientDTO,
                             RedirectAttributes redirectAttributes) {
        var user = userService.getUserById(addClientDTO.getUserId())
                .orElseThrow(() -> new CustomException("User with id: " + addClientDTO.getUserId() + " not found"));
        var client = modelMapper.map(addClientDTO, Client.class);
        var keys = KeyGen.generate();
        var keyPair = new KeyPair();
        keyPair.setPrivateKey(keys.getPrivateKey());
        keyPair.setPublicKey(keys.getPublicKey());
        keyPair.setPresharedKey(keys.getPreSharedKey());
        client.setEnabled(addClientDTO.getEnabled());
        client.setUser(user);
        client.setKeyPair(keyPairService.save(keyPair));
        clientService.save(client);
        if (client.getEnabled()) {
            actionsService.updateWgConfFile();
            actionsService.updateWgServer();
        }
        redirectAttributes.addFlashAttribute("notificationMessage", "Client added successfully.");
        return "redirect:/clients/add";
    }


    @GetMapping("/clients/{clientId}/edit")
    public String edit(@PathVariable("clientId") UUID clientId, Model model) {
        var user = userService.getAutenticatedUser();
        var client = clientService.getClientsById(clientId)
                .orElseThrow(() -> new CustomException("Client with id: " + clientId + " not found"));
        model.addAttribute("user", user);
        model.addAttribute("client", client);
        return "edit-client";
    }

    @GetMapping("/client/{clientId}/delete")
    public String delete(@PathVariable("clientId") UUID clientId, Model model) {
        var client = clientService.getClientsById(clientId)
                .orElseThrow(() -> new CustomException("Client with id: " + clientId + " not found"));
        clientService.delete(client);
        actionsService.updateWgConfFile();
        actionsService.updateWgServer();
        return "redirect:/clients";
    }


    @PostMapping("/clients/update")
    public String editUser(@ModelAttribute("client") UpdateClientDTO updateClientDTO,
                           RedirectAttributes redirectAttributes) {
        var client = clientService.getClientsById(updateClientDTO.getId())
                .orElseThrow(() -> new CustomException("Client with id: " + updateClientDTO.getId() + " not found"));
        clientService.updateClient(client, updateClientDTO);
        actionsService.updateWgConfFile();
        actionsService.updateWgServer();
        redirectAttributes.addFlashAttribute("notificationMessage", "Client updated successfully.");
        return "redirect:/clients";
    }
    @GetMapping(value = "/home")
    public String clients(Model model) {
        var user = userService.getAutenticatedUser();
        var clients = clientService.getClientsByUserId(user.getId()).stream().map(clientService::toDTO).toList();
        model.addAttribute("activeMenu", "my-clients");
        model.addAttribute("clients", clients);
        model.addAttribute("user", user);
        return "my-clients";
    }
}
