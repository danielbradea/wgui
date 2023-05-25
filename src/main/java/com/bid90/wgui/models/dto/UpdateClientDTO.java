package com.bid90.wgui.models.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateClientDTO {
    UUID id;
    String name;
    String address;
    String allowedIPs;
    Boolean enabled;
}
