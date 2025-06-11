package model;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.User;

public class Admin extends User {
	private static int userCounter = 1; 
    private List<User> users = new ArrayList<>();
    private static final long serialVersionUID = 1L;
    private SecureRandom random = new SecureRandom();

    public Admin(String username, String password) {
        super(username, password);
    }

    // Creates a new user with a sequential username formatted with three digits and random password
    public User createNewUser() {
        String username = generateUsername();
        String password = generatePassword();
        User newUser = new User(username, password);
        users.add(newUser);
        return newUser;
    }

    private String generateUsername() {
        // Format number with leading zeros, width 3
        return String.format("user%03d", userCounter++);
    }

    private String generatePassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        //return sb.toString();
        return "123";
    }

    
    // Getter and setter for userCounter to persist static field
    public static int getUserCounter() {
        return userCounter;
    }

    public static void setUserCounter(int counter) {
        userCounter = counter;
    }   
    
    public List<User> getUsers(){
    	return users;
    }
    
    public void setUsers(List<User> listUsers){
    	users = listUsers;
    }
    
}
