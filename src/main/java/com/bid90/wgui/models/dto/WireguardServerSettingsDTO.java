package com.bid90.wgui.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WireguardServerSettingsDTO {

    private String serverInterfaceAddresses;
    private String listenPort;
    private String postUpScript;
    private String postDownScript;
    private String preUpScript;
    private String preDownScript;


}
