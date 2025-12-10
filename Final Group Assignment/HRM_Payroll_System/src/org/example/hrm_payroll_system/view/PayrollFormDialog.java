package org.example.hrm_payroll_system.view;

import org.example.hrm_payroll_system.models.Payroll;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class PayrollFormDialog {
	private Dialog<Payroll> payrollDialog;
    private TextField empIdField;
    private TextField overtimeField;
    private TextField bonusField;
    private TextField taxField;
    private TextField netSalaryField;

    
    public PayrollFormDialog(Payroll payroll) {
    	
    	payrollDialog = new Dialog<>();
    	payrollDialog.setTitle(payroll == null ? "Add Payroll" : "Edit Payroll");
    	
    	//Buttons
    	payrollDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
    	
    	//Form Layout
    	GridPane grid = new GridPane();
    	grid.setHgap(10);
    	grid.setVgap(10);
    	
    	empIdField = new TextField();
    	overtimeField = new TextField();
    	bonusField = new TextField();
    	taxField = new TextField();
    	netSalaryField = new TextField();
    	
    	if (payroll != null) {
    		empIdField.setText(String.valueOf(payroll.getEmployeeId()));
    		overtimeField.setText(String.valueOf(payroll.getOvertime()));
    		bonusField.setText(String.valueOf(payroll.getBonus()));
    		taxField.setText(String.valueOf(payroll.getTax()));
    		netSalaryField.setText(String.valueOf(String.valueOf(payroll.getNetSalary())));
    	}
    	
    	//Labels
    	grid.add(new Label("Employee Id:"), 0, 0);
        grid.add(empIdField, 1, 0);

        grid.add(new Label("Overtime:"), 0, 1);
        grid.add(overtimeField, 1, 1);

        grid.add(new Label("Bonus:"), 0, 2);
        grid.add(bonusField, 1, 2);

        grid.add(new Label("Tax:"), 0, 3);
        grid.add(taxField, 1, 3);
        
        grid.add(new Label("Net Salary:"), 0, 4);
        grid.add(netSalaryField, 1, 4);
        
        payrollDialog.getDialogPane().setContent(grid);
        
        payrollDialog.setResultConverter(new Callback<ButtonType, Payroll>() {
        	@Override
            public Payroll call(ButtonType button) {
                if (button == ButtonType.OK) {
                	return new Payroll(
                            Integer.parseInt(empIdField.getText()),
                            Double.parseDouble(overtimeField.getText()),
                            Double.parseDouble(bonusField.getText()),
                            Double.parseDouble(taxField.getText()),
                            Double.parseDouble(netSalaryField.getText())
                	);
                }
                return null;
                
        	}
        });
    }
    public Dialog<Payroll> getDialog() {
        return payrollDialog;
    }
}

