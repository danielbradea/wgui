package com.bid90.wgui.services;

import com.bid90.wgui.models.KeyPair;

public interface KeyPairService {
    KeyPair save(KeyPair keyPair);
    void delete(KeyPair keyPair);
}
