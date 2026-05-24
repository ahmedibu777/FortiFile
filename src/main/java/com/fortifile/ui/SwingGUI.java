package com.fortifile.ui;

import com.fortifile.crypto.FileCryptoService;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Optional Swing-based GUI for FortiFile.
 * Runs crypto operations on a background SwingWorker thread to keep the UI responsive.
 */
public class SwingGUI {

    public void start() {
        JFrame frame = new JFrame("FortiFile - Secure Encryption");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLayout(new GridLayout(6, 1, 10, 10));
        frame.setLocationRelativeTo(null);

        JLabel title = new JLabel("FortiFile — AES-256-GCM Encryption", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        frame.add(title);

        JButton      encryptBtn  = new JButton("Encrypt File");
        JButton      decryptBtn  = new JButton("Decrypt File");
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        JLabel status = new JLabel("Ready", SwingConstants.CENTER);

        encryptBtn.addActionListener(e -> runCrypto(true,  progressBar, status, frame));
        decryptBtn.addActionListener(e -> runCrypto(false, progressBar, status, frame));

        frame.add(encryptBtn);
        frame.add(decryptBtn);
        frame.add(progressBar);
        frame.add(status);

        frame.setVisible(true);
    }

    private void runCrypto(boolean encrypt, JProgressBar bar,
                           JLabel status, JFrame parent) {
        JFileChooser chooser = new JFileChooser();
        if (chooser.showOpenDialog(parent) != JFileChooser.APPROVE_OPTION) return;

        File   input       = chooser.getSelectedFile();
        String defaultOut  = input.getAbsolutePath() + (encrypt ? ".enc" : ".dec");
        String outputPath  = JOptionPane.showInputDialog(parent, "Save output as:", defaultOut);
        if (outputPath == null || outputPath.isBlank()) return;

        JPasswordField pf = new JPasswordField();
        int ok = JOptionPane.showConfirmDialog(parent, pf,
                "Enter password:", JOptionPane.OK_CANCEL_OPTION);
        if (ok != JOptionPane.OK_OPTION) return;

        char[] password = pf.getPassword();
        File   output   = new File(outputPath);

        SwingWorker<Void, Integer> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                if (encrypt) {
                    FileCryptoService.encryptFile(input, output, password, this::publish);
                } else {
                    FileCryptoService.decryptFile(input, output, password, this::publish);
                }
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                int latest = chunks.get(chunks.size() - 1);
                bar.setValue(latest);
                bar.setString(latest + "%");
                status.setText((encrypt ? "Encrypting: " : "Decrypting: ") + latest + "%");
            }

            @Override
            protected void done() {
                try {
                    get(); // re-throws any exception from doInBackground
                    status.setText(encrypt ? "✅ Encryption complete!" : "✅ Decryption complete!");
                } catch (Exception ex) {
                    Throwable cause = ex.getCause() != null ? ex.getCause() : ex;
                    status.setText("❌ Error: " + cause.getMessage());
                } finally {
                    Arrays.fill(password, '\0'); // wipe password from memory
                }
            }
        };
        worker.execute();
    }
}