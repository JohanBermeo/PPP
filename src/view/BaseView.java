package view;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import config.AppConfig;

public class BaseView extends JFrame {

	private Boolean isExit = false;
    private List<String> listaLogros = new ArrayList<>();
    private String currentUser = "Usuario";

    public BaseView() {
    	super("App Logros Personales");
    	init();
    }

    public BaseView(String username) {
        super("App Logros Personales - " + username);
        this.currentUser = username;
        init();
    }
    
    private void init() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(550, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(PRIMARY_BG);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(PRIMARY_BG);
        panelPrincipal.setBorder(new EmptyBorder(30, 30, 30, 30));
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        setContentPane(panelPrincipal);

        JPanel headerPanel = createHeaderPanel();

        JPanel optionsPanel = createOptionsPanel();

        JPanel footerPanel = createFooterPanel();

        panelPrincipal.add(headerPanel);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 30)));
        panelPrincipal.add(optionsPanel);
        panelPrincipal.add(Box.createVerticalGlue());
        panelPrincipal.add(footerPanel);
    }

    public JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_BG);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        
        JLabel iconLabel = new JLabel("🏆");
        titleLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 48));
        titleLabel.setForeground(WHITE_TEXT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("App Logros Personales");
        titleLabel.setForeground(WHITE_TEXT);
        titelLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel welcomeLabel = new JLabel("Bienvenido, " + currentUser);
        welcomeLabel.setForeground(TEXT_COLOR);
        welcomeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(iconLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        headerPanel.add(welcomeLabel);

        return headerPanel;
    }

    private JPanel createOptionsPanel() {
        JPanel optionsPanel = new JPanel();
        optionsPanel.setBackground(SECONDARY_BG);
        optionsPanel.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(15),
            new EmptyBorder(25, 25, 25, 25)
        ));
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));

        // Título de sección
        JLabel sectionTitle = new JLabel("¿Qué deseas hacer?");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        sectionTitle.setForeground(WHITE_TEXT);
        sectionTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        optionsPanel.add(sectionTitle);
        optionsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Botones principales
        JButton btnRegistrar = createMenuButton("📝 Registrar nuevo logro", 
            "Agrega un nuevo logro personal a tu colección");
        JButton btnVerLogros = createMenuButton("📋 Ver mis logros", 
            "Consulta todos tus logros registrados");
        JButton btnTablero = createMenuButton("📊 Ver Tablero Común", 
            "Consulta el tablero común con estadísticas");
        JButton btnSalir = createExitButton();

        // Event listeners
        btnRegistrar.addActionListener(e -> mostrarFormularioRegistro());
        btnVerLogros.addActionListener(e -> mostrarLogros());
        btnTablero.addActionListener(e -> mostrarTablero());
        btnSalir.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de que deseas salir?",
                "Confirmar salida",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
                isExit = true;
            }
        });

        optionsPanel.add(btnRegistrar);
        optionsPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        optionsPanel.add(btnVerLogros);
        optionsPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        optionsPanel.add(btnTablero);
        optionsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        optionsPanel.add(btnSalir);

        return optionsPanel;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(PRIMARY_BG);
        footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.Y_AXIS));

        JLabel statsLabel = new JLabel("Logros registrados: " + listaLogros.size());
        statsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        statsLabel.setForeground(TEXT_COLOR);
        statsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        footerPanel.add(statsLabel);
        return footerPanel;
    }

    private JButton createMenuButton(String title, String description) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(BUTTON_COLOR);
        buttonPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(WHITE_TEXT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descLabel = new JLabel("<html><center>" + description + "</center></html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        descLabel.setForeground(new Color(230, 230, 250));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(titleLabel);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        buttonPanel.add(descLabel);

        JButton button = new JButton();
        button.setLayout(new BorderLayout());
        button.add(buttonPanel, BorderLayout.CENTER);
        button.setBackground(BUTTON_COLOR);
        button.setBorder(new RoundedBorder(10));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        button.setPreferredSize(new Dimension(450, 70));

        // Efectos hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(BUTTON_HOVER);
                buttonPanel.setBackground(BUTTON_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(BUTTON_COLOR);
                buttonPanel.setBackground(BUTTON_COLOR);
            }
        });

        return button;
    }

    private JButton createExitButton() {
        JButton button = new JButton("🚪 Salir de la aplicación");
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(WHITE_TEXT);
        button.setBackground(ERROR_COLOR);
        button.setFocusPainted(false);
        button.setBorder(new RoundedBorder(8));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        button.setPreferredSize(new Dimension(450, 45));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(210, 100, 100));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(ERROR_COLOR);
            }
        });

        return button;
    }

    public Boolean getExit() {
    	return isExit;
    }

    public void setCurrentUser(String username) {
        this.currentUser = username;
        setTitle("App Logros Personales - " + username);
    }

    private void updateFooterStats() {
        // Buscar y actualizar el label del footer
        Component[] components = getContentPane().getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel) {
                updateStatsLabelRecursive((JPanel) comp);
            }
        }
    }

    private void updateStatsLabelRecursive(JPanel panel) {
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JLabel) {
                JLabel label = (JLabel) comp;
                if (label.getText().startsWith("Logros registrados:")) {
                    label.setText("Logros registrados: " + listaLogros.size());
                    return;
                }
            } else if (comp instanceof JPanel) {
                updateStatsLabelRecursive((JPanel) comp);
            }
        }
    }

    private void mostrarFormularioRegistro() {
        JDialog dialog = new JDialog(this, "Registrar Nuevo Logro", true);
        dialog.setSize(500, 450);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        dialog.getContentPane().setBackground(PRIMARY_BG);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(SECONDARY_BG);
        headerPanel.setBorder(new EmptyBorder(20, 20, 15, 20));
        
        JLabel headerLabel = new JLabel("🎯 Nuevo Logro Personal");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerLabel.setForeground(WHITE_TEXT);
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(headerLabel);

        // Panel de campos
        JPanel panelCampos = new JPanel();
        panelCampos.setBackground(PRIMARY_BG);
        panelCampos.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelCampos.setLayout(new BoxLayout(panelCampos, BoxLayout.Y_AXIS));

        JLabel lblNombre = createStyledLabel("Nombre del logro:");
        JTextField nombreField = createStyledTextField();

        JLabel lblDescripcion = createStyledLabel("Descripción detallada:");
        JTextArea descripcionArea = new JTextArea(6, 20);
        descripcionArea.setLineWrap(true);
        descripcionArea.setWrapStyleWord(true);
        descripcionArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descripcionArea.setBackground(WHITE_TEXT);
        descripcionArea.setForeground(PRIMARY_BG);
        descripcionArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR, 2),
            new EmptyBorder(10, 10, 10, 10)
        ));
        
        JScrollPane scrollDescripcion = new JScrollPane(descripcionArea);
        scrollDescripcion.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        panelCampos.add(lblNombre);
        panelCampos.add(Box.createRigidArea(new Dimension(0, 8)));
        panelCampos.add(nombreField);
        panelCampos.add(Box.createRigidArea(new Dimension(0, 15)));
        panelCampos.add(lblDescripcion);
        panelCampos.add(Box.createRigidArea(new Dimension(0, 8)));
        panelCampos.add(scrollDescripcion);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(SECONDARY_BG);
        panelBotones.setBorder(new EmptyBorder(15, 15, 20, 15));
        
        JButton btnAceptar = createDialogButton("✓ Registrar", SUCCESS_COLOR, new Color(100, 210, 100));
        JButton btnCancelar = createDialogButton("✗ Cancelar", ERROR_COLOR, new Color(210, 100, 100));
        
        panelBotones.add(btnCancelar);
        panelBotones.add(Box.createRigidArea(new Dimension(15, 0)));
        panelBotones.add(btnAceptar);

        dialog.add(headerPanel, BorderLayout.NORTH);
        dialog.add(panelCampos, BorderLayout.CENTER);
        dialog.add(panelBotones, BorderLayout.SOUTH);

        btnAceptar.addActionListener(e -> {
            String nombre = nombreField.getText().trim();
            String descripcion = descripcionArea.getText().trim();

            if (nombre.isEmpty() || descripcion.isEmpty()) {
                showStyledMessage(dialog, "Por favor complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (nombre.length() < 3) {
                showStyledMessage(dialog, "El nombre del logro debe tener al menos 3 caracteres.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // TODO: Crear una función que exporte los logros y los envíe al modelo para guardado
                String logro = nombre + ": " + descripcion;
                listaLogros.add(logro);
                updateFooterStats();
                showStyledMessage(dialog, "¡Logro registrado exitosamente!\n\n" + nombre, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            }
        });

        btnCancelar.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    private void mostrarLogros() {
        // TODO: Hacer que el controlador traiga la información de cada usuario y la presente
        if (listaLogros.isEmpty()) {
            showStyledMessage(this, "Aún no tienes logros registrados.\n¡Empieza agregando tu primer logro!", 
                "Mis Logros", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(this, "Mis Logros Personales", true);
        dialog.setSize(600, 500);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(PRIMARY_BG);
        dialog.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(SECONDARY_BG);
        headerPanel.setBorder(new EmptyBorder(20, 20, 15, 20));
        
        JLabel headerLabel = new JLabel("📋 Mis Logros (" + listaLogros.size() + ")");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerLabel.setForeground(WHITE_TEXT);
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(headerLabel);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(PRIMARY_BG);
        panelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        panelPrincipal.setLayout(new BorderLayout());

        JTextArea areaLogros = new JTextArea();
        areaLogros.setEditable(false);
        areaLogros.setLineWrap(true);
        areaLogros.setWrapStyleWord(true);
        areaLogros.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        areaLogros.setForeground(WHITE_TEXT);
        areaLogros.setBackground(SECONDARY_BG);
        areaLogros.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < listaLogros.size(); i++) {
            sb.append("🏆 ").append(i + 1).append(". ").append(listaLogros.get(i)).append("\n\n");
        }
        areaLogros.setText(sb.toString());

        JScrollPane scroll = new JScrollPane(areaLogros);
        scroll.setBorder(BorderFactory.createLineBorder(ACCENT_COLOR, 2));
        panelPrincipal.add(scroll, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(SECONDARY_BG);
        panelBotones.setBorder(new EmptyBorder(15, 15, 20, 15));
        
        JButton btnCerrar = createDialogButton("Cerrar", BUTTON_COLOR, BUTTON_HOVER);
        btnCerrar.addActionListener(e -> dialog.dispose());
        panelBotones.add(btnCerrar);

        dialog.add(headerPanel, BorderLayout.NORTH);
        dialog.add(panelPrincipal, BorderLayout.CENTER);
        dialog.add(panelBotones, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void mostrarTablero() {
        JDialog dialog = new JDialog(this, "Tablero Común de Logros", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(PRIMARY_BG);
        dialog.setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(SECONDARY_BG);
        headerPanel.setBorder(new EmptyBorder(20, 20, 15, 20));
        
        JLabel headerLabel = new JLabel("📊 Estadísticas Generales");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        headerLabel.setForeground(WHITE_TEXT);
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(headerLabel);

        JPanel panelContenido = new JPanel();
        panelContenido.setBackground(PRIMARY_BG);
        panelContenido.setBorder(new EmptyBorder(30, 30, 30, 30));
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));

        // TODO: Hacer que el controlador traiga la información de los usuarios y la presente
        JLabel lblUsuarioTop = createInfoLabel("👑 Usuario con más logros: Ana (15 logros)");
        JLabel lblLogroComun = createInfoLabel("🎯 Logro más común: 'Aprendí POO'");
        JLabel lblTotal = createInfoLabel("📈 Total logros registrados: " + getTotalLogros());
        JLabel lblPromedio = createInfoLabel("📊 Promedio por usuario: " + getPromedioLogros() + " logros");

        panelContenido.add(lblUsuarioTop);
        panelContenido.add(Box.createRigidArea(new Dimension(0, 15)));
        panelContenido.add(lblLogroComun);
        panelContenido.add(Box.createRigidArea(new Dimension(0, 15)));
        panelContenido.add(lblTotal);
        panelContenido.add(Box.createRigidArea(new Dimension(0, 15)));
        panelContenido.add(lblPromedio);

        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(SECONDARY_BG);
        panelBotones.setBorder(new EmptyBorder(15, 15, 20, 15));
        
        JButton btnCerrar = createDialogButton("Cerrar", BUTTON_COLOR, BUTTON_HOVER);
        btnCerrar.addActionListener(e -> dialog.dispose());
        panelBotones.add(btnCerrar);

        dialog.add(headerPanel, BorderLayout.NORTH);
        dialog.add(panelContenido, BorderLayout.CENTER);
        dialog.add(panelBotones, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(WHITE_TEXT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(WHITE_TEXT);
        field.setForeground(PRIMARY_BG);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR, 2),
            new EmptyBorder(10, 15, 10, 15)
        ));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setPreferredSize(new Dimension(400, 40));
        return field;
    }

    private JButton createDialogButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(WHITE_TEXT);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorder(new RoundedBorder(8));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(120, 40));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private JLabel createInfoLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        label.setForeground(WHITE_TEXT);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private void showStyledMessage(Component parent, String message, String title, int messageType) {
        JOptionPane.showMessageDialog(parent, message, title, messageType);
    }

    // Métodos auxiliares para estadísticas (temporales hasta implementar modelo)
    private int getTotalLogros() {
        return listaLogros.size(); // TODO: Obtener del modelo/controlador
    }

    private String getPromedioLogros() {
        // TODO: Calcular promedio real desde el modelo
        return "3.2";
    }

    // Clase para bordes redondeados
    public static class RoundedBorder implements javax.swing.border.Border {
        private int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(0, 0, 0, 0);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (c.getBackground() != null) {
                g2.setColor(c.getBackground());
                g2.fill(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
            }
            
            g2.dispose();
        }
    }
}
