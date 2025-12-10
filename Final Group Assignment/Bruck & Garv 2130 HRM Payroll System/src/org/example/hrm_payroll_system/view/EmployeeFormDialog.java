package org.example.hrm_payroll_system.view;

import org.example.hrm_payroll_system.models.Employee;
import org.example.hrm_payroll_system.models.Role;
import org.example.hrm_payroll_system.utils.AlertUtil;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;


public class EmployeeFormDialog {

	private Dialog<Employee> empDialog;
    private TextField fNameField;
    private TextField lNameField;
    private ComboBox<String> depBox;
    private TextField bSalaryField;
    
    public EmployeeFormDialog(Employee employee, java.util.List<String> departments, Role currentUserRole) {
        boolean isEmployee = currentUserRole == Role.EMPLOYEE;

    	empDialog = new Dialog<>();
    	empDialog.setTitle(employee == null ? "Add Employee" : "Edit Employee");
    	
    	//Buttons
    	empDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    	
    	Button okButton = (Button) empDialog.getDialogPane().lookupButton(ButtonType.OK);
        okButton.setDisable(isEmployee);
        if (isEmployee) {
            okButton.setTooltip(new Tooltip("You do not have permission to edit employee data"));
        }

    	okButton.addEventFilter(ActionEvent.ACTION, event -> {
    	    
            if (fNameField.getText().trim().isEmpty()) {
                AlertUtil.showError("First Name cannot be empty.");
                event.consume();
                return;
            }

            if (lNameField.getText().trim().isEmpty()) {
                AlertUtil.showError("Last Name cannot be empty.");
                event.consume();
                return;
            }

            if (depBox.getValue() == null || depBox.getValue().trim().isEmpty()) {
                AlertUtil.showError("Please select a department.");
                event.consume();
                return;
            }

            if (bSalaryField.getText().trim().isEmpty()) {
                AlertUtil.showError("Base salary is required.");
                event.consume();
                return;
            }

            try {
                Double.parseDouble(bSalaryField.getText());
            } catch (NumberFormatException ex) {
                AlertUtil.showError("Base salary must be a valid number.");
                event.consume();
            }
        });
    	
    	//Form Layout
    	GridPane grid = new GridPane();
    	grid.setHgap(10);
    	grid.setVgap(10);
    	
    	fNameField = new TextField();
        fNameField.setPromptText("First name");
        fNameField.setDisable(isEmployee);
        
    	lNameField = new TextField();
        lNameField.setPromptText("Last name");
        lNameField.setDisable(isEmployee);

    	depBox = new ComboBox();
    	depBox.getItems().addAll(departments);
    	depBox.setPromptText("Select department");
        depBox.setEditable(!isEmployee);
        depBox.setDisable(isEmployee);
        
    	bSalaryField = new TextField();
    	bSalaryField.setPromptText("Example: 4500.50");
        bSalaryField.setDisable(isEmployee);
    	
    	if (employee != null) {
    		fNameField.setText(employee.getFirstName());
    		lNameField.setText(employee.getLastName());
    		depBox.setValue(employee.getDepartment());
    		bSalaryField.setText(String.valueOf(employee.getBaseSalary()));
    		
    	}
    	
    	//Labels
    	grid.add(new Label("First Name:"), 0, 0);
        grid.add(fNameField, 1, 0);

        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(lNameField, 1, 1);

        grid.add(new Label("Department:"), 0, 2);
        grid.add(depBox, 1, 2);

        grid.add(new Label("Base Salary:"), 0, 3);
        grid.add(bSalaryField, 1, 3);
        
        empDialog.getDialogPane().setContent(grid);
        
        //Accepts only letters and spaces or space and rejects invalid input
        fNameField.setTextFormatter(new TextFormatter<>(change -> {
        	if (change.getText().matches("[a-zA-Z ]*")) {
        		return change;
        	}
        	return null;
        }));
        
        lNameField.setTextFormatter(new TextFormatter<>(change -> {
        	if (change.getText().matches("[a-zA-Z ]*")) {
        		return change;
        	}
        	return null;
        }));
        
        //Accepts only numeric values
        bSalaryField.setTextFormatter(new TextFormatter<>(change -> {
        	if (change.getControlNewText().matches("\\d*(\\.\\d*)?")) {
        		return change;
        	}
        	return null;
        }));
        
        
        empDialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                return new Employee(
                        employee != null ? employee.getId() : -1,
                        fNameField.getText(),
                        lNameField.getText(),
                        depBox.getValue(),
                        Double.parseDouble(bSalaryField.getText())
                );
            }
            return null;
        });
    } 
        
        /*empDialog.setResultConverter(new Callback<ButtonType, Employee>() {
        	@Override
            public Employee call(ButtonType button) {
                if (button == ButtonType.OK) {
                	
                	//Validation
                	if (fNameField.getText().isEmpty() || 
                		lNameField.getText().isEmpty() ||
                		depBox.getValue() == null ||
                		bSalaryField.getText().isEmpty()) {
                		
                		AlertUtil.showError("All fields are necessary");
                		return null;
                	}
                	
                	int id = employee != null ? employee.getId() : -1;
                	return new Employee(
                			id,
                			fNameField.getText(),
                			lNameField.getText(),
                			depBox.getValue(),
                            Double.parseDouble(bSalaryField.getText())
                	);
                }
                return null;
                
        	}
        });
    }*/
    
    public Dialog<Employee> getDialog() {
        return empDialog;
    }
    
}
