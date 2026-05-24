package com.fortifile.crypto;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * Cryptographic utilities for key derivation, AES-GCM cipher creation,
 * and secure random nonce generation.
 */
public class CryptoUtils {

    public static final String ALGORITHM      = "AES";
    public static final String TRANSFORMATION = "AES/GCM/NoPadding";
    public static final int    GCM_TAG_LENGTH = 128; // bits
    public static final int    GCM_IV_LENGTH  = 12;  // bytes (96-bit nonce)

    public static final String KDF_ALGORITHM  = "PBKDF2WithHmacSHA256";
    public static final int    KDF_ITERATIONS = 65536;
    public static final int    KEY_LENGTH     = 256;  // bits
    public static final int    SALT_LENGTH    = 16;   // bytes

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private CryptoUtils() { /* utility class — do not instantiate */ }

    /** Derives a 256-bit AES key from a password and salt using PBKDF2. */
    public static SecretKey deriveKey(char[] password, byte[] salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance(KDF_ALGORITHM);
        KeySpec spec = new PBEKeySpec(password, salt, KDF_ITERATIONS, KEY_LENGTH);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), ALGORITHM);
    }

    /** Generates a fresh random 12-byte nonce for GCM. */
    public static byte[] generateNonce() {
        byte[] nonce = new byte[GCM_IV_LENGTH];
        SECURE_RANDOM.nextBytes(nonce);
        return nonce;
    }

    /** Generates a fresh random 16-byte salt for PBKDF2. */
    public static byte[] generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        SECURE_RANDOM.nextBytes(salt);
        return salt;
    }

    /** Creates a GCMParameterSpec from a nonce byte array. */
    public static GCMParameterSpec createGcmSpec(byte[] nonce) {
        return new GCMParameterSpec(GCM_TAG_LENGTH, nonce);
    }

    /** Initializes a Cipher for AES-GCM in ENCRYPT_MODE or DECRYPT_MODE. */
    public static Cipher initCipher(int mode, SecretKey key, byte[] nonce)
            throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(mode, key, createGcmSpec(nonce));
        return cipher;
    }
}