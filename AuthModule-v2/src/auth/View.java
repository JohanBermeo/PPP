import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class View extends JFrame {

    public VistaLogrosSwing() {
        super("App Logros Personales");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        // Botones del menú
        JButton btnRegistrar = new JButton("Registrar nuevo logro");
        JButton btnVerLogros = new JButton("Ver mis logros");
        JButton btnTablero = new JButton("Ver Tablero Común");
        JButton btnSalir = new JButton("Salir");

        add(btnRegistrar);
        add(btnVerLogros);
        add(btnTablero);
        add(btnSalir);

        // Eventos
        btnRegistrar.addActionListener(e -> mostrarFormularioRegistro());
        btnVerLogros.addActionListener(e -> mostrarLogrosEjemplo());
        btnTablero.addActionListener(e -> mostrarTableroEjemplo());
        btnSalir.addActionListener(e -> System.exit(0));
    }

    // Formulario para registrar un logro
    private void mostrarFormularioRegistro() {
        JTextField nombreField = new JTextField();
        JTextField descripcionField = new JTextField();
        SpinnerNumberModel modeloDificultad = new SpinnerNumberModel(1, 1, 5, 1);
        JSpinner dificultadSpinner = new JSpinner(modeloDificultad);

        Object[] campos = {
            "Nombre del logro:", nombreField,
            "Descripción:", descripcionField,
            "Nivel de dificultad (1-5):", dificultadSpinner
        };

        int opcion = JOptionPane.showConfirmDialog(this, campos, 
            "Registrar Logro", JOptionPane.OK_CANCEL_OPTION);

        if (opcion == JOptionPane.OK_OPTION) {
            String nombre = nombreField.getText().trim();
            String descripcion = descripcionField.getText().trim();
            int dificultad = (int) dificultadSpinner.getValue();

            if (nombre.isEmpty() || descripcion.isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor complete todos los campos.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Aquí se enviaría el logro al controlador
                JOptionPane.showMessageDialog(this, 
                    "Logro registrado:\n" + nombre + " (Dificultad: " + dificultad + ")", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // Mostrar logros (ejemplo estático)
    private void mostrarLogrosEjemplo() {
        String logros = "- Aprendí POO\n- Implementé patrón MVC\n- Creé mi primera app Java";
        JOptionPane.showMessageDialog(this, logros, "Mis Logros", JOptionPane.INFORMATION_MESSAGE);
    }

    // Mostrar tablero común (ejemplo estático)
    private void mostrarTableroEjemplo() {
        String tablero = "Usuario con más logros: Ana\nLogro más común: 'Aprendí POO'\nTotal logros: 42";
        JOptionPane.showMessageDialog(this, tablero, "Tablero Común", JOptionPane.INFORMATION_MESSAGE);
    }

    
}
