package com.bid90.wgui.models.dto;

import com.bid90.wgui.models.KeyPair;
import lombok.Data;


@Data
public class ServerDTO {

    private KeyPair keyPair;
    private String address;
    private Integer listenPort;
    private String preUp;
    private String postUp;
    private String preDown;
    private String postDown;
}
