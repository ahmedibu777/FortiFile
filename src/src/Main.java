import ui.ConsoleApp;
import ui.SwingGUI;

/**
 * Entry point for FortiFile.
 * Usage: java Main [--gui]
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