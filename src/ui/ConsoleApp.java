package ui;

import crypto.FileCryptoService;

import java.io.Console;
import java.io.File;
import java.util.Scanner;

/**
 * Console-driven user interface for the file encryption system.
 */
public class ConsoleApp {

    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("=================================================");
        System.out.println("     FortiFile - Secure File Encryption System   ");
        System.out.println("=================================================");
        System.out.println("Algorithms: AES-256-GCM | PBKDF2-HMAC-SHA256");
        System.out.println();

        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> handleEncrypt();
                case "2" -> handleDecrypt();
                case "3" -> {
                    System.out.println("Exiting FortiFile. Stay secure.");
                    return;
                }
                default -> System.out.println("Invalid option. Please choose 1-3.\n");
            }
        }
    }

    private void printMenu() {
        System.out.println("Main Menu:");
        System.out.println("  [1] Encrypt a File");
        System.out.println("  [2] Decrypt a File");
        System.out.println("  [3] Exit");
        System.out.print("Enter choice: ");
    }

    private void handleEncrypt() {
        try {
            File input = promptFile("Enter path of file to encrypt: ");
            if (!input.exists()) {
                System.out.println("Error: File not found.\n");
                return;
            }
            File output = promptFile("Enter output encrypted file path (.enc): ");
            char[] password = promptPassword("Enter encryption password: ");

            System.out.println("Encrypting... ");
            FileCryptoService.encryptFile(input, output, password, p -> {
                if (p % 10 == 0) System.out.print(p + "% ");
            });
            System.out.println("\nEncryption complete: " + output.getAbsolutePath() + "\n");

            java.util.Arrays.fill(password, '\0'); // Clear password from memory
        } catch (Exception e) {
            System.out.println("Encryption failed: " + e.getMessage() + "\n");
        }
    }

    private void handleDecrypt() {
        try {
            File input = promptFile("Enter path of encrypted file: ");
            if (!input.exists()) {
                System.out.println("Error: File not found.\n");
                return;
            }
            File output = promptFile("Enter output decrypted file path: ");
            char[] password = promptPassword("Enter decryption password: ");

            System.out.println("Decrypting... ");
            FileCryptoService.decryptFile(input, output, password, p -> {
                if (p % 10 == 0) System.out.print(p + "% ");
            });
            System.out.println("\nDecryption complete: " + output.getAbsolutePath() + "\n");

            java.util.Arrays.fill(password, '\0');
        } catch (Exception e) {
            System.out.println("Decryption failed: " + e.getMessage() + "\n");
        }
    }

    private File promptFile(String message) {
        System.out.print(message);
        return new File(scanner.nextLine().trim());
    }

    private char[] promptPassword(String message) {
        Console console = System.console();
        if (console != null) {
            return console.readPassword(message);
        } else {
            System.out.print(message);
            return scanner.nextLine().toCharArray();
        }
    }
}