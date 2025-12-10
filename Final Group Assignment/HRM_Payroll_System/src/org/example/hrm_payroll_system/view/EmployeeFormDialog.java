package org.example.hrm_payroll_system.view;

import org.example.hrm_payroll_system.models.Employee;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import javafx.util.Callback;


public class EmployeeFormDialog {

	private Dialog<Employee> empDialog;
    private TextField fNameField;
    private TextField lNameField;
    private TextField depField;
    private TextField bSalaryField;
    
    public EmployeeFormDialog(Employee employee) {
    	
    	empDialog = new Dialog<>();
    	empDialog.setTitle(employee == null ? "Add Employee" : "Edit Employee");
    	
    	//Buttons
    	empDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    	
    	//Form Layout
    	GridPane grid = new GridPane();
    	grid.setHgap(10);
    	grid.setVgap(10);
    	
    	fNameField = new TextField();
    	lNameField = new TextField();
    	depField = new TextField();
    	bSalaryField = new TextField();
    	
    	if (employee != null) {
    		fNameField.setText(employee.getFirstName());
    		lNameField.setText(employee.getLastName());
    		depField.setText(employee.getDepartment());
    		bSalaryField.setText(String.valueOf(employee.getBaseSalary()));
    	}
    	
    	//Labels
    	grid.add(new Label("First Name:"), 0, 0);
        grid.add(fNameField, 1, 0);

        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(lNameField, 1, 1);

        grid.add(new Label("Department:"), 0, 2);
        grid.add(depField, 1, 2);

        grid.add(new Label("Base Salary:"), 0, 3);
        grid.add(bSalaryField, 1, 3);
        
        empDialog.getDialogPane().setContent(grid);
        
        empDialog.setResultConverter(new Callback<ButtonType, Employee>() {
        	@Override
            public Employee call(ButtonType button) {
                if (button == ButtonType.OK) {
                	int id = employee != null ? employee.getId() : -1;
                	return new Employee(
                			id,
                			fNameField.getText(),
                			lNameField.getText(),
                			depField.getText(),
                            Double.parseDouble(bSalaryField.getText())
                	);
                }
                return null;
                
        	}
        });
    }
    public Dialog<Employee> getDialog() {
        return empDialog;
    }
}
