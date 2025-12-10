package org.example.hrm_payroll_system.view;

import java.util.List;

import org.example.hrm_payroll_system.models.Employee;
import org.example.hrm_payroll_system.persistence.DataManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EmployeeManagementView {

	private TableView<Employee> tableView;
	private Button addBtn;
	private Button editBtn;
	private Button deleteBtn;
	
    public EmployeeManagementView() {
        tableView = new TableView<>();
        addBtn = new Button("Add");
        editBtn = new Button("Edit");
        deleteBtn = new Button("Delete");
        setupTableColumns();
    }
    
    private void setupTableColumns() {
    	//ID
    	TableColumn<Employee, Integer> idCol = new TableColumn<>("ID");
    	idCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getId()).asObject());
    			
    	// First Name
        TableColumn<Employee, String> fNameCol = new TableColumn<>("First Name");
        fNameCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getFirstName()));

        // Last Name
        TableColumn<Employee, String> lNameCol = new TableColumn<>("Last Name");
        lNameCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getLastName()));

        // Department
        TableColumn<Employee, String> depCol = new TableColumn<>("Department");
        depCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getDepartment()));

        // Base Salary
        TableColumn<Employee, Double> bSalaryCol = new TableColumn<>("Base Salary");
        bSalaryCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleDoubleProperty(cell.getValue().getBaseSalary()).asObject());

        tableView.getColumns().addAll(idCol, fNameCol, lNameCol, depCol, bSalaryCol);
    }
    
	public void show(Stage stage, ObservableList<Employee> employeeList) {
		tableView.setItems(employeeList);
        VBox root = new VBox(10, tableView, addBtn, editBtn, deleteBtn);
        Scene scene = new Scene(root, 600, 400);
        
        stage.setScene(scene);
        stage.setTitle("Employee Management");
        stage.show();	
	}
	
    //Getters
    public TableView<Employee> getTableView() {
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

	

	