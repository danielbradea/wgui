package com.bid90.wgui.models.dto;

import com.bid90.wgui.models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddUserDTO {

    private String name;
    private String email;
    private UserRole role;
    private Boolean enabled = false;
    private String password;
}
