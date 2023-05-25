package com.bid90.wgui.exceptions;


import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;




@ControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(Exception.class)
    public String handleGlobalException(Exception cx,
                                        HttpServletRequest request,
                                        RedirectAttributes redirectAttributes) {

        logger.error(cx.getMessage());
        redirectAttributes.addFlashAttribute("errorMessage", cx.getMessage());
        return "redirect:" + request.getHeader("Referer");
    }
}