package org.example.hrm_payroll_system.controller;

import java.util.List;

import org.example.hrm_payroll_system.models.Department;
import org.example.hrm_payroll_system.models.Employee;
import org.example.hrm_payroll_system.models.Payroll;
import org.example.hrm_payroll_system.persistence.DataManager;
import org.example.hrm_payroll_system.view.DepartmentReportView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class DepartmentReportController {
	private DepartmentReportView view;
    private ObservableList<Department> departmentList;
    private MainController mainController;
    private Stage menuStage;

    public DepartmentReportController(MainController mainController, Stage menuStage) {
    	 this.mainController = mainController;
         this.menuStage = menuStage;
    	
    	view = new DepartmentReportView();
        List<Department> departments = DataManager.loadDepartments();
        departmentList = FXCollections.observableArrayList(departments);

        view.getGenerateBtn().setOnAction(e -> generateReport());
        view.getBackBtn().setOnAction(e -> {
        	Stage stage = (Stage) view.getBackBtn().getScene().getWindow();
	        stage.close();
	        menuStage.show();
	    });
    }

    public void show(Stage stage) {
    	Stage reportStage = new Stage();
        view.show(reportStage, departmentList);
        menuStage.hide();
    }

    private void generateReport() {
        Department selectedDept = view.getDepartmentComboBox().getValue();
        if (selectedDept == null) return;

        List<Employee> employees = DataManager.loadEmployees().stream()
                .filter(e -> e.getDepartment().equals(selectedDept.getName()))
                .toList();

        List<Payroll> payrolls = DataManager.loadPayrolls();

        StringBuilder report = new StringBuilder();
        report.append("Department Payroll Report: ").append(selectedDept.getName()).append("\n");
        report.append("------------------------------------------------\n");

        double totalBase = 0, totalOvertime = 0, totalBonus = 0, totalTax = 0, totalNet = 0;

        for (Employee e : employees) {
            Payroll p = payrolls.stream().filter(pay -> pay.getEmployeeId() == e.getId()).findFirst().orElse(null);
            if (p != null) {
                report.append(e.getFirstName()).append(" ").append(e.getLastName()).append(" - Net Salary: ").append(p.getNetSalary()).append("\n");
                totalBase += e.getBaseSalary();
                totalOvertime += p.getOvertime();
                totalBonus += p.getBonus();
                totalTax += p.getTax();
                totalNet += p.getNetSalary();
            }
        }

        report.append("\nTotals:\n");
        report.append("Base Salary: ").append(totalBase).append("\n");
        report.append("Overtime: ").append(totalOvertime).append("\n");
        report.append("Bonus: ").append(totalBonus).append("\n");
        report.append("Tax: ").append(totalTax).append("\n");
        report.append("Net Salary: ").append(totalNet).append("\n");

        view.getReportArea().setText(report.toString());
    }
}
