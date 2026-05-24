package com.fortifile.crypto;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import java.io.*;
import java.util.function.Consumer;

/**
 * Streaming file encryption and decryption service.
 * Uses 64 KB chunked I/O — never loads entire file into memory.
 */
public class FileCryptoService {

    private static final int BUFFER_SIZE = 64 * 1024; // 64 KB

    /**
     * Encrypts inputFile → outputFile.
     * Output format: [salt(16 B)][nonce(12 B)][ciphertext + GCM tag(16 B)]
     */
    public static void encryptFile(File inputFile, File outputFile,
                                   char[] password,
                                   Consumer<Integer> progressCallback) throws Exception {
        byte[]    salt   = CryptoUtils.generateSalt();
        byte[]    nonce  = CryptoUtils.generateNonce();
        SecretKey key    = CryptoUtils.deriveKey(password, salt);
        Cipher    cipher = CryptoUtils.initCipher(Cipher.ENCRYPT_MODE, key, nonce);

        long totalBytes     = inputFile.length();
        long processedBytes = 0;

        try (FileInputStream     fis = new FileInputStream(inputFile);
             FileOutputStream    fos = new FileOutputStream(outputFile);
             CipherOutputStream  cos = new CipherOutputStream(fos, cipher)) {

            fos.write(salt);   // write 16-byte salt header
            fos.write(nonce);  // write 12-byte nonce header

            byte[] buffer = new byte[BUFFER_SIZE];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                cos.write(buffer, 0, bytesRead);
                processedBytes += bytesRead;
                if (progressCallback != null && totalBytes > 0) {
                    int progress = (int) ((processedBytes * 100) / totalBytes);
                    progressCallback.accept(progress);
                }
            }
            cos.flush();
        }
        if (progressCallback != null) progressCallback.accept(100);
    }

    /**
     * Decrypts a file previously encrypted by encryptFile().
     * Reads [salt(16 B)][nonce(12 B)] header, then streams decrypted content.
     */
    public static void decryptFile(File inputFile, File outputFile,
                                   char[] password,
                                   Consumer<Integer> progressCallback) throws Exception {
        try (FileInputStream fis = new FileInputStream(inputFile)) {

            byte[] salt = new byte[CryptoUtils.SALT_LENGTH];
            if (fis.read(salt) != CryptoUtils.SALT_LENGTH) {
                throw new IOException("Invalid encrypted file: salt header missing.");
            }

            byte[] nonce = new byte[CryptoUtils.GCM_IV_LENGTH];
            if (fis.read(nonce) != CryptoUtils.GCM_IV_LENGTH) {
                throw new IOException("Invalid encrypted file: nonce header missing.");
            }

            SecretKey key    = CryptoUtils.deriveKey(password, salt);
            Cipher    cipher = CryptoUtils.initCipher(Cipher.DECRYPT_MODE, key, nonce);

            long encryptedDataSize = inputFile.length()
                    - CryptoUtils.SALT_LENGTH
                    - CryptoUtils.GCM_IV_LENGTH;
            long processedBytes = 0;

            try (CipherInputStream  cis = new CipherInputStream(fis, cipher);
                 FileOutputStream   fos = new FileOutputStream(outputFile)) {

                byte[] buffer = new byte[BUFFER_SIZE];
                int bytesRead;
                while ((bytesRead = cis.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                    processedBytes += bytesRead;
                    if (progressCallback != null && encryptedDataSize > 0) {
                        int progress = (int) ((processedBytes * 100) / encryptedDataSize);
                        progressCallback.accept(Math.min(progress, 99));
                    }
                }
            }
        }
        if (progressCallback != null) progressCallback.accept(100);
    }
}