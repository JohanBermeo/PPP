package view;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.Admin;
import model.User;

public class AuthView {
	
	public int initialOptions() {
        
        String[] options = {"Admin", "User", "Exit"};
        int choice = JOptionPane.showOptionDialog(null,
                "Select user type:",
                "Main Menu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);
        
        return choice;
	}
	
	// Admin´s view
	public Boolean adminLoginFlow(Admin admin) {
		String[] input = inputUsernameAndPassword(); 
		if (input.length == 0) {
        	JOptionPane.showMessageDialog(null, "Canceled.");
        	return false;
		}
		String adminName = input[0];
		String password = input[1];
		
		if (!adminName.equals(admin.getUsername()) || !admin.authenticate(password)) {
			JOptionPane.showMessageDialog(null,"Password or admin incorrect!");
			return false;
		}
          
        JOptionPane.showMessageDialog(null, "Admin authenticated!");

        // Enforce password reset on first login or if flagged
        while (admin.isPasswordResetPending()) {
            boolean changed = promptPasswordChange(admin);
            if (!changed) {
                JOptionPane.showMessageDialog(null, "You must change your password to continue.");
                return false;  // Stop login flow until password changed
            }
            admin.setPasswordResetPending(false);
        }
        
        return true;
	}
	
    public int adminMenu(Admin admin) {
        String[] options = {"Create new user", "List users", "Remove user", "Logout"};
        int choice = JOptionPane.showOptionDialog(null,
                "Admin Menu:",
                "Admin: " + admin.getUsername(),
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);

        return choice; 
    }
    
    public void showUserCreated(User newUser) {
        JOptionPane.showMessageDialog(null, "New user created!\nUsername: " + newUser.getUsername() + "\nPassword: " + newUser.getPassword());
    }
    
    public void listUsers(Admin admin) {
    	if (admin.getUsers().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No users registered.");
        } else {
            StringBuilder sb = new StringBuilder("Users:\n");
            for (User u : admin.getUsers()) {
                sb.append(u.getUsername()).append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    }
    
    public List<User> removeUser(List<User> users) {
        if (users.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No users to remove.");
            return users;
        }

        String[] usernames = users.stream()
                .map(User::getUsername)
                .toArray(String[]::new);

        String userToRemove = (String) JOptionPane.showInputDialog(
                null,
                "Select user to remove:",
                "Remove User",
                JOptionPane.PLAIN_MESSAGE,
                null,
                usernames,
                usernames[0]);

        if (userToRemove == null) return users;

        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to remove user '" + userToRemove + "'?",
                "Confirm Remove",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            users.removeIf(u -> u.getUsername().equals(userToRemove));
            JOptionPane.showMessageDialog(null, "User '" + userToRemove + "' removed.");
        }
        
		return users;
    }
    
    // User´s views
    public Boolean userLoginFlow(List<User> users) {
    	String[] input = inputUsernameAndPassword();
    	if (input.length == 0) {
        	JOptionPane.showMessageDialog(null, "Canceled.");
        	return false;
		}
		String username = input[0];
		String password = input[1];
		
		User user = null;
		for (User u : users) {
            if (u.getUsername().equals(username)) {
            	user = u;
            }
        }
		
        if (user == null || !user.authenticate(password)) return false;    
        JOptionPane.showMessageDialog(null, "User authenticated!");

        // Enforce password reset on first login or if flagged
        if (user.isPasswordResetPending()) {
            boolean changed = promptPasswordChange(user);
            if (!changed) {
                JOptionPane.showMessageDialog(null, "You must change your password to continue.");
                return false;  // Stop login flow until password changed
            }
            user.setPasswordResetPending(false);
        }
        return true;
    }
	
	
	// General views
    private String[] inputUsernameAndPassword() {
        while (true) {
            JPanel panel = new JPanel(new GridLayout(2, 2));
            JLabel userLabel = new JLabel("Username:");
            JTextField userField = new JTextField();
            JLabel passLabel = new JLabel("Password:");
            JPasswordField passField = new JPasswordField();
            panel.add(userLabel);
            panel.add(userField);
            panel.add(passLabel);
            panel.add(passField);

            int result = JOptionPane.showConfirmDialog(
                    null, panel, "Login",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result != JOptionPane.OK_OPTION) return new String[] {}; 
          
            String username = new String(userField.getText().trim());
            String password = new String(passField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Username and password cannot be empty.");
                continue;
            }
            
            return new String[] {username, password};
            
        }
    }
    
    public boolean promptPasswordChange(User user) {
        while (true) {
            JPanel panel = new JPanel(new GridLayout(2, 2));
            panel.add(new JLabel("New Password:"));
            JPasswordField newPass = new JPasswordField();
            panel.add(newPass);
            panel.add(new JLabel("Confirm Password:"));
            JPasswordField confirmPass = new JPasswordField();
            panel.add(confirmPass);

            int result = JOptionPane.showConfirmDialog(null, panel, "Change Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result != JOptionPane.OK_OPTION) return false; // Cancelled

            String newPassword = new String(newPass.getPassword());
            String confirmPassword = new String(confirmPass.getPassword());

            if (newPassword.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Password cannot be empty.");
                continue;
            }
            if (!newPassword.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(null, "Passwords do not match. Try again.");
                continue;
            }

            user.setPassword(newPassword);
            user.setPasswordResetPending(false);  // Clear the flag
            // Save user data here if needed
            JOptionPane.showMessageDialog(null, "Password changed successfully!");
            return true;
        }
    }
}
