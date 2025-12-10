package org.example.hrm_payroll_system.view;

import org.example.hrm_payroll_system.models.Department;

import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DepartmentManagementView {
	private TableView<Department> tableView;
	private Button addBtn;
	private Button editBtn;
	private Button deleteBtn;
	
    public DepartmentManagementView() {
        tableView = new TableView<>();
        addBtn = new Button("Add");
        editBtn = new Button("Edit");
        deleteBtn = new Button("Delete");
        setupTableColumns();
    }
    
    private void setupTableColumns() {
    	//Department ID
    	TableColumn<Department, Integer> departmentIdCol = new TableColumn<>("Department ID");
    	departmentIdCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getDepartmentId()).asObject());
    			
        // Name
        TableColumn<Department, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getName()));
        
        // Manager Id
        TableColumn<Department, String> managerIdCol = new TableColumn<>("Manager Id");
        managerIdCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getManagerId()));

        // numOfEmployees
        TableColumn<Department, Integer> numOfEmployeesCol = new TableColumn<>("Number of Employees");
        numOfEmployeesCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getNumOfEmployees()).asObject());
        
        tableView.getColumns().addAll(departmentIdCol, nameCol, managerIdCol, numOfEmployeesCol);
    }
    
	public void show(Stage stage, ObservableList<Department> departmentList) {
		tableView.setItems(departmentList);
        VBox root = new VBox(10, tableView, addBtn, editBtn, deleteBtn);
        Scene scene = new Scene(root, 700, 400);
        
        stage.setScene(scene);
        stage.setTitle("Department Management");
        stage.show();	
	}
	
    //Getters
    public TableView<Department> getTableView() {
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

	



