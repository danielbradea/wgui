package com.bid90.wgui.securitys;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private static Logger logger = LoggerFactory.getLogger(CustomAccessDeniedHandler.class);
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        Authentication auth
                = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            logger.info("User '" + auth.getName()
                    + "' attempted to access the protected URL: "
                    + request.getRequestURI());
        }

        response.sendRedirect(request.getContextPath() + "/403");
    }
}
