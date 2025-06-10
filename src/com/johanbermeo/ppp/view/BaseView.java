package view;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class BaseView extends JFrame {

	private Boolean isExit = false;
    private List<String> listaLogros = new ArrayList<>();

    public BaseView() {
    	super("App Logros Personales");
    	init();
    }
    
    private void init() {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(450, 350);
        setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(new Color(30, 30, 60));
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        setContentPane(panelPrincipal);

        JLabel titulo = new JLabel("App Logros Personales");
        titulo.setForeground(new Color(200, 200, 255));
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(new EmptyBorder(0, 0, 20, 0));
        panelPrincipal.add(titulo);

        JButton btnRegistrar = crearBoton("Registrar nuevo logro");
        JButton btnVerLogros = crearBoton("Ver mis logros");
        JButton btnTablero = crearBoton("Ver Tablero Común");
        JButton btnSalir = crearBoton("Salir");

        panelPrincipal.add(btnRegistrar);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPrincipal.add(btnVerLogros);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPrincipal.add(btnTablero);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 10)));
        panelPrincipal.add(btnSalir);

        btnRegistrar.setToolTipText("Agrega un nuevo logro personal");
        btnVerLogros.setToolTipText("Muestra tus logros registrados");
        btnTablero.setToolTipText("Consulta el tablero común con estadísticas");
        btnSalir.setToolTipText("Salir de la aplicación");

        btnRegistrar.addActionListener(e -> mostrarFormularioRegistro());
        btnVerLogros.addActionListener(e -> mostrarLogros());
        btnTablero.addActionListener(e -> mostrarTablero());
        btnSalir.addActionListener(e -> {
        	System.exit(0);
        	isExit = true;
        });
        
    }

    
    public Boolean getExit() {
    	return isExit;
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        boton.setBackground(new Color(70, 130, 180));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(100, 160, 210));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(70, 130, 180));
            }
        });
        return boton;
    }

    // Diálogo personalizado para registrar logro
    private void mostrarFormularioRegistro() {
        JDialog dialog = new JDialog(this, "Registrar Logro", true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(new Color(30, 30, 60));

        JPanel panelCampos = new JPanel();
        panelCampos.setBackground(new Color(30, 30, 60));
        panelCampos.setBorder(new EmptyBorder(15, 15, 15, 15));
        panelCampos.setLayout(new BoxLayout(panelCampos, BoxLayout.Y_AXIS));

        JLabel lblNombre = new JLabel("Nombre del logro:");
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JTextField nombreField = new JTextField();
        nombreField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        JLabel lblDescripcion = new JLabel("Descripción:");
        lblDescripcion.setForeground(Color.WHITE);
        lblDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JTextArea descripcionArea = new JTextArea(5, 20);
        descripcionArea.setLineWrap(true);
        descripcionArea.setWrapStyleWord(true);
        JScrollPane scrollDescripcion = new JScrollPane(descripcionArea);
        scrollDescripcion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        panelCampos.add(lblNombre);
        panelCampos.add(nombreField);
        panelCampos.add(Box.createRigidArea(new Dimension(0, 10)));
        panelCampos.add(lblDescripcion);
        panelCampos.add(scrollDescripcion);

        dialog.add(panelCampos, BorderLayout.CENTER);

        // Crear una función que exporte los logros y los envie al modelo para guardado
        // ----------------------------------------------------------------------------

        // ----------------------------------------------------------------------------

        // Panel botones
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(30, 30, 60));
        JButton btnAceptar = crearBoton("Aceptar");
        JButton btnCancelar = crearBoton("Cancelar");
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        dialog.add(panelBotones, BorderLayout.SOUTH);

        btnAceptar.addActionListener(e -> {
            String nombre = nombreField.getText().trim();
            String descripcion = descripcionArea.getText().trim();

            if (nombre.isEmpty() || descripcion.isEmpty()) {
                JOptionPane.showMessageDialog(dialog,
                        "Por favor complete todos los campos.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String logro = nombre + ": " + descripcion;
                listaLogros.add(logro);
                JOptionPane.showMessageDialog(dialog,
                        "Logro registrado con éxito:\n" + nombre,
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            }
        });

        btnCancelar.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }


    // Hacer que el controlador traiga la información de cada usuario y la presente
    // -----------------------------------------------------
    private void mostrarLogros() {
    if (listaLogros.isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "No tienes logros registrados.",
                "Mis Logros", JOptionPane.INFORMATION_MESSAGE);
        return;
    }
    // -----------------------------------------------------

    JDialog dialog = new JDialog(this, "Mis Logros", true);
    dialog.setSize(450, 350);
    dialog.setLocationRelativeTo(this);
    dialog.getContentPane().setBackground(new Color(30, 30, 60));
    dialog.setLayout(new BorderLayout());

    // Panel principal con padding y fondo
    JPanel panelPrincipal = new JPanel();
    panelPrincipal.setBackground(new Color(30, 30, 60));
    panelPrincipal.setBorder(new EmptyBorder(15, 15, 15, 15));
    panelPrincipal.setLayout(new BorderLayout());
    dialog.add(panelPrincipal, BorderLayout.CENTER);

    // Área de texto con logros
    JTextArea areaLogros = new JTextArea();
    areaLogros.setEditable(false);
    areaLogros.setLineWrap(true);
    areaLogros.setWrapStyleWord(true);
    areaLogros.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    areaLogros.setForeground(Color.WHITE);
    areaLogros.setBackground(new Color(50, 50, 80));
    areaLogros.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    StringBuilder sb = new StringBuilder();
    int count = 1;
    for (String logro : listaLogros) {
        sb.append(count++).append(". ").append(logro).append("\n\n");
    }
    areaLogros.setText(sb.toString());

    JScrollPane scroll = new JScrollPane(areaLogros);
    scroll.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));
    panelPrincipal.add(scroll, BorderLayout.CENTER);

    // Panel de botones
    JPanel panelBotones = new JPanel();
    panelBotones.setBackground(new Color(30, 30, 60));
    JButton btnCerrar = crearBoton("Cerrar");
    btnCerrar.addActionListener(e -> dialog.dispose());
    panelBotones.add(btnCerrar);
    dialog.add(panelBotones, BorderLayout.SOUTH);

    dialog.setVisible(true);
}


    // Diálogo estilizado para mostrar tablero común
    private void mostrarTablero() {
        JDialog dialog = new JDialog(this, "Tablero Común", true);
        dialog.setSize(350, 200);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(new Color(30, 30, 60));
        dialog.setLayout(new BorderLayout());

        JPanel panelContenido = new JPanel();
        panelContenido.setBackground(new Color(30, 30, 60));
        panelContenido.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));

        // Hacer que el controlador traiga la información de los usuarios y la presente
        // ----------------------------------------------------------------------
        JLabel lblUsuarioTop = new JLabel("Usuario con más logros: Ana");
        JLabel lblLogroComun = new JLabel("Logro más común: 'Aprendí POO'");
        JLabel lblTotal = new JLabel("Total logros registrados: " + listaLogros.size());
        // -------------------------------------------------------------------


        Font fuente = new Font("Segoe UI", Font.PLAIN, 16);
        Color colorTexto = Color.WHITE;

        lblUsuarioTop.setFont(fuente);
        lblUsuarioTop.setForeground(colorTexto);
        lblLogroComun.setFont(fuente);
        lblLogroComun.setForeground(colorTexto);
        lblTotal.setFont(fuente);
        lblTotal.setForeground(colorTexto);

        panelContenido.add(lblUsuarioTop);
        panelContenido.add(Box.createRigidArea(new Dimension(0, 10)));
        panelContenido.add(lblLogroComun);
        panelContenido.add(Box.createRigidArea(new Dimension(0, 10)));
        panelContenido.add(lblTotal);

        dialog.add(panelContenido, BorderLayout.CENTER);

        JButton btnCerrar = crearBoton("Cerrar");
        btnCerrar.addActionListener(e -> dialog.dispose());

        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(new Color(30, 30, 60));
        panelBotones.add(btnCerrar);

        dialog.add(panelBotones, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}
