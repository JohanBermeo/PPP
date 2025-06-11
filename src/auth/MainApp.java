package auth;

import achievements.AchievementsApp;

public class MainApp {
	
    public static void main(String[] args) {
    	//testAchievementsApp();
        Controller controller = new Controller();
        controller.start();
    }
    
    public static void testAchievementsApp() {
        // Create a fictitious user
        User testUser = new User("testuser", "testpass");

        // Launch the simple ToDoApp for this user
        AchievementsApp todoApp = new AchievementsApp(testUser);
        todoApp.start();    	
    }
      
}
