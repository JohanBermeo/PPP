package controller;

import javax.swing.*;

import view.AuthView;
import view.BaseView;
import model.User;
import model.Admin;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BaseController {

    private Admin admin;
    
    // Views
    private AuthView authView = new AuthView();

    private static final String DATA_FILE = "resources\\data\\userdata.ser";

    public BaseController() {
    	admin = new Admin("admin", "admin123");
        loadData();
    }

    public void start() {
    	int option = authView.initialOptions();
    	while (option != -1 || option != 2) {	
	         switch (option) {
	             case 0: // Admin
	            	 Boolean adminIsLogin = authView.adminLoginFlow(admin);
	            	 if (adminIsLogin) {
	            		 int actionAdmin = authView.adminMenu(admin);
	            		 while (!(actionAdmin == -1 || actionAdmin == 3)) {
	            			 actionsAdmin(actionAdmin);
	            			 actionAdmin = authView.adminMenu(admin);
	            		 }
	            	 }
	            	 option = authView.initialOptions();
	                 break;
	             case 1: // User
	            	 
	            	 Boolean userIsLogin = authView.userLoginFlow(admin.getUsers());;
	            	 if (userIsLogin) {
	            		 runAplication();
	            	 }
	            	 option = authView.initialOptions();
	                 break;
	         }
    	}
    	saveData();
    	System.exit(0);
    }

    private void actionsAdmin(int action) {
    	switch (action) {
        case 0: // Create new user
        	User newUser = admin.createNewUser();
        	authView.showUserCreated(newUser);
        	saveData();
            break;

        case 1: // List users
        	authView.listUsers(admin);
            break;

        case 2: // Remove user
        	List<User> users = authView.removeUser(admin.getUsers());
        	admin.setUsers(users);
            break;

    	}
    }
    
    private void runAplication() {
        
        // Aplicación
        BaseView ventana = new BaseView();
        while (!ventana.getExit()){
        	ventana.setVisible(true);
        }

        // Toca guardar para que no se requiera volver a cambiar la contraseña
    }
    
    // Serialization: Save users, admins, and userCounter to file
    private void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            out.writeObject(admin.getUsers());
            out.writeInt(Admin.getUserCounter());  // save static counter
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving data: " + e.getMessage());
        }
    }

    // Deserialization: Load users, admins, and userCounter from file
    @SuppressWarnings("unchecked")
    private void loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) return;

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            List<User> users = (List<User>) in.readObject();
            admin.setUsers(users);
            int counter = in.readInt();
            Admin.setUserCounter(counter);
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage());
        }
    }
}
       

