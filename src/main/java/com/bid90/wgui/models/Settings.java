package com.bid90.wgui.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@Entity
@Table(name = "settings_tb")
@AllArgsConstructor
@NoArgsConstructor
public class Settings {
    @Id
    private UUID id;
    private String endpointAddress;
    private String dnsServers;
    private Integer mtu;
    private Integer persistentKeepAlive;
    private String fwMark;

}
