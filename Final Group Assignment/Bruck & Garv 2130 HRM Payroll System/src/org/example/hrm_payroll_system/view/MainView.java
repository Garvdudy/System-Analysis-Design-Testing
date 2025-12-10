package org.example.hrm_payroll_system.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView {
	private Button employeeBtn;
	private Button payrollBtn;
	private Button departmentBtn;
	private Button reportsBtn;
	private Button attendanceBtn;
	private Button leaveBtn;
	private Button analyticsBtn;

	
    private final Map<String, Button> moduleButtons = new HashMap<>();
    private List<Button> buttons = new ArrayList<>();

    
	public MainView() {
		employeeBtn = new Button("Employee Management");
		payrollBtn = new Button("Payroll Management");
		departmentBtn = new Button("Department Management");
		reportsBtn = new Button("Generate Reports");
		attendanceBtn = new Button("Attendance");
		leaveBtn = new Button("Leave");
		analyticsBtn = new Button("Analytics");

		
		//All buttons same width
        employeeBtn.setPrefWidth(220);
        payrollBtn.setPrefWidth(220);
        departmentBtn.setPrefWidth(220);
        reportsBtn.setPrefWidth(220);
        attendanceBtn.setPrefWidth(220);
        leaveBtn.setPrefWidth(220);
        analyticsBtn.setPrefWidth(220);

	}
	
	public void show(Stage stage, List<String> modulesToShow) {
		VBox root = new VBox(30);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 30;");
		
		//Title
		Label title = new Label("Welcome to HRM & Payroll System");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

		Label subtitle = new Label("Please select a module to begin");
        subtitle.setStyle("-fx-font-size: 18px;");
        
        //Module Buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        
        moduleButtons.clear(); // Clear previous buttons if any

        // Add only allowed buttons
        if (modulesToShow.contains("EMPLOYEE")) buttonBox.getChildren().add(employeeBtn);
        if (modulesToShow.contains("PAYROLL")) buttonBox.getChildren().add(payrollBtn);
        if (modulesToShow.contains("DEPARTMENT")) buttonBox.getChildren().add(departmentBtn);
        if (modulesToShow.contains("REPORTS")) buttonBox.getChildren().add(reportsBtn);
        if (modulesToShow.contains("ATTENDANCE")) buttonBox.getChildren().add(attendanceBtn);
        if (modulesToShow.contains("LEAVE")) buttonBox.getChildren().add(leaveBtn);
        if (modulesToShow.contains("ANALYTICS")) buttonBox.getChildren().add(analyticsBtn);

        root.getChildren().addAll(title, subtitle, buttonBox);
		
		Scene scene = new Scene(root, 600, 250);
		stage.setScene(scene);
	    stage.setTitle("HRM & Payroll System");
	    stage.show();
	}
	
	//Getters
	public Button getEmployeeBtn() {
		return employeeBtn;
	}

	public Button getPayrollBtn() {
		return payrollBtn;
	}

	public Button getDepartmentBtn() {
		return departmentBtn;
	}

	public Button getReportsBtn() {
		return reportsBtn;
	}
	public Button getAttendanceBtn() {
		return attendanceBtn;
	}
	public Button getLeaveBtn() {
		return leaveBtn;
	}

	public Button getAnalyticsBtn() {
		return analyticsBtn;
	}
	
}
