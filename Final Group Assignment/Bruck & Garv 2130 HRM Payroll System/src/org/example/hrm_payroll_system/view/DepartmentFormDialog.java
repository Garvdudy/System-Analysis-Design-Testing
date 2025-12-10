package org.example.hrm_payroll_system.view;

import org.example.hrm_payroll_system.models.Department;
import org.example.hrm_payroll_system.models.Role;
import org.example.hrm_payroll_system.utils.AlertUtil;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class DepartmentFormDialog {
	private Dialog<Department> departmentDialog;
    private TextField departmentIdField;
    private TextField nameField;
    private TextField managerIdField;
    private Spinner<Integer> numOfEmployeesField;

    
    public DepartmentFormDialog(Department department, Role currentUserRole) {
        boolean isEmployee = currentUserRole == Role.EMPLOYEE;

    	departmentDialog = new Dialog<>();
    	departmentDialog.setTitle(department == null ? "Add Department" : "Edit Department");
    	
    	//Buttons
    	departmentDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    	
    	Button okButton = (Button) departmentDialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(isEmployee);
        if (isEmployee) {
            okButton.setTooltip(new Tooltip("You do not have permission to edit department data"));
        }
    	okButton.addEventFilter(ActionEvent.ACTION, event -> {
    	    // Basic validation (empty fields?)
    	    if (departmentIdField.getText().isEmpty() ||
    	        nameField.getText().isEmpty() ||
    	        managerIdField.getText().isEmpty()) {

    	        AlertUtil.showError("All fields are required!");
    	        event.consume(); // prevent dialog from closing
    	        return;
    	    }
    	});

    	//Form Layout
    	GridPane grid = new GridPane();
    	grid.setHgap(10);
    	grid.setVgap(10);
    	
    	departmentIdField = new TextField();
        departmentIdField.setDisable(isEmployee);

    	nameField = new TextField();
    	nameField.setDisable(isEmployee);

    	managerIdField = new TextField();
    	managerIdField.setDisable(isEmployee);
    	
    	numOfEmployeesField = new Spinner<Integer>();
    	numOfEmployeesField.setValueFactory(
    		new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 999,1)
    		);
        numOfEmployeesField.setEditable(!isEmployee);
        numOfEmployeesField.setDisable(isEmployee);
    	
    	if (department != null) {
    		departmentIdField.setText(String.valueOf(department.getDepartmentId()));
    		nameField.setText(department.getName());
    		managerIdField.setText(String.valueOf(department.getManagerId()));
            numOfEmployeesField.getValueFactory().setValue(department.getNumOfEmployees());
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
        
        //Accepts only letters and spaces or space and rejects invalid input
        nameField.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("[a-zA-Z0-9 .,&-]*")) {
                return change;
            }
            return null;
        }));
        
        //Accepts only numeric values
        departmentIdField.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*")) {
        		return change;
        	}
        	return null;
        }));
        
        //Accepts only integer values
        managerIdField.setTextFormatter(new TextFormatter<>(change -> {
            if (change.getControlNewText().matches("\\d*"))
                return change;
            return null;
        }));
        
        departmentDialog.setResultConverter(new Callback<ButtonType, Department>() {
            @Override
            public Department call(ButtonType button) {
                if (button == ButtonType.OK) {
                    return new Department(
                        Integer.parseInt(departmentIdField.getText()),
                        nameField.getText(),
                        Integer.parseInt(managerIdField.getText()),
                        numOfEmployeesField.getValue()
                    );
                }
                return null;
            }
        });

    }
    public Dialog<Department> getDialog() {
        return departmentDialog;
    }
    
	public TextField getDepartmentIdField() {
		return departmentIdField;
	}
	public TextField getNameField() {
		return nameField;
	}
	public TextField getManagerIdField() {
		return managerIdField;
	}
	public Spinner<Integer> getNumOfEmployeesField() {
		return numOfEmployeesField;
	}
}


