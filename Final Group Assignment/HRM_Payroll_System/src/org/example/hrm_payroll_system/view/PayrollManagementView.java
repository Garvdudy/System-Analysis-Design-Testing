package org.example.hrm_payroll_system.view;

import org.example.hrm_payroll_system.models.Payroll;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PayrollManagementView {
	private TableView<Payroll> tableView;
	private Button addBtn;
	private Button editBtn;
	private Button deleteBtn;
	
    public PayrollManagementView() {
        tableView = new TableView<>();
        addBtn = new Button("Add");
        editBtn = new Button("Edit");
        deleteBtn = new Button("Delete");
        setupTableColumns();
    }
    
    private void setupTableColumns() {
    	//EMPLOYEE ID
    	TableColumn<Payroll, Integer> empIdCol = new TableColumn<>("Employee ID");
    	empIdCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getEmployeeId()).asObject());
    			
        // Overtime
        TableColumn<Payroll, Double> overtimeCol = new TableColumn<>("Overtime");
        overtimeCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleDoubleProperty(cell.getValue().getOvertime()).asObject());
        
        // Bonus
        TableColumn<Payroll, Double> bonusCol = new TableColumn<>("Bonus");
        bonusCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleDoubleProperty(cell.getValue().getBonus()).asObject());

        // Tax
        TableColumn<Payroll, Double> taxCol = new TableColumn<>("Tax");
        taxCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleDoubleProperty(cell.getValue().getTax()).asObject());
        
        // Net Salary
        TableColumn<Payroll, Double> netSalaryCol = new TableColumn<>("Net Salary");
        netSalaryCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleDoubleProperty(cell.getValue().getNetSalary()).asObject());
        
        tableView.getColumns().addAll(empIdCol, overtimeCol, bonusCol, taxCol, netSalaryCol);
    }
    
	public void show(Stage stage, ObservableList<Payroll> payrollList) {
		tableView.setItems(payrollList);
        VBox root = new VBox(10, tableView, addBtn, editBtn, deleteBtn);
        Scene scene = new Scene(root, 700, 400);
        
        stage.setScene(scene);
        stage.setTitle("Payroll Management");
        stage.show();	
	}
	
    //Getters
    public TableView<Payroll> getTableView() {
		return tableView;
	}

	public Button getAddBtn() {
		return addBtn;
	}

	public Button getEditBtn() {
		return editBtn;
	}

	public Button getDeleteBtn() {
		return deleteBtn;
	}
}

	


