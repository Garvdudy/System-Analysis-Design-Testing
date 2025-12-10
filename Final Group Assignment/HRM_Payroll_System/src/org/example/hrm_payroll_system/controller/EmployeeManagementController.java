package org.example.hrm_payroll_system.controller;

import java.util.List;

import org.example.hrm_payroll_system.models.Employee;
import org.example.hrm_payroll_system.persistence.DataManager;
import org.example.hrm_payroll_system.view.EmployeeFormDialog;
import org.example.hrm_payroll_system.view.EmployeeManagementView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EmployeeManagementController {
    private EmployeeManagementView view;
    private ObservableList<Employee> employeeList;
	
    public EmployeeManagementController() {
        view = new EmployeeManagementView();
        List<Employee> employees = DataManager.loadEmployees();
        employeeList = FXCollections.observableArrayList(employees);

        view.getAddBtn().setOnAction(e -> addEmployee());
        view.getEditBtn().setOnAction(e -> editEmployee());
        view.getDeleteBtn().setOnAction(e -> deleteEmployee());
    }
	
    public void show(Stage stage) {
    	view.show(stage, employeeList);
    }

	private void addEmployee() {
		EmployeeFormDialog empForm = new EmployeeFormDialog(null);
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
	        EmployeeFormDialog empForm = new EmployeeFormDialog(selected);
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
		Employee selected = view.getTableView().getSelectionModel().getSelectedItem();
		if (selected != null) {
			employeeList.remove(selected);
			DataManager.saveEmployees(employeeList);
		}
	}
}
