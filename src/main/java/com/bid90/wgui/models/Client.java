package com.bid90.wgui.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "client_tb")
public class Client {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(name = "name")
    String name;

    @Column(name = "address")
    private String address;

    @ManyToOne
    private User user;

    @OneToOne( cascade = CascadeType.ALL)
    KeyPair keyPair;

    @Column(name = "allowedIPs")
    String allowedIPs;

    @Column(name = "enabled")
    Boolean enabled;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


}
