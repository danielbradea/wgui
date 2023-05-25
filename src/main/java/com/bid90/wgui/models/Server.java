package com.bid90.wgui.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "server_tb")
public class Server {
    @Id
    private UUID id;

    @OneToOne(cascade = CascadeType.ALL)
    private KeyPair keyPair;

    @Column(name = "address")
    private String address;
    @Column(name = "listenPort")
    private Integer listenPort;
    @Column(name = "preUp")
    private String preUp;
    @Column(name = "postUp")
    private String postUp;
    @Column(name = "preDown")
    private String preDown;
    @Column(name = "postDown")
    private String postDown;

//    public void setKeyPair(KeyPair keyPair) {
//        this.keyPair = keyPair;
//        keyPair.setServer(this);
//    }
}
