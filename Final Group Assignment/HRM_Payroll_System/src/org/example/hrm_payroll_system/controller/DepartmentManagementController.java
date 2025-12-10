package org.example.hrm_payroll_system.controller;

import java.util.List;

import org.example.hrm_payroll_system.models.Department;
import org.example.hrm_payroll_system.models.Employee;
import org.example.hrm_payroll_system.persistence.DataManager;
import org.example.hrm_payroll_system.view.DepartmentFormDialog;
import org.example.hrm_payroll_system.view.DepartmentManagementView;
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

public class DepartmentManagementController {
	private DepartmentManagementView view;
    private ObservableList<Department> departmentList;
	
    public DepartmentManagementController() {
        view = new DepartmentManagementView();
        List<Department> departments = DataManager.loadDepartments();
        departmentList = FXCollections.observableArrayList(departments);

        view.getAddBtn().setOnAction(e -> addDepartment());
        view.getEditBtn().setOnAction(e -> editDepartment());
        view.getDeleteBtn().setOnAction(e -> deleteDepartment());
    }
	
    public void show(Stage stage) {
    	view.show(stage, departmentList);
    }

	private void addDepartment() {
		DepartmentFormDialog depForm = new DepartmentFormDialog(null);
		depForm.getDialog().showAndWait().ifPresent(dep -> {
			
			departmentList.add(dep);
	        DataManager.saveDepartments(departmentList);
		});
	}
	
	private void editDepartment() {
		Department selected = view.getTableView().getSelectionModel().getSelectedItem();
		if (selected != null) {
			DepartmentFormDialog depForm = new DepartmentFormDialog(selected);
	        depForm.getDialog().showAndWait().ifPresent(dep -> {
	            selected.setDepartmentId(dep.getDepartmentId());
	            selected.setName(dep.getName());
	            selected.setManagerId(dep.getManagerId());
	            selected.setNumOfEmployees(dep.getNumOfEmployees());
	            view.getTableView().refresh();
	            DataManager.saveDepartments(departmentList);
	        });
		}
	}
	
	private void deleteDepartment() {
		Department selected = view.getTableView().getSelectionModel().getSelectedItem();
		if (selected != null) {
			departmentList.remove(selected);
			DataManager.saveDepartments(departmentList);
		}
	}
}
