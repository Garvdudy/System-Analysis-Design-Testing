package org.example.hrm_payroll_system.controller;

import java.util.List;

import org.example.hrm_payroll_system.models.Department;
import org.example.hrm_payroll_system.models.Role;
import org.example.hrm_payroll_system.persistence.DataManager;
import org.example.hrm_payroll_system.view.DepartmentFormDialog;
import org.example.hrm_payroll_system.view.DepartmentManagementView;
import org.example.hrm_payroll_system.utils.AlertUtil;
import org.example.hrm_payroll_system.utils.AppSession;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class DepartmentManagementController {
	private DepartmentManagementView view;
	private MainController mainController;
    private ObservableList<Department> departmentList;
    private boolean isEmployeeRole;
    
    public DepartmentManagementController(MainController mainController) {
        view = new DepartmentManagementView();
        this.mainController = mainController;
        
        isEmployeeRole = AppSession.getInstance().getCurrentUser().getRole() == Role.EMPLOYEE;
        
        List<Department> departments = DataManager.loadDepartments();
        departmentList = FXCollections.observableArrayList(departments);

        view.getBackBtn().setOnAction(e -> mainController.showMainView());
        view.getAddBtn().setOnAction(e -> addDepartment());
        view.getEditBtn().setOnAction(e -> editDepartment());
        view.getDeleteBtn().setOnAction(e -> deleteDepartment());
        
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
    
    private String isUnique(int departmentId, int managerId, String name, Department current) {
        for (Department d : departmentList) {

            // Skip current record when editing
            if (current != null && d == current) {
            	continue;
            }

            // Check duplicate department ID
            if (d.getDepartmentId() == departmentId) {
                return "Department ID already exists!";
            }

            // Check duplicate manager ID
            if (d.getManagerId() == managerId) {
                return "Manager ID already exists!";
            }

            // Check duplicate department name
            if (d.getName().equalsIgnoreCase(name)) {
                return "Department already exists!";
            }
        }
        return null;
    }

    public void show(Stage stage) {
    	view.show(stage, departmentList);
    }

	private void addDepartment() {
		if (isEmployeeRole) return;
		
		DepartmentFormDialog depForm = new DepartmentFormDialog(null,isEmployeeRole ? Role.EMPLOYEE : Role.ADMIN);
		
		Button okButton = (Button) depForm.getDialog().getDialogPane().lookupButton(ButtonType.OK);
		
		okButton.addEventFilter(ActionEvent.ACTION, event -> {
			try {
				int id = Integer.parseInt(depForm.getDepartmentIdField().getText());
				String name = depForm.getNameField().getText();
				int manager = Integer.parseInt(depForm.getManagerIdField().getText());

	            String dup = isUnique(id, manager, name, null);

	            if (dup != null) {
	                AlertUtil.showError(dup);
	                event.consume();
	            }
	        } catch (Exception ex) {
	            AlertUtil.showError("Invalid input!");
	            event.consume();
	        }
	    });
		
		depForm.getDialog().showAndWait().ifPresent(dep -> {
			departmentList.add(dep);
	        DataManager.saveDepartments(departmentList);
		});
	}
	
	private void editDepartment() {
		Department selected = view.getTableView().getSelectionModel().getSelectedItem();
		if (selected != null) {
			DepartmentFormDialog depForm = new DepartmentFormDialog(selected, isEmployeeRole ? Role.EMPLOYEE : Role.ADMIN);
			Button okButton = (Button) depForm.getDialog().getDialogPane().lookupButton(ButtonType.OK);
			
			okButton.addEventFilter(ActionEvent.ACTION, event -> {
				try {
					int id = Integer.parseInt(depForm.getDepartmentIdField().getText());
					String name = depForm.getNameField().getText();
					int manager = Integer.parseInt(depForm.getManagerIdField().getText());

		            String dup = isUnique(id, manager, name, selected);

		            if (dup != null) {
		                AlertUtil.showError(dup);
		                event.consume();
		            }
		        } catch (Exception ex) {
		            AlertUtil.showError("Invalid input!");
		            event.consume();
		        }
		    });
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
		if (isEmployeeRole) return;
		
		Department selected = view.getTableView().getSelectionModel().getSelectedItem();
		if (selected != null) {
			departmentList.remove(selected);
			DataManager.saveDepartments(departmentList);
		}
	}
    

}
