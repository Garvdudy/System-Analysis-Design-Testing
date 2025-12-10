package org.example.hrm_payroll_system.controller;

import java.util.List;

import org.example.hrm_payroll_system.models.Payroll;
import org.example.hrm_payroll_system.persistence.DataManager;
import org.example.hrm_payroll_system.view.PayrollFormDialog;
import org.example.hrm_payroll_system.view.PayrollManagementView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class PayrollManagementController {
	
	private PayrollManagementView view;
    private ObservableList<Payroll> payrollList;
	
    public PayrollManagementController() {
        view = new PayrollManagementView();
        List<Payroll> payrolls = DataManager.loadPayrolls();
        payrollList = FXCollections.observableArrayList(payrolls);

        view.getAddBtn().setOnAction(e -> addPayroll());
        view.getEditBtn().setOnAction(e -> editPayroll());
        view.getDeleteBtn().setOnAction(e -> deletePayroll());
    }
	
    public void show(Stage stage) {
    	view.show(stage, payrollList);
    }

	private void addPayroll() {
		PayrollFormDialog payrollForm = new PayrollFormDialog(null);
		payrollForm.getDialog().showAndWait().ifPresent(pay -> {
			
	        payrollList.add(pay);
	        DataManager.savePayrolls(payrollList);
		});
	}
	
	private void editPayroll() {
		Payroll selected = view.getTableView().getSelectionModel().getSelectedItem();
		if (selected != null) {
	        PayrollFormDialog empForm = new PayrollFormDialog(selected);
	        empForm.getDialog().showAndWait().ifPresent(pay -> {
	            selected.setEmployeeId(pay.getEmployeeId());
	            selected.setOvertime(pay.getOvertime());
	            selected.setBonus(pay.getBonus());
	            selected.setTax(pay.getTax());
	            selected.setNetSalary(pay.getNetSalary());
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
}
