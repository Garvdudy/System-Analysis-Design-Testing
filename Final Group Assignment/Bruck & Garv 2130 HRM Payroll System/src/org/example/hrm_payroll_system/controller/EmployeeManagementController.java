package org.example.hrm_payroll_system.controller;

import java.util.List;

import org.example.hrm_payroll_system.models.Department;
import org.example.hrm_payroll_system.models.Employee;
import org.example.hrm_payroll_system.models.Role;
import org.example.hrm_payroll_system.persistence.DataManager;
import org.example.hrm_payroll_system.utils.AppSession;
import org.example.hrm_payroll_system.view.EmployeeFormDialog;
import org.example.hrm_payroll_system.view.EmployeeManagementView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class EmployeeManagementController {
    private EmployeeManagementView view;
    private MainController mainController;
    private ObservableList<Employee> employeeList;
    private boolean isEmployeeRole;
	
    public EmployeeManagementController(MainController mainController) {
        this.mainController = mainController;
    	view = new EmployeeManagementView();
    	
        isEmployeeRole = AppSession.getInstance().getCurrentUser().getRole() == Role.EMPLOYEE;
        
        List<Employee> employees = DataManager.loadEmployees();
        employeeList = FXCollections.observableArrayList(employees);

        view.getAddBtn().setOnAction(e -> addEmployee());
        view.getEditBtn().setOnAction(e -> editEmployee());
        view.getDeleteBtn().setOnAction(e -> deleteEmployee());
        view.getBackBtn().setOnAction(e -> mainController.showMainView());
        
        // Disable buttons if role is Employee
        if (isEmployeeRole) {
            view.getAddBtn().setDisable(true);
            view.getEditBtn().setDisable(true);
            view.getDeleteBtn().setDisable(true);

            view.getAddBtn().setTooltip(new javafx.scene.control.Tooltip("You do not have permission to add employees"));
            view.getEditBtn().setTooltip(new javafx.scene.control.Tooltip("You do not have permission to edit employees"));
            view.getDeleteBtn().setTooltip(new javafx.scene.control.Tooltip("You do not have permission to delete employees"));
        }
    }
    public void show(Stage stage) {
    	view.show(stage, employeeList);
    }

	private void addEmployee() {
        if (isEmployeeRole) return;
        
		List<Department> depObjects = DataManager.loadDepartments();
		List<String> departments = depObjects.stream().map(Department::getName).toList();
		
		EmployeeFormDialog empForm = new EmployeeFormDialog(null, departments, isEmployeeRole ? Role.EMPLOYEE : Role.ADMIN);
		empForm.getDialog().showAndWait().ifPresent(emp -> {
			//Auto-generates ID
			int newId = employeeList.stream().mapToInt(Employee::getId).max().orElse(0) + 1;
	        emp.setId(newId);
	        
	        employeeList.add(emp);
	        DataManager.saveEmployees(employeeList);
		});
	}
	
	private void editEmployee() {
		Employee selected = view.getTableView().getSelectionModel().getSelectedItem();
		if (selected != null) {
			List<Department> depObjects = DataManager.loadDepartments();
			List<String> departments = depObjects.stream().map(d -> d.getName()).toList();
			
	        EmployeeFormDialog empForm = new EmployeeFormDialog(selected, departments, isEmployeeRole ? Role.EMPLOYEE : Role.ADMIN);
	        empForm.getDialog().showAndWait().ifPresent(emp -> {
	            selected.setFirstName(emp.getFirstName());
	            selected.setLastName(emp.getLastName());
	            selected.setDepartment(emp.getDepartment());
	            selected.setBaseSalary(emp.getBaseSalary());
	            
	            view.getTableView().refresh();
	            DataManager.saveEmployees(employeeList);
	        });
		}
	}
	
	private void deleteEmployee() {
		if (isEmployeeRole) return;
		
		Employee selected = view.getTableView().getSelectionModel().getSelectedItem();
		if (selected != null) {
			employeeList.remove(selected);
			DataManager.saveEmployees(employeeList);
		}
	}
}
