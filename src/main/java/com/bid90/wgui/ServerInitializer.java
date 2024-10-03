package com.bid90.wgui;

import com.bid90.wgui.models.*;
import com.bid90.wgui.models.dto.AddUserDTO;
import com.bid90.wgui.services.ActionsService;
import com.bid90.wgui.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ServerInitializer implements ApplicationRunner {

    private final UserService userService;
    private final String adminEmail;
    private final String adminPassword;
    private final ActionsService actionsService;


    @Autowired
    public ServerInitializer(UserService userService,
                             @Value("${admin.email}") String adminEmail,
                             @Value("${admin.password}") String adminPassword, ActionsService actionsService) {
        this.userService = userService;

        this.adminEmail = adminEmail;
        this.adminPassword = adminPassword;
        this.actionsService = actionsService;
    }

    /**
     * Executes the initialization logic when the application starts.
     *
     * @param args The application arguments
     * @throws Exception If an exception occurs during initialization
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {

        // Create admin user if it doesn't exist
        if (userService.getUserByEmail(adminEmail).isEmpty()) {
            var userDTO = new AddUserDTO();
            userDTO.setName("Admin");
            userDTO.setEmail(adminEmail);
            userDTO.setPassword(adminPassword);
            userDTO.setRole(UserRole.ADMIN);
            userDTO.setEnabled(true);
            userService.addUser(userDTO);
        }

        actionsService.wgQuickUp();
    }
}
