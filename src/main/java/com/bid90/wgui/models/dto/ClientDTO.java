package com.bid90.wgui.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

    UUID id;
    String name;
    UserDTO user;
    String address;
    String allowedIPs;
    Boolean enabled;
    String createdAt;
    String updatedAt;


}
