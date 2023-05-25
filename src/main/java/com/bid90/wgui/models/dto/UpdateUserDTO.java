package com.bid90.wgui.models.dto;

import com.bid90.wgui.models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private Boolean enabled = false;
    private UserRole role;
}
