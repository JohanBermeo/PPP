package achievements;

import javax.swing.*;
import auth.User;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AchievementsApp {
    private User user;
    private List<String> personalAchievements;
    private List<String> forumAchievements;
    private Properties personalProps;
    private Properties forumProps;
    private static final String DATA_FOLDER = "data";
    private static final String PERSONAL_FILE = DATA_FOLDER + File.separator + "achievements.properties";
    private static final String FORUM_FILE = DATA_FOLDER + File.separator + "forum.properties";
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public AchievementsApp(User user) {
        this.user = user;
        this.personalProps = loadProperties(PERSONAL_FILE);
        this.forumProps = loadProperties(FORUM_FILE);
        this.personalAchievements = loadUserAchievements();
        this.forumAchievements = loadForumAchievements();
    }

    public void start() {
        while (true) {
            String[] options = {"Mis Logros", "Agregar Logro", "Compartir Logro", "Ver Foro", "Eliminar Logro Personal", "Logout"};
            int choice = JOptionPane.showOptionDialog(null,
                    "Bienvenido, " + user.getUsername() + "! Elige una opción:",
                    "Gestor de Logros",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (choice == -1 || choice == 5) {  // Logout or close dialog
                saveUserAchievements();
                saveForumAchievements();
                JOptionPane.showMessageDialog(null, "¡Hasta luego, " + user.getUsername() + "!");
                break;
            }

            switch (choice) {
                case 0:
                    showPersonalAchievements();
                    break;
                case 1:
                    addPersonalAchievement();
                    break;
                case 2:
                    shareAchievementToForum();
                    break;
                case 3:
                    showForum();
                    break;
                case 4:
                    deletePersonalAchievement();
                    break;
            }
        }
    }

    private void showPersonalAchievements() {
        if (personalAchievements.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Aún no tienes logros personales.");
        } else {
            StringBuilder sb = new StringBuilder("Mis Logros:\n\n");
            for (int i = 0; i < personalAchievements.size(); i++) {
                sb.append(i + 1).append(". ").append(personalAchievements.get(i)).append("\n\n");
            }
            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setEditable(false);
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new java.awt.Dimension(500, 400));
            JOptionPane.showMessageDialog(null, scrollPane, "Mis Logros", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void addPersonalAchievement() {
        JTextArea textArea = new JTextArea(5, 30);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        
        int result = JOptionPane.showConfirmDialog(null, scrollPane, 
                "Describe tu logro personal:", JOptionPane.OK_CANCEL_OPTION);
        
        if (result == JOptionPane.OK_OPTION) {
            String achievement = textArea.getText();
            if (achievement != null && !achievement.trim().isEmpty()) {
                String timestamp = dateFormat.format(new Date());
                String formattedAchievement = "[" + timestamp + "] " + achievement.trim();
                personalAchievements.add(formattedAchievement);
                JOptionPane.showMessageDialog(null, "¡Logro agregado exitosamente!");
                saveUserAchievements();
            }
        }
    }

    private void shareAchievementToForum() {
        if (personalAchievements.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tienes logros personales para compartir.");
            return;
        }

        String[] achievementArray = personalAchievements.toArray(new String[0]);
        String selectedAchievement = (String) JOptionPane.showInputDialog(
                null,
                "Selecciona el logro que quieres compartir en el foro:",
                "Compartir Logro",
                JOptionPane.PLAIN_MESSAGE,
                null,
                achievementArray,
                achievementArray[0]);

        if (selectedAchievement != null) {
            // Remover el timestamp del logro personal y agregar info del usuario
            String achievementText = selectedAchievement.substring(selectedAchievement.indexOf("] ") + 2);
            String timestamp = dateFormat.format(new Date());
            String forumPost = "[" + timestamp + "] " + user.getUsername() + ": " + achievementText;
            
            forumAchievements.add(forumPost);
            JOptionPane.showMessageDialog(null, "¡Logro compartido en el foro exitosamente!");
            saveForumAchievements();
        }
    }

    private void showForum() {
        if (forumAchievements.isEmpty()) {
            JOptionPane.showMessageDialog(null, "El foro está vacío. ¡Sé el primero en compartir un logro!");
        } else {
            StringBuilder sb = new StringBuilder("Foro de Logros:\n\n");
            for (int i = forumAchievements.size() - 1; i >= 0; i--) { // Mostrar más recientes primero
                sb.append(forumAchievements.get(i)).append("\n\n");
            }
            JTextArea textArea = new JTextArea(sb.toString());
            textArea.setEditable(false);
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new java.awt.Dimension(600, 450));
            JOptionPane.showMessageDialog(null, scrollPane, "Foro de Logros", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void deletePersonalAchievement() {
        if (personalAchievements.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No tienes logros personales para eliminar.");
            return;
        }

        String[] achievementArray = personalAchievements.toArray(new String[0]);
        String achievementToDelete = (String) JOptionPane.showInputDialog(
                null,
                "Selecciona el logro que quieres eliminar:",
                "Eliminar Logro",
                JOptionPane.PLAIN_MESSAGE,
                null,
                achievementArray,
                achievementArray[0]);

        if (achievementToDelete != null) {
            int confirm = JOptionPane.showConfirmDialog(null,
                    "¿Estás seguro de que quieres eliminar este logro?",
                    "Confirmar Eliminación",
                    JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                personalAchievements.remove(achievementToDelete);
                JOptionPane.showMessageDialog(null, "¡Logro eliminado!");
                saveUserAchievements();
            }
        }
    }

    // Load properties file from disk
    private Properties loadProperties(String fileName) {
        Properties p = new Properties();
        File folder = new File(DATA_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();  // Create data folder if it doesn't exist
        }
        File file = new File(fileName);
        if (file.exists()) {
            try (InputStream in = new FileInputStream(file)) {
                p.load(in);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al cargar datos: " + e.getMessage());
            }
        }
        return p;
    }

    // Load personal achievements for the current user
    private List<String> loadUserAchievements() {
        List<String> userAchievements = new ArrayList<>();
        int i = 1;
        String key = user.getUsername() + ".achievement" + i;
        while (personalProps.containsKey(key)) {
            userAchievements.add(personalProps.getProperty(key));
            key = user.getUsername() + ".achievement" + (++i);
        }
        return userAchievements;
    }

    // Load forum achievements (shared by all users)
    private List<String> loadForumAchievements() {
        List<String> forum = new ArrayList<>();
        int i = 1;
        String key = "forum.post" + i;
        while (forumProps.containsKey(key)) {
            forum.add(forumProps.getProperty(key));
            key = "forum.post" + (++i);
        }
        return forum;
    }

    // Save current user's personal achievements
    private void saveUserAchievements() {
        // Remove old achievements for user
        int i = 1;
        String key = user.getUsername() + ".achievement" + i;
        while (personalProps.containsKey(key)) {
            personalProps.remove(key);
            key = user.getUsername() + ".achievement" + (++i);
        }
        
        // Add current achievements
        for (i = 0; i < personalAchievements.size(); i++) {
            personalProps.setProperty(user.getUsername() + ".achievement" + (i + 1), personalAchievements.get(i));
        }
        
        // Save properties to file
        savePropertiesToFile(personalProps, PERSONAL_FILE, "Personal Achievements");
    }

    // Save forum achievements
    private void saveForumAchievements() {
        // Clear existing forum posts
        int i = 1;
        String key = "forum.post" + i;
        while (forumProps.containsKey(key)) {
            forumProps.remove(key);
            key = "forum.post" + (++i);
        }
        
        // Add current forum posts
        for (i = 0; i < forumAchievements.size(); i++) {
            forumProps.setProperty("forum.post" + (i + 1), forumAchievements.get(i));
        }
        
        // Save properties to file
        savePropertiesToFile(forumProps, FORUM_FILE, "Forum Achievements");
    }

    // Helper method to save properties to file
    private void savePropertiesToFile(Properties props, String fileName, String comment) {
        File folder = new File(DATA_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        try (OutputStream out = new FileOutputStream(fileName)) {
            props.store(out, comment);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar datos: " + e.getMessage());
        }
    }
}