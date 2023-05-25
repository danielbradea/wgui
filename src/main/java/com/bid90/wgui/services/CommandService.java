package com.bid90.wgui.services;

import com.bid90.wgui.exceptions.CustomException;
import com.bid90.wgui.models.dto.Response;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class CommandService {

    public Response executeCommand(String command) {
        var response = new Response();
        response.setCommand(command);
        try {
            ProcessBuilder processBuilder;
            if (isWindows()) {
                throw new CustomException("Only linux");
            } else {
                processBuilder = new ProcessBuilder("/bin/bash", "-c", command);
            }
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Read the output from the command
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            response.setOutput(output.toString());
            // Wait for the command to complete
            int exitCode = process.waitFor();
            response.setExitCode(exitCode);

           return response;

        } catch (IOException | InterruptedException e) {
            response.setOutput(e.toString());
        }

        return response;
    }

    private boolean isWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("win");
    }

}