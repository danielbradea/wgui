package com.bid90.wgui.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddClientDTO {

    String name;
    UUID userId;
    String allowedIPs;
    String address;
    Boolean enabled = false;

}
