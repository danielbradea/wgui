package com.bid90.wgui.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettingsDTO {

    private String endpointAddress;
    private List<String> dnsServers;
    private Integer mtu;
    private Integer persistentKeepalive;
    private String fwMark;
    private String serverInterfaceAddresses;
    private Integer listenPort;
    private String postUpScript;
    private String postDownScript;
    private String preUpScript;
    private String preDownScript;
}
