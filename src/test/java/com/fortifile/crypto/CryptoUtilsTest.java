package com.fortifile.crypto;

import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CryptoUtils and FileCryptoService.
 * Verifies round-trip encryption/decryption and tamper detection.
 */
class CryptoUtilsTest {

    private static final char[] PASSWORD  = "Test@Password123!".toCharArray();
    private File tempInput, tempEncrypted, tempDecrypted;

    @BeforeEach
    void setup() throws IOException {
        tempInput     = File.createTempFile("fortifile_test_input",  ".txt");
        tempEncrypted = File.createTempFile("fortifile_test_enc",    ".enc");
        tempDecrypted = File.createTempFile("fortifile_test_output", ".txt");

        Files.writeString(tempInput.toPath(), "Hello, FortiFile! AES-256-GCM test payload.");
    }

    @AfterEach
    void cleanup() {
        tempInput.delete();
        tempEncrypted.delete();
        tempDecrypted.delete();
    }

    @Test
    @DisplayName("Round-trip: encrypt then decrypt produces identical content")
    void testRoundTrip() throws Exception {
        FileCryptoService.encryptFile(tempInput, tempEncrypted, PASSWORD, null);
        FileCryptoService.decryptFile(tempEncrypted, tempDecrypted, PASSWORD, null);

        String original  = Files.readString(tempInput.toPath());
        String recovered = Files.readString(tempDecrypted.toPath());
        assertEquals(original, recovered, "Decrypted content must match the original.");
    }

    @Test
    @DisplayName("Wrong password throws an exception (GCM auth tag mismatch)")
    void testWrongPasswordFails() {
        assertThrows(Exception.class, () -> {
            FileCryptoService.encryptFile(tempInput, tempEncrypted, PASSWORD, null);
            FileCryptoService.decryptFile(tempEncrypted, tempDecrypted,
                    "WrongPassword!".toCharArray(), null);
        });
    }

    @Test
    @DisplayName("Tampered ciphertext is rejected (GCM integrity check)")
    void testTamperedFileFails() throws Exception {
        FileCryptoService.encryptFile(tempInput, tempEncrypted, PASSWORD, null);

        // Flip a byte deep in the ciphertext (after 28-byte header)
        byte[] bytes = Files.readAllBytes(tempEncrypted.toPath());
        bytes[40] ^= 0xFF;
        Files.write(tempEncrypted.toPath(), bytes);

        assertThrows(Exception.class, () ->
                FileCryptoService.decryptFile(tempEncrypted, tempDecrypted, PASSWORD, null));
    }

    @Test
    @DisplayName("Salt generation always produces 16 unique bytes")
    void testSaltUniqueness() {
        byte[] s1 = CryptoUtils.generateSalt();
        byte[] s2 = CryptoUtils.generateSalt();
        assertEquals(16, s1.length);
        assertFalse(java.util.Arrays.equals(s1, s2), "Two salts must not be identical.");
    }

    @Test
    @DisplayName("Nonce generation always produces 12 unique bytes")
    void testNonceUniqueness() {
        byte[] n1 = CryptoUtils.generateNonce();
        byte[] n2 = CryptoUtils.generateNonce();
        assertEquals(12, n1.length);
        assertFalse(java.util.Arrays.equals(n1, n2), "Two nonces must not be identical.");
    }
}