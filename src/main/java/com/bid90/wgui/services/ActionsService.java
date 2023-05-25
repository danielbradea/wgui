package com.bid90.wgui.services;

import com.bid90.wgui.exceptions.CustomException;
import com.bid90.wgui.models.Client;
import com.bid90.wgui.models.dto.Response;
import com.bid90.wgui.utils.FileEditor;
import com.bid90.wgui.utils.IPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ActionsService {

    private static Logger logger = LoggerFactory.getLogger(ActionsService.class);
    @Autowired
    ServerService serverService;

    @Autowired
    ClientService clientService;

    @Autowired
    CommandService commandService;

    @Value("${wgc.wgConfFilePath}")
    String wgConfFilePath;

    /**
     * Updates the WireGuard configuration file with the current server and client information.
     * The method retrieves the server configuration from the ServerService and the enabled clients
     * from the ClientService. It generates the WireGuard configuration content using the WGGen class.
     * The generated content is then written to the specified wgConfFilePath using the FileEditor class.
     *
     * @throws CustomException if an error occurs while updating the configuration file.
     */
    public void updateWgConfFile() {
        var server = serverService.getServer();
        var clients = clientService.getAllClients().stream().filter(Client::getEnabled).toList();
        var content = WGGen.wgServer(server, clients);
        try {
            FileEditor.editFile(wgConfFilePath, content);
        } catch (IOException e) {
            throw new CustomException(e.getMessage());
        }
    }

    public void updateWgServer(){
        var wgShow = wgShow();
        logger.info(wgShow.toString());
        if(wgShow.getExitCode() == 0 &&  wgShow.getCommand().isEmpty()){
            var wgQuickUp = wgQuickUp();
            logger.info(wgQuickUp.toString());
            return;
        }
        var wgQuickDown = wgQuickDown();
        logger.info(wgQuickDown.toString());
        var wgQuickUp = wgQuickUp();
        logger.info(wgQuickUp.toString());
    }


    /**
     * Starts the WireGuard interface named 'wg0' using the 'wg-quick up' command.
     *
     * @return A response indicating the result of the command execution.
     */
    public Response wgQuickUp() {
        return commandService.executeCommand("wg-quick up wg0");
    }

    /**
     * Stops the WireGuard interface named 'wg0' using the 'wg-quick down' command.
     *
     * @return A response indicating the result of the command execution.
     */
    public Response wgQuickDown() {
        return commandService.executeCommand("wg-quick down wg0");
    }

    /**
     * Saves the current configuration of the WireGuard interface named 'wg0' using the 'wg-quick save' command.
     *
     * @return A response indicating the result of the command execution.
     */
    public Response wgQuickSave() {
        return commandService.executeCommand("wg-quick save wg0");
    }

    /**
     * Retrieves the output of the current status of WireGuard interfaces and their associated configurations.
     *
     * @return A Response object containing the result of the command execution.
     */
    public Response wgShow() {
        return commandService.executeCommand("wg show");
    }


    public Response wg() {
        return commandService.executeCommand("wg");
    }
}
