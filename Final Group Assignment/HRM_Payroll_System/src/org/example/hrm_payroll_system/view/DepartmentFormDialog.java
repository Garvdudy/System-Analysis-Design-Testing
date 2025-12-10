package org.example.hrm_payroll_system.view;

import org.example.hrm_payroll_system.models.Department;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class DepartmentFormDialog {
	private Dialog<Department> departmentDialog;
    private TextField departmentIdField;
    private TextField nameField;
    private TextField managerIdField;
    private TextField numOfEmployeesField;

    
    public DepartmentFormDialog(Department department) {
    	
    	departmentDialog = new Dialog<>();
    	departmentDialog.setTitle(department == null ? "Add Department" : "Edit Department");
    	
    	//Buttons
    	departmentDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    	
    	//Form Layout
    	GridPane grid = new GridPane();
    	grid.setHgap(10);
    	grid.setVgap(10);
    	
    	departmentIdField = new TextField();
    	nameField = new TextField();
    	managerIdField = new TextField();
    	numOfEmployeesField = new TextField();
    	
    	if (department != null) {
    		departmentIdField.setText(String.valueOf(department.getDepartmentId()));
    		nameField.setText(department.getName());
    		managerIdField.setText(department.getManagerId());
    		numOfEmployeesField.setText(String.valueOf(department.getNumOfEmployees()));
    	}
    	
    	//Labels
    	grid.add(new Label("Department Id:"), 0, 0);
        grid.add(departmentIdField, 1, 0);

        grid.add(new Label("Name:"), 0, 1);
        grid.add(nameField, 1, 1);

        grid.add(new Label("Manager Id:"), 0, 2);
        grid.add(managerIdField, 1, 2);

        grid.add(new Label("Number of employees:"), 0, 3);
        grid.add(numOfEmployeesField, 1, 3);
        
        
        departmentDialog.getDialogPane().setContent(grid);
        
        departmentDialog.setResultConverter(new Callback<ButtonType, Department>() {
            @Override
            public Department call(ButtonType button) {
                if (button == ButtonType.OK) {
                    return new Department(
                        Integer.parseInt(departmentIdField.getText()),
                        nameField.getText(),  // name is a String
                        managerIdField.getText(),
                        Integer.parseInt(numOfEmployeesField.getText())
                    );
                }
                return null;
            }
        });

    }
    public Dialog<Department> getDialog() {
        return departmentDialog;
    }
}


