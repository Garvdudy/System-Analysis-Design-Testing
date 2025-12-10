package org.example.hrm_payroll_system;

import java.util.List;

import org.example.hrm_payroll_system.controller.DepartmentManagementController;
import org.example.hrm_payroll_system.controller.EmployeeManagementController;
import org.example.hrm_payroll_system.controller.MainController;
import org.example.hrm_payroll_system.controller.PayrollManagementController;
import org.example.hrm_payroll_system.models.Employee;
import org.example.hrm_payroll_system.persistence.DataManager;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainApp extends Application {
	
    @Override
    public void start(Stage stage) {
    	MainController mainController = new  MainController();
    	mainController.show(stage);

    }

    public static void main(String[] args) {
        launch(args);
    }
}