package com.bid90.wgui.models.dto;

import lombok.Data;

@Data
public class Response {
    String command;
    String output;
    Integer exitCode;
}
