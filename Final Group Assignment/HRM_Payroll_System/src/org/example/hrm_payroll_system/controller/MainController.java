package org.example.hrm_payroll_system.controller;

import org.example.hrm_payroll_system.view.MainView;

import javafx.stage.Stage;

public class MainController {
	private MainView view;
	
	public MainController() {
		view = new MainView();
		
		//Actions for buttons
		view.getEmployeeBtn().setOnAction(e -> openEmployeeManagement());
		view.getPayrollBtn().setOnAction(e -> openPayrollManagement());
		view.getDepartmentBtn().setOnAction(e -> openDepartmentManagement());
	}
	
	public void show(Stage stage) {
		view.show(stage);
	}
	
	private void openEmployeeManagement() {
		EmployeeManagementController empController = new EmployeeManagementController();
		Stage empStage = new Stage();
		empController.show(empStage);
	}
	
	private void openPayrollManagement() {
		PayrollManagementController payrollController = new PayrollManagementController();
		Stage payStage = new Stage();
		payrollController.show(payStage);
	}
	
	private void openDepartmentManagement() {
		DepartmentManagementController deptController = new DepartmentManagementController();
		Stage deptStage = new Stage();
		deptController.show(deptStage);
	}
}
