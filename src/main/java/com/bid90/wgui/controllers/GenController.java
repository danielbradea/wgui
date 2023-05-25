package com.bid90.wgui.controllers;

import com.bid90.wgui.exceptions.CustomException;
import com.bid90.wgui.services.*;
import com.bid90.wgui.utils.QRCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@RestController
public class GenController {

    @Autowired
    ClientService clientService;
    @Autowired
    UserService userService;

    @Autowired
    SettingsService settingsService;

    @Autowired
    ServerService serverService;


    @GetMapping(value = "/client/{clientId}/qrcode", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable("clientId") UUID clientId) throws IOException {
        var user = userService.getAutenticatedUser();
        var client = clientService.getClientsByIdAndUserId(clientId,user.getId())
                .orElseThrow(() -> new CustomException("Client with id:" +clientId+" not found"));
        var server = serverService.getServer();
        var settings = settingsService.get();
        var content = WGGen.wgClient(server,client,settings);
        var imageBytes = QRCodeGenerator.generateQRCode(content,400, 400);
        return ResponseEntity.ok().body(imageBytes);
    }


    @GetMapping(value = "/client/{clientId}/file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> downloadConfig(@PathVariable("clientId") UUID clientId) throws IOException {
        var user = userService.getAutenticatedUser();
        var client = clientService.getClientsByIdAndUserId(clientId,user.getId())
                .orElseThrow(() -> new CustomException("Client with id:" +clientId+" not found"));
        var server = serverService.getServer();
        var settings = settingsService.get();
        var content = WGGen.wgClient(server,client,settings);

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename="+client.getName()+".conf")
                .body(content.getBytes(StandardCharsets.UTF_8));
    }
}
