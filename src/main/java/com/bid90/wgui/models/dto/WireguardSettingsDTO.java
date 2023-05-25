package com.bid90.wgui.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WireguardSettingsDTO {

    private String endpointAddress;
    private String dnsServers;
    private Integer mtu;
    private Integer persistentKeepAlive;
    private String fwMark;


}
