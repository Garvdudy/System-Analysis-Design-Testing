package org.example.hrm_payroll_system.view;

import org.example.hrm_payroll_system.controller.LoginController;
import org.example.hrm_payroll_system.controller.MainController;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginView {
private LoginController controller;
	
	public LoginView(LoginController controller) {
		this.controller = controller;
	}
	
	public void show(Stage stage) {
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        Label userLabel = new Label("Username:");
        TextField userField = new TextField();
        
        Label pwdLabel = new Label("Password:");
        PasswordField pwdField = new PasswordField();
        
        Button loginButton = new Button("Login");
        
        Label msgLabel = new Label();
        
        grid.add(userLabel, 0, 0);
        grid.add(userField, 1, 0);
        grid.add(pwdLabel, 0, 1);
        grid.add(pwdField, 1, 1);
        grid.add(loginButton, 1, 2);
        grid.add(msgLabel, 1, 3);
        
        loginButton.setOnAction(e -> {
        	boolean success = controller.authenticate(userField.getText(), pwdField.getText());
        	
        	if(success) {
        		msgLabel.setText("You have been logged in successfully!");
        		MainController mainController = new  MainController(stage);
            	mainController.showMainView();
        	} else {
        		msgLabel.setText("Invalid username or password!");
        	}
        }); 
    	
        Scene scene = new Scene(grid, 350, 200);
        stage.setScene(scene);
        stage.setTitle("HRM Payroll System - Login");
        stage.show();
	}
}

