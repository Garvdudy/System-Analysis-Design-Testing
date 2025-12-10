package org.example.hrm_payroll_system.view;

import org.example.hrm_payroll_system.models.Department;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DepartmentManagementView {
	private TableView<Department> tableView;
	private Button addBtn;
	private Button editBtn;
	private Button deleteBtn;
	private Button backBtn;
	
    public DepartmentManagementView() {
        tableView = new TableView<>();
        backBtn = new Button("Back");
        addBtn = new Button("Add");
        editBtn = new Button("Edit");
        deleteBtn = new Button("Delete");
        setupTableColumns();
    }
    
    private void setupTableColumns() {
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    	//Department ID
    	TableColumn<Department, Integer> departmentIdCol = new TableColumn<>("Department ID");
    	departmentIdCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getDepartmentId()).asObject());
    			
        // Name
        TableColumn<Department, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleStringProperty(cell.getValue().getName()));
        
        // Manager Id
        TableColumn<Department, Integer> managerIdCol = new TableColumn<>("Manager Id");
        managerIdCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getManagerId()).asObject());

        // numOfEmployees
        TableColumn<Department, Integer> numOfEmployeesCol = new TableColumn<>("Number of Employees");
        numOfEmployeesCol.setCellValueFactory(cell -> new javafx.beans.property.SimpleIntegerProperty(cell.getValue().getNumOfEmployees()).asObject());
        
        tableView.getColumns().addAll(departmentIdCol, nameCol, managerIdCol, numOfEmployeesCol);
    }
    
	public void show(Stage stage, ObservableList<Department> departmentList) {
		tableView.setItems(departmentList);
	
		//Action Buttons
        HBox buttons = new HBox(20, backBtn, addBtn, editBtn, deleteBtn);
        buttons.setAlignment(Pos.CENTER);
        
        VBox root = new VBox(15, tableView, buttons);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 30;");
        
        Scene scene = new Scene(root, 750, 450);
        stage.setScene(scene);
        stage.setTitle("Department Management");
        stage.show();	
	}
	
    //Getters
    public TableView<Department> getTableView() {
		return tableView;
	}

	public Button getBackBtn() {
		return backBtn;
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

	



