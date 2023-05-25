package com.bid90.wgui.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Data
@Entity
@Table(name = "key_tb")
public class KeyPair {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    @Column(name = "privateKey")
    private String privateKey;
    @Column(name = "publicKey")
    private String publicKey;
    @Column(name = "presharedKey")
    private String presharedKey;

    @OneToOne(mappedBy = "keyPair")
    Server server;

    @OneToOne(mappedBy = "keyPair")
    Client client;

}
