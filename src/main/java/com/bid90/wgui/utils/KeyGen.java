package com.bid90.wgui.utils;


import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.generators.X25519KeyPairGenerator;
import org.bouncycastle.crypto.params.X25519KeyGenerationParameters;
import org.bouncycastle.crypto.params.X25519PrivateKeyParameters;
import org.bouncycastle.crypto.params.X25519PublicKeyParameters;
import org.bouncycastle.util.encoders.Base64;

import java.security.SecureRandom;

/**
 * KeyGen class for generating WireGuard-compatible keys.
 */
public class KeyGen {

    /**
     * Generates a new WireGuard key pair along with a pre-shared key.
     *
     * @return A Key object containing the generated public key, private key, and pre-shared key.
     */
    public static Key generate() {
        // Initialize the Curve25519 key pair generator
        X25519KeyPairGenerator keyPairGenerator = new X25519KeyPairGenerator();
        SecureRandom random = new SecureRandom();
        X25519KeyGenerationParameters keyGenParams = new X25519KeyGenerationParameters(random);

        keyPairGenerator.init(keyGenParams);

        // Generate a new WireGuard key pair
        AsymmetricCipherKeyPair keyPair = keyPairGenerator.generateKeyPair();

        // Get the private and public keys
        X25519PrivateKeyParameters privateKey = (X25519PrivateKeyParameters) keyPair.getPrivate();
        X25519PublicKeyParameters publicKey = (X25519PublicKeyParameters) keyPair.getPublic();

        // Convert the keys to WireGuard format
        var privateKeyWireGuard = encodePrivateKey(privateKey);
        var publicKeyWireGuard = encodePublicKey(publicKey);

        // Generate a new pre-shared key
        String preSharedKey = generatePreSharedKey(32);

        return new Key(publicKeyWireGuard, privateKeyWireGuard, preSharedKey);
    }

    /**
     * Encodes the private key in WireGuard format.
     *
     * @param privateKey The private key to encode.
     * @return The encoded private key as a string in WireGuard format.
     */
    private static String encodePrivateKey(X25519PrivateKeyParameters privateKey) {
        // Encode the private key in WireGuard format
        byte[] publicKeyBytes = privateKey.getEncoded();

        // Convert the key to Base64
        return Base64.toBase64String(publicKeyBytes);
    }

    /**
     * Encodes the public key in WireGuard format.
     *
     * @param publicKey The public key to encode.
     * @return The encoded public key as a string in WireGuard format.
     */
    private static String encodePublicKey(X25519PublicKeyParameters publicKey) {
        // Encode the public key in WireGuard format
        byte[] publicKeyBytes = publicKey.getEncoded();

        // Convert the key to Base64
        return Base64.toBase64String(publicKeyBytes);
    }

    /**
     * Generates a random pre-shared key of the specified length.
     *
     * @param length The length of the pre-shared key in bytes.
     * @return The generated pre-shared key as a string in Base64 format.
     */
    public static String generatePreSharedKey(int length) {
        // Generate a random pre-shared key
        SecureRandom random = new SecureRandom();
        byte[] pskBytes = new byte[length];
        random.nextBytes(pskBytes);

        // Convert the key to Base64
        return Base64.toBase64String(pskBytes);
    }

    /**
     * Represents a WireGuard key consisting of a public key, private key, and pre-shared key.
     */
    public static class Key {
        String publicKey;
        String privateKey;
        String preSharedKey;

        /**
         * Constructs a Key object with the provided public key, private key, and pre-shared key.
         *
         * @param publicKey     The public key.
         * @param privateKey    The private key.
         * @param preSharedKey  The pre-shared key.
         */
        public Key(String publicKey, String privateKey, String preSharedKey) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
            this.preSharedKey = preSharedKey;
        }

        /**
         * Returns the public key.
         *
         * @return The public key.
         */
        public String getPublicKey() {
            return publicKey;
        }

        /**
         * Returns the private key.
         *
         * @return The private key.
         */
        public String getPrivateKey() {
            return privateKey;
        }

        /**
         * Returns the pre-shared key.
         *
         * @return The pre-shared key.
         */
        public String getPreSharedKey() {
            return preSharedKey;
        }
    }
}