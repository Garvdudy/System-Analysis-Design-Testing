package org.example.hrm_payroll_system.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.example.hrm_payroll_system.models.Role;
import org.example.hrm_payroll_system.utils.AppSession;
import org.example.hrm_payroll_system.view.AnalyticsView;
import org.example.hrm_payroll_system.view.MainView;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainController {
	private MainView view;
	private Stage mainStage;
    private List<String> allowedModules;
	
	public MainController(Stage stage) {
		this.mainStage = stage;
		view = new MainView();
		
		//Method to implement Role Based Access Control
		applyRBAC();
		
		//setupModuleActions();
		showMainView();
		
		//Actions for buttons
		view.getEmployeeBtn().setOnAction(e -> openEmployeeManagement());
		view.getPayrollBtn().setOnAction(e -> openPayrollManagement());
		view.getDepartmentBtn().setOnAction(e -> openDepartmentManagement());
		
	    if (allowedModules.contains("REPORTS")) {
	        view.getReportsBtn().setOnAction(e -> openReports());
	    }
	    
	    if (allowedModules.contains("ATTENDANCE")) {
	        view.getAttendanceBtn().setOnAction(e -> new AttendanceController(this).show(mainStage));
	    }
	    if (allowedModules.contains("LEAVE")) {
	        view.getLeaveBtn().setOnAction(e -> new LeaveController(this).show(mainStage));
	    }

	    if (allowedModules.contains("ANALYTICS")) {
	        view.getAnalyticsBtn().setOnAction(e -> {
	            AnalyticsView analyticsView = new AnalyticsView();
	            analyticsView.showAnalytics(mainStage);
	        });
	        
	    } else {
	        view.getAnalyticsBtn().setDisable(true);
	    }
	}
	
	private void openEmployeeManagement() {
		EmployeeManagementController empController = new EmployeeManagementController(this);
		//Stage empStage = new Stage();
		empController.show(mainStage);
	}
	
	private void openPayrollManagement() {
		PayrollManagementController payrollController = new PayrollManagementController(this);
		//Stage payStage = new Stage();
		payrollController.show(mainStage);
	}
	
	private void openDepartmentManagement() {
		DepartmentManagementController deptController = new DepartmentManagementController(this);
		//Stage deptStage = new Stage();
		deptController.show(mainStage);
	}
	
	private void openAnalyticsUI() {
	    AnalyticsView view = new AnalyticsView();
	    view.showAnalytics(mainStage);
	}
	
	
	public void showMainView() {
        view.show(mainStage, allowedModules);
        Map<String, Runnable> moduleActions = Map.of(
                "EMPLOYEE", this::openEmployeeManagement,
                "PAYROLL", this::openPayrollManagement,
                "DEPARTMENT", this::openDepartmentManagement
            );

            /*for (String module : allowedModules) {
                Button btn = view.getModuleButton(module);
                if (btn != null && moduleActions.containsKey(module)) {
                    btn.setOnAction(e -> moduleActions.get(module).run());
                }
            }*/
	}
	
	private void openReports() {
		Stage reportStage = new Stage();
	    
	    Button empReportBtn = new Button("Employee Report");
	    Button deptReportBtn = new Button("Department Report");
	    
	    empReportBtn.setPrefWidth(180);
	    deptReportBtn.setPrefWidth(180);

	    empReportBtn.setOnAction(e -> {
	        new EmployeeReportController(this, reportStage).show(reportStage);
	    });

	    deptReportBtn.setOnAction(e -> {
	        new DepartmentReportController(this, reportStage).show(reportStage);
	    });

	    VBox root = new VBox(20, empReportBtn, deptReportBtn);
	    root.setAlignment(Pos.CENTER);
	    root.setStyle("-fx-padding: 30;");

	    Scene scene = new Scene(root, 300, 200);
	    reportStage.setScene(scene);
	    reportStage.setTitle("Select Report Type");
	    reportStage.show();
	}
	
	private void applyRBAC() {
	    allowedModules = new ArrayList<>();
		if(!AppSession.getInstance().isLoggedIn()) {
			return;
		}
		
		Role role = AppSession.getInstance().getCurrentUser().getRole();
		switch (role)  {
			case ADMIN: //Admin ccan access all buttons
				allowedModules.addAll(List.of("EMPLOYEE", "PAYROLL", "DEPARTMENT", "REPORTS","ATTENDANCE","LEAVE", "ANALYTICS"));
				break;
			case HR://HR cannot access Departments record
				allowedModules.addAll(List.of("EMPLOYEE", "PAYROLL","ATTENDANCE","LEAVE", "ANALLYTICS"));
	            break;
			case EMPLOYEE: //Employee cant access any buttons
				allowedModules.addAll(List.of("EMPLOYEE", "DEPARTMENT","ATTENDANCE","LEAVE"));

				break; 
		}
	}
}