package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import config.AppConfig;

public class MainMenuView extends JFrame {
    
    private String selectedUserType = null;
    private boolean exitSelected = false;
    
    public MainMenuView() {
        super("App Logros Personales - Menú Principal");
        initializeComponents();
        setupLayout();
        setupEventListeners();
    }
    
    private void initializeComponents() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(PRIMARY_BG);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Panel principal con padding
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(PRIMARY_BG);
        mainPanel.setBorder(new EmptyBorder(40, 40, 40, 40));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        // Header con título y logo
        JPanel headerPanel = createHeaderPanel();
        
        // Panel de opciones
        JPanel optionsPanel = createOptionsPanel();
        
        // Footer
        JPanel footerPanel = createFooterPanel();
        
        mainPanel.add(headerPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        mainPanel.add(optionsPanel);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(footerPanel);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_BG);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        
        // Logo/Icono principal
        JLabel logoLabel = new JLabel();
        logoLabel.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 64));
        logoLabel.setForeground(ACCENT_COLOR);
        logoLabel.setText("🏆");
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Título principal
        JLabel titleLabel = new JLabel("App Logros Personales");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(WHITE_TEXT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Subtítulo
        JLabel subtitleLabel = new JLabel("Gestiona tus logros y metas personales");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(TEXT_COLOR);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(logoLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        headerPanel.add(subtitleLabel);
        
        return headerPanel;
    }
    
    private JPanel createOptionsPanel() {
        JPanel optionsPanel = new JPanel();
        optionsPanel.setBackground(SECONDARY_BG);
        optionsPanel.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(15),
            new EmptyBorder(30, 30, 30, 30)
        ));
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        
        // Título de la sección
        JLabel sectionTitle = new JLabel("Selecciona tu tipo de acceso");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        sectionTitle.setForeground(WHITE_TEXT);
        sectionTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        optionsPanel.add(sectionTitle);
        optionsPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        
        // Botón de Administrador
        JButton adminButton = createUserTypeButton("👨‍💼 Administrador", 
            "Gestiona usuarios y configuraciones del sistema");
        adminButton.addActionListener(e -> {
            selectedUserType = "Admin";
            setVisible(false);
        });
        
        // Botón de Usuario
        JButton userButton = createUserTypeButton("👤 Usuario", 
            "Accede a tus logros personales y al tablero común");
        userButton.addActionListener(e -> {
            selectedUserType = "User";
            setVisible(false);
        });
        
        // Botón de Salir
        JButton exitButton = createExitButton();
        exitButton.addActionListener(e -> {
            exitSelected = true;
            System.exit(0);
        });
        
        optionsPanel.add(adminButton);
        optionsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        optionsPanel.add(userButton);
        optionsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        optionsPanel.add(exitButton);
        
        return optionsPanel;
    }
    
    private JButton createUserTypeButton(String title, String description) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(BUTTON_COLOR);
        buttonPanel.setBorder(new EmptyBorder(20, 25, 20, 25));
        buttonPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(WHITE_TEXT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel descLabel = new JLabel("<html><center>" + description + "</center></html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descLabel.setForeground(new Color(230, 230, 250));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        buttonPanel.add(titleLabel);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        buttonPanel.add(descLabel);
        
        JButton button = new JButton();
        button.setLayout(new BorderLayout());
        button.add(buttonPanel, BorderLayout.CENTER);
        button.setBackground(BUTTON_COLOR);
        button.setBorder(new RoundedBorder(12));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
        button.setPreferredSize(new Dimension(380, 90));
        
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
        JButton button = new JButton("🚪 Salir");
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(WHITE_TEXT);
        button.setBackground(new Color(180, 70, 70));
        button.setFocusPainted(false);
        button.setBorder(new RoundedBorder(10));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        button.setPreferredSize(new Dimension(380, 50));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Efectos hover
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(210, 100, 100));
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(180, 70, 70));
            }
        });
        
        return button;
    }
    
    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(PRIMARY_BG);
        footerPanel.setLayout(new BoxLayout(footerPanel, BoxLayout.Y_AXIS));
        
        JLabel versionLabel = new JLabel("Versión 1.0.0");
        versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        versionLabel.setForeground(TEXT_COLOR);
        versionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel authorLabel = new JLabel("Desarrollado por Juan Otálora, Johan Bermeo");
        authorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        authorLabel.setForeground(TEXT_COLOR);
        authorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        footerPanel.add(versionLabel);
        footerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        footerPanel.add(authorLabel);
        
        return footerPanel;
    }
    
    private void setupEventListeners() {
        // Los event listeners ya están configurados en los métodos create
    }
    
    // Getters
    public String getSelectedUserType() {
        return selectedUserType;
    }
    
    public boolean isExitSelected() {
        return exitSelected;
    }
    
    // Método para mostrar el menú y esperar selección
    public String showMenuAndWait() {
        setVisible(true);
        
        // Esperar hasta que se seleccione una opción
        while (isVisible() && selectedUserType == null && !exitSelected) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        
        return selectedUserType;
    }
    
    // Clase para bordes redondeados reutilizable
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
            
            // Si el componente tiene un background color, lo usamos
            if (c.getBackground() != null) {
                g2.setColor(c.getBackground());
                g2.fill(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
            }
            
            g2.dispose();
        }
    }
}
