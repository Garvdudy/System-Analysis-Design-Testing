package org.example.hrm_payroll_system.view;

import java.util.List;

import org.example.hrm_payroll_system.models.Employee;
import org.example.hrm_payroll_system.models.Payroll;
import org.example.hrm_payroll_system.utils.AlertUtil;

import javafx.collections.FXCollections;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class PayrollFormDialog {
	private Dialog<Payroll> payrollDialog;
    //private TextField empIdField;
    private ComboBox<Employee> empIdBox;
    private TextField overtimeField;
    private TextField bonusField;
    private TextField hWorkedField;
    
    public PayrollFormDialog(Payroll payroll, List<Employee> employees) {
    	
    	payrollDialog = new Dialog<>();
    	payrollDialog.setTitle(payroll == null ? "Add Payroll" : "Edit Payroll");
    	
    	//Buttons
    	payrollDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    	
    	//Form Layout
    	GridPane grid = new GridPane();
    	grid.setHgap(10);
    	grid.setVgap(10);
    	
    	empIdBox = new ComboBox<>();
        empIdBox.setItems(FXCollections.observableArrayList(employees));
        empIdBox.setCellFactory(listView -> new ListCell<>() {
            @Override
            protected void updateItem(Employee emp, boolean empty) {
                super.updateItem(emp, empty);
                if (empty || emp == null) {
                    setText("");
                } else {
                    setText(emp.getId() + " - " + emp.getFirstName() + " " + emp.getLastName());
                }
            }
        });
        empIdBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Employee emp, boolean empty) {
                super.updateItem(emp, empty);
                if (empty || emp == null) {
                    setText("");
                } else {
                    setText(emp.getId() + " - " + emp.getFirstName() + " " + emp.getLastName());
                }
            }
        });
        
    	overtimeField = new TextField();
    	bonusField = new TextField();
    	hWorkedField = new TextField();
    	//taxField = new TextField();
    	//netSalaryField = new TextField();
    	
    	if (payroll != null) {
            employees.stream()
            .filter(e -> e.getId() == payroll.getEmployeeId())
            .findFirst()
            .ifPresent(empIdBox::setValue);
            overtimeField.setText(String.valueOf(payroll.getOvertime()));
    		bonusField.setText(String.valueOf(payroll.getBonus()));
    		//taxField.setText(String.valueOf(payroll.getTax()));
    		//netSalaryField.setText(String.valueOf(String.valueOf(payroll.getNetSalary())));
    	}
    	
    	//Labels
    	grid.add(new Label("Employee:"), 0, 0);
        grid.add(empIdBox, 1, 0);

        grid.add(new Label("Overtime:"), 0, 1);
        grid.add(overtimeField, 1, 1);

        grid.add(new Label("Bonus:"), 0, 2);
        grid.add(bonusField, 1, 2);

        grid.add(new Label("Hours Worked:"), 0, 3);
        grid.add(hWorkedField, 1, 3);
        
        payrollDialog.getDialogPane().setContent(grid);
        
        payrollDialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                try {
                    Employee selected = empIdBox.getValue();
                    if (selected == null) {
                        AlertUtil.showError("Please select an employee.");
                        return null;
                    }

                    return new Payroll(
                            selected.getId(),
                            Double.parseDouble(overtimeField.getText()),
                            Double.parseDouble(bonusField.getText()),
                            Double.parseDouble(hWorkedField.getText())
                    );

                } catch (Exception e) {
                    AlertUtil.showError("Please enter valid numeric values.");
                }
            }
            return null;
        });
    }
    public Dialog<Payroll> getDialog() {
        return payrollDialog;
    }
}

