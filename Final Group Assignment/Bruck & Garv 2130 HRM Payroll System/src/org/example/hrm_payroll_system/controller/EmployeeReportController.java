package org.example.hrm_payroll_system.controller;

import java.util.List;

import org.example.hrm_payroll_system.models.Employee;
import org.example.hrm_payroll_system.models.Payroll;
import org.example.hrm_payroll_system.persistence.DataManager;
import org.example.hrm_payroll_system.view.EmployeeReportView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class EmployeeReportController {
	private EmployeeReportView view;
	private ObservableList<Employee> employeeList;
    private MainController mainController;
    private Stage menuStage;

	
	public EmployeeReportController(MainController mainController, Stage menuStage) {
        this.mainController = mainController;
        this.menuStage = menuStage;

        view = new EmployeeReportView();
        List<Employee> employees = DataManager.loadEmployees();
        employeeList = FXCollections.observableArrayList(employees);

        view.getGenerateBtn().setOnAction(e -> generateReport());
        view.getBackBtn().setOnAction(e -> {
                Stage stage = (Stage) view.getBackBtn().getScene().getWindow();
                stage.close();
                menuStage.show();
            });
    }

    public void show(Stage stage) {
        Stage reportStage = new Stage();
        view.show(reportStage, employeeList);
        menuStage.hide();
    }

    private void generateReport() {
        Employee selected = view.getEmployeeComboBox().getValue();
        if (selected == null) return;

        Payroll payroll = DataManager.loadPayrolls().stream()
                .filter(p -> p.getEmployeeId() == selected.getId())
                .findFirst()
                .orElse(null);

        StringBuilder report = new StringBuilder();
        report.append("Employee Payroll Report\n");
        report.append("----------------------\n");
        report.append("Name: ").append(selected.getFirstName()).append(" ").append(selected.getLastName()).append("\n");
        report.append("Department: ").append(selected.getDepartment()).append("\n");
        report.append("Base Salary: ").append(selected.getBaseSalary()).append("\n");

        if (payroll != null) {
            report.append("Overtime: ").append(payroll.getOvertime()).append("\n");
            report.append("Bonus: ").append(payroll.getBonus()).append("\n");
            report.append("Tax: ").append(payroll.getTax()).append("\n");
            report.append("Net Salary: ").append(payroll.getNetSalary()).append("\n");
        } else {
            report.append("No payroll record found.\n");
        }

        view.getReportArea().setText(report.toString());
    }
}