package auth;

import javax.swing.SwingUtilities;

public class Main {
	public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            View ventana = new View();
            ventana.setVisible(true);
        });
    }
}
