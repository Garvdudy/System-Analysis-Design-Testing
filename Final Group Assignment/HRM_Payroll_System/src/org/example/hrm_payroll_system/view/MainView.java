package org.example.hrm_payroll_system.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView {
	private Button employeeBtn;
	private Button payrollBtn;
	private Button departmentBtn;
	
	public MainView() {
		employeeBtn = new Button("Employee Management");
		payrollBtn = new Button("Payroll Management");
		departmentBtn = new Button("Department Management");
	}
	
	public void show(Stage stage) {
		VBox root = new VBox(15, employeeBtn, payrollBtn, departmentBtn);
		root.setStyle("-fx-padding: 20; -fx-alignment: center");
		
		Scene scene = new Scene(root, 400, 300);
		stage.setScene(scene);
	    stage.setTitle("HRM & Payroll System");
	    stage.show();
	}

	public Button getEmployeeBtn() {
		return employeeBtn;
	}

	public Button getPayrollBtn() {
		return payrollBtn;
	}

	public Button getDepartmentBtn() {
		return departmentBtn;
	}
	
	//Getters
	
}
