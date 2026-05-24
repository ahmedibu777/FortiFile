package com.fortifile;

import com.fortifile.ui.ConsoleApp;
import com.fortifile.ui.SwingGUI;

import javax.swing.SwingUtilities;

/**
 * Entry point for FortiFile.
 *
 * Usage:
 *   java -jar fortifile.jar          → Console mode
 *   java -jar fortifile.jar --gui    → Swing GUI mode
 */
public class Main {
    public static void main(String[] args) {
        boolean useGui = args.length > 0 && "--gui".equalsIgnoreCase(args[0]);
        if (useGui) {
            SwingUtilities.invokeLater(() -> new SwingGUI().start());
        } else {
            new ConsoleApp().start();
        }
    }
}