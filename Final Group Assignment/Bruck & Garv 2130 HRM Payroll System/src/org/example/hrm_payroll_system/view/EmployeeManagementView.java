package org.example.hrm_payroll_system.view;

import org.example.hrm_payroll_system.models.Employee;
import org.example.hrm_payroll_system.models.Role;
import org.example.hrm_payroll_system.utils.AppSession;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EmployeeManagementView {

	private TableView<Employee> tableView;
	private Button addBtn;
	private Button editBtn;
	private Button deleteBtn;
	private Button backBtn;
	
    public EmployeeManagementView() {
        tableView = new TableView<>();
        addBtn = new Button("Add");
        editBtn = new Button("Edit");
        deleteBtn = new Button("Delete");
        backBtn = new Button("Back");
        setupTableColumns();
    }
    
    private void setupTableColumns() {
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        boolean isEmployee = AppSession.getInstance().getCurrentUser().getRole() == Role.EMPLOYEE;

        addBtn.setDisable(isEmployee);
        editBtn.setDisable(isEmployee);
        deleteBtn.setDisable(isEmployee);
        
        if (isEmployee) {
            addBtn.setTooltip(new Tooltip("You do not have permission to add employees"));
            editBtn.setTooltip(new Tooltip("You do not have permission to edit employees"));
            deleteBtn.setTooltip(new Tooltip("You do not have permission to delete employees"));
        }
        
    	//ID
    	TableColumn<Employee, Integer> idCol = new TableColumn<>("ID");
        idCol.setPrefWidth(60);
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
        
        if (AppSession.getInstance().getCurrentUser().getRole() == Role.EMPLOYEE) {
        	bSalaryCol.setVisible(false);
        }
    }
    
	public void show(Stage stage, ObservableList<Employee> employeeList) {
		tableView.setItems(employeeList);
		//tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		//Action Buttons
        HBox buttons = new HBox(20, backBtn, addBtn, editBtn, deleteBtn);
        buttons.setAlignment(Pos.CENTER);
        
        VBox root = new VBox(15, tableView, buttons);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 30;");
        
        Scene scene = new Scene(root, 750, 450);
        
        stage.setScene(scene);
        stage.setTitle("Employee Management");
        stage.show();	
	}
	
    //Getters
    public TableView<Employee> getTableView() {
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

	

	