package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class AppCofig {
    public static final String APP_NAME = "App Logros Personales";
    public static final String APP_VERSION = "1.0.0";
    public static final String APP_AUTHOR = "Juan Otálora, Johan Bermeo";

    public static final String DATA_DIRECTORY = "data";
    public static final String USER_DATA_FILE = DATA_DIRECTORY + File.separator + "userdata.ser";
    public static final String ACHIEVEMENTS_DATA_FILE = DATA_DIRECTORY + File.separator + "achievements.ser";
    public static final String CONFIG_FILE = DATA_DIRECTORY + File.separator + "app.properties";

    public static final String DEFAULT_ADMIN_USERNAME = "admin";
    public static final String DEFAULT_ADMIN_PASSWORD = "admin123";
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MAX_PASSWORD_LENGTH = 20;
    public static final in GENERATED_PASSWORD_LENGTH = 8;

    public static final String USER_PREFIX = "user";
    public static final String ADMIN_PREFIX = "admin";
    public static final int USERNAME_NUMBER_PADDING = 3;

    public static final int MAIN_WINDOW_WIDTH = 800;
    public static final int MAIN_WINDOW_HEIGHT = 600;
    public static final int DIALOG_WIDTH = 400;
    public static final int DIALOG_HEIGHT = 300;

    public static final int[] PRIMARY_BACKGORUND_COLOR = { 30, 30, 60};
    public static final int[] SECONDARY_BACKGROUND_COLOR = { 50, 50, 80};
    public static final int[] BUTTON_COLOR = { 70, 130, 180};
    public static final int[] BUTTON_HOVER_COLOR = { 100, 160, 210};
    public static final int[] TEXT_COLOR = { 200, 200, 255};

    public static final String PASSWORD_CHARS = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZabcdefghijklmnñopqrstuvwxyz0123456789";
    public static final String PASSWORD_SPECIAL_CHARS = "!@#$%^&*()_+-=[]{}|;:,.<>?";

    public static final String PASSWORD_REGEX_LOW = "^.{" + MIN_PASWWORD_LENGTH + "," + MAX_PASSWORD_LENGTH + "}$";
    public static final String PASSWORD_REGEX_MEDIUM = "^(?=.*[a-zA-Z])(?=.*\\d).{" + MIN_PASSWORD_LENGTH + "," + MAX_PASSWORD_LENGTH + "}$";
    public static final String PASSWORD_REGEX_HIGH = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[" + PASSWORD_SPECIAL_CHARS + "]).{" + MIN_PASSWORD_LENGTH + "," + MAX_PASSWORD_LENGTH + "}$";

    public static final String MSG_LOGIN_SUCCESS_ADMIN = "Admin authenticated!";
    public static final String MSG_LOGIN_SUCCESS_USER = "User authenticated!";
    public static final String MSG_PASSWORD_CHANGE_REQUIRED = "You must change your password to continue.";
    public static final String MSG_PASSWORD_CHANGED = "Password changed successfully!";
    public static final String MSG_INVALID_CREDENTIALS = "Invalid username or password.";
    public static final String MSD_EMPTY_FIELDS = "Please fill in all fields.";
    public static final String MSG_PASSWORD_TOO_SHORT = "Password must be at least " + MIN_PASSWORD_LENGTH + " characters long.";
    public static final String MSG_PASSWORD_DONT_MATCH = "Passwords do not match. Try again.";

    public static final int MAX_ACHIEVEMENT_NAME_LENGTH = 100;
    public static final int MAX_ACHIEVEMENT_DESCRIPTION_LENGTH = 500;
    public static final String MSG_ACHIEVEMENT_CREATED = "Achievement created successfully!";
    public static final String MSG_NO_ACHIEVEMENTS = "No achievements found.";
    public static final String MSG_COMPLETE_ALL_FIELDS = "Please complete all fields.";

    private static Properties configProperties = new Properties();
    private static boolean configLoaded = false;

    private static java.util.regex.Pattern passwordPatternLow;
    private static java.util.regex.Pattern passwordPatternMedium;
    private static java.util.regex.Pattern passwordPatternHigh;

    private static final Properties DEFAULT_PROPERTIES = new Properties();
    static {
        DEFAULT_PROPERTIES.setProperty("app.theme", "dark");
        DEFAULT_PROPERTIES.setProperty("app.language", "es");
        DEFAULT_PROPERTIES.setProperty("app.autosave", "true");
        DEFAULT_PROPERTIES.setProperty("app.backup.enabled", "true");
        DEFAULT_PROPERTIES.setProperty("app.backup.interval", "24");
        DEFAULT_PROPERTIES.setProperty("app.max.users", "100");
        DEFAULT_PROPERTIES.setProperty("app.session.timeout", "30");
        DEFAULT_PROPERTIES.setProperty("app.passwrod.complexity", "medium");

        createDataDirectory();
    
        compilePasswordPatterns();
    
        loadConfiguration();
    }

    private static void createDataDirectory() {
        File dataDir = new File(DATA_DIRECTORY);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
    }

    private static void loadConfiguration() {
        File configFile = new File(CONFIG_FILE);
        if (configFile.exists()) {
            try (FileInputStream fis = new FileInputStream(configFile)) {
                configProperties.load(fis);
                configLoaded = true;
            } catch (IOException e) {
                System.err.println("Error loading configuration: " + e.getMessage());
                configProperties = new Properties(DEFAULT_PROPERTIES);
            }
        } else {
            configProperties = new Properties(DEFAULT_PROPERTIES);
            saveConfiguration();
        }
    }

    public static void saveConfiguration() {
        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
            configProperties.store(fos, "App Logros Personales - Configuration File");
        } catch (IOException e) {
            System.err.println("Error saving configuration: " + e.getMessage());
        }
    }

    public static String getProperty(String key, String defaultValue) {
        return configProperties.getProperty(key);
    }

    public static int getIntProperty(String key, int defaultValue) {
        try {
            return Integer.parseInt(configProperties.getProperty(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static boolean getBooleanProperty(String key, boolean defaultValue) {
        return Boolean.parseBoolean(configProperties.getProperty(key, String.valueOf(defaultValue)));
    }

    public static boolean isConfigurationLoaded() {
        return configLoaded;
    }

    public static void resetToDefaults() {
        configProperties = new Properties(DEFAULT_PROPERTIES);
        saveConfiguration();
    }

    public static String getAppInfo() {
        return String.format("%s v%s by %s", APP_NAME, APP_VERSION, APP_AUTHOR);
    }

    public static boolean isValidPassword(String password) {
        if (password == null){
            return false;
        } 

        try {
            switch (complexity.toLowerCase()){
                case "low":
                    return passwordPatternLow != null && passwordPatternLow.matcher(password).matches();
                case "medium":
                    return passwordPatternMedium != null && passwordPatternMedium.matcher(password).matches();
                case "high":
                    return passworPatternHigh != null && passwordPatternHigh.matcher(password).matches();
                case default:
                    return passwordPatternMedium != null && passwordPatternMedium.matcher(password).matches();
                }
        } catch (Exception e) {
            return isValidPasswordFallback(password, complexity);
        }
    }

    private static boolean isValidPasswordFallback(String password, String complexity) {
        if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
            return false;
        }

        switch (complexity.toLowerCase()) {
            case "low":
                return true;
            case "medium":
                return password.matches(".*[a-zA-Z].*") && password.matches(".*\\d.*");
            case "high":
                return password.matches(".*[a-z].*") && 
                       password.matches(".*[A-Z].*") && 
                       password.matches(".*\\d.*") && 
                       password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{}|;:,.<>?].*");
            default:
                return password.matches(".*[a-zA-Z].*") && password.matches(".*\\d.*");
        }
    }

    public static String getCurrentPasswordRegex() {
        String complexity = getProperty("app.password.complexity", "medium");
        
        switch (complexity.toLowerCase()) {
            case "low":
                return PASSWORD_REGEX_LOW;
            case "medium":
                return PASSWORD_REGEX_MEDIUM;
            case "high":
                return PASSWORD_REGEX_HIGH;
            default:
                return PASSWORD_REGEX_MEDIUM;
        }
    }
 
    public static boolean validatePasswordWithCustomRegex(String password, String customRegex) {
        if (password == null || customRegex == null) {
            return false;
        }
        
        try {
            return password.matches(customRegex);
        } catch (java.util.regex.PatternSyntaxException e) {
            System.err.println("Invalid regex pattern: " + e.getMessage());
            return false;
        }
    }

    public static String getPasswordRequirements() {
        String complexity = getProperty("app.password.complexity", "medium");
        String baseReq = "Password must be between " + MIN_PASSWORD_LENGTH + 
                        " and " + MAX_PASSWORD_LENGTH + " characters";
        
        switch (complexity.toLowerCase()) {
            case "low":
                return baseReq + ".";
            case "medium":
                return baseReq + " and contain at least one letter and one number.";
            case "high":
                return baseReq + ", contain at least one uppercase letter, " +
                       "one lowercase letter, one number, and one special character.";
            default:
                return baseReq + " and contain at least one letter and one number.";
        }
    }
}