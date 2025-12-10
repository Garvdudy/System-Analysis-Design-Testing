package org.example.hrm_payroll_system;

import org.example.hrm_payroll_system.controller.LoginController;
import org.example.hrm_payroll_system.controller.MainController;
import org.example.hrm_payroll_system.persistence.DataManager;
import org.example.hrm_payroll_system.view.LoginView;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
	
    @Override
    public void start(Stage stage) {
    	DataManager.initializeUsers();
        LoginController loginController = new LoginController();
        LoginView loginView = new LoginView(loginController);
        loginView.show(stage);
        
        System.out.println("Users loaded: " + DataManager.loadUsers());


    }

    public static void main(String[] args) {
        launch(args);
    }
}