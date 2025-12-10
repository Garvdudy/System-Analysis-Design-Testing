package org.example.hrm_payroll_system.controller;

import java.util.List;

import org.example.hrm_payroll_system.models.Employee;
import org.example.hrm_payroll_system.models.Payroll;
import org.example.hrm_payroll_system.persistence.DataManager;
import org.example.hrm_payroll_system.utils.AlertUtil;
import org.example.hrm_payroll_system.view.PayrollFormDialog;
import org.example.hrm_payroll_system.view.PayrollManagementView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class PayrollManagementController {
	
	private PayrollManagementView view;
    private MainController mainController;
    private ObservableList<Payroll> payrollList;
	
    public PayrollManagementController(MainController mainController) {
        this.mainController = mainController;
    	view = new PayrollManagementView();
       
        List<Payroll> payrolls = DataManager.loadPayrolls();
        payrollList = FXCollections.observableArrayList(payrolls);

        view.getAddBtn().setOnAction(e -> addPayroll());
        view.getEditBtn().setOnAction(e -> editPayroll());
        view.getDeleteBtn().setOnAction(e -> deletePayroll());
        view.getBackBtn().setOnAction(e -> mainController.showMainView());
    }
	
    public void show(Stage stage) {
    	view.show(stage, payrollList);
    }

	private void addPayroll() {
		List<Employee> employees = DataManager.loadEmployees();
		
		PayrollFormDialog payrollForm = new PayrollFormDialog(null, employees);
		payrollForm.getDialog().showAndWait().ifPresent(pay -> {
			
			boolean exists = payrollList.stream().anyMatch(p -> p.getEmployeeId() == pay.getEmployeeId());

	        if (exists) {
	            AlertUtil.showError("A payroll record already exists for Employee ID: " + pay.getEmployeeId());
	            return;
	        }
	        
            Payroll calculatedPayroll = calculatePayroll(pay.getEmployeeId(), pay.getOvertime(), pay.getBonus(), pay.getHoursWorked());
			
	        payrollList.add(calculatedPayroll);
	        DataManager.savePayrolls(payrollList);
		});
	}

	private void editPayroll() {
		Payroll selected = view.getTableView().getSelectionModel().getSelectedItem();
		if (selected != null) {
            List<Employee> employees = DataManager.loadEmployees();

	        PayrollFormDialog payrollForm = new PayrollFormDialog(selected, employees);
	        payrollForm.getDialog().showAndWait().ifPresent(pay -> {
	        	boolean duplicateId = payrollList.stream()
	                    .anyMatch(p -> p.getEmployeeId() == pay.getEmployeeId() && p != selected);

	            if (duplicateId) {
	                AlertUtil.showError("Employee ID " + pay.getEmployeeId() + " is already assigned to another payroll record.");
	                return;
	            }
	            
                Payroll recalculated = calculatePayroll(pay.getEmployeeId(), pay.getOvertime(), pay.getBonus(), pay.getHoursWorked());

                selected.setEmployeeId(pay.getEmployeeId());
                selected.setOvertime(recalculated.getOvertime());
                selected.setBonus(recalculated.getBonus());
                selected.setTax(recalculated.getTax());
                selected.setNetSalary(recalculated.getNetSalary());
                selected.setHoursWorked(recalculated.getHoursWorked());

	            view.getTableView().refresh();
	            DataManager.savePayrolls(payrollList);
	        });
		}
	}
	
	private void deletePayroll() {
		Payroll selected = view.getTableView().getSelectionModel().getSelectedItem();
		if (selected != null) {
			payrollList.remove(selected);
			DataManager.savePayrolls(payrollList);
		}
	}
	
	private Payroll calculatePayroll(int employeeId, double overtimeHours, double bonus, double hoursWorked) {
        Employee emp = DataManager.getEmployeeById(employeeId);
        if (emp == null) {
            throw new RuntimeException("Employee not found: " + employeeId);
        }

        double baseSalary = emp.getBaseSalary();
        double overtimeRate = 1.5; //1.5x hourly rate
        double standardHours = 172; // Monthly hours = 40 × 4.3 = 172 hours

        double hourlyRate = baseSalary / standardHours;
        double salary = hoursWorked * hourlyRate;
        double overtimePay = Math.round((overtimeHours * hourlyRate * overtimeRate) * 100.0) / 100.0;

        double grossSalary = salary + overtimePay + bonus;
        double tax = Math.round((grossSalary * 0.2) * 100.0) / 100.0; // 20% income tax
        double netSalary = Math.round((grossSalary - tax) * 100.0) / 100.0;

        return new Payroll(employeeId, overtimePay, bonus, tax, netSalary, hoursWorked);
    }
}

