package com.bid90.wgui.services.impl;

import com.bid90.wgui.models.KeyPair;
import com.bid90.wgui.repositorys.KeyPairRepository;
import com.bid90.wgui.services.KeyPairService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implementation for managing Wireguard key pairs.
 */
@Service
@Transactional
public class KeyPairServiceImpl implements KeyPairService {

    private final KeyPairRepository keyPairRepository;

    /**
     * Constructs a new instance of KeyPairServiceImpl.
     *
     * @param keyPairRepository The key pair repository.
     */
    @Autowired
    public KeyPairServiceImpl(KeyPairRepository keyPairRepository) {
        this.keyPairRepository = keyPairRepository;
    }

    /**
     * Saves a key pair.
     *
     * @param keyPair The key pair to save.
     * @return The saved key pair.
     */
    @Override
    public KeyPair save(KeyPair keyPair) {
        return keyPairRepository.save(keyPair);
    }

    /**
     * Deletes a key pair.
     *
     * @param keyPair The key pair to delete.
     */
    @Override
    public void delete(KeyPair keyPair) {
        keyPairRepository.delete(keyPair);
    }
}
