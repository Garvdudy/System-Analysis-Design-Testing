package org.example.hrm_payroll_system.view;

import org.example.hrm_payroll_system.models.Leave;
import org.example.hrm_payroll_system.models.Role;
import org.example.hrm_payroll_system.models.User;
import org.example.hrm_payroll_system.persistence.DataManager;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LeaveView {
	private TextField empIdField;
    private DatePicker startPicker;
    private DatePicker endPicker;
    private TextField typeField;
    private Button requestBtn;
    private Button backBtn;
    private Button approveBtn;
    private Button rejectBtn;
    private Label leavesLabel;
    private ListView<Leave> listView;

    public LeaveView() {
    	leavesLabel = new Label("Leeaves taken: 0");
        empIdField = new TextField();
        empIdField.setPromptText("Employee ID");

        startPicker = new DatePicker();
        endPicker = new DatePicker();
        typeField = new TextField();
        typeField.setPromptText("Sick / Vacation");
        approveBtn = new Button("Approve");
        rejectBtn = new Button("Reject");

        requestBtn = new Button("Request Leave");
        backBtn = new Button("Back");
        listView = new ListView<>();
    }

    public void show(Stage stage, ObservableList<Leave> leaves, User currentUser) {
        listView.setItems(leaves);
        
        long leavesTaken = DataManager.loadLeaves()
                    .stream()
                    .filter(l -> l.getEmployeeId() == currentUser.getEmployeeId())
                    .count();

        leavesLabel.setText("Leaves Taken: " + leavesTaken);

        HBox top;
        if (currentUser.getRole() == Role.EMPLOYEE) {
	        top = new HBox(8,
	            new Label("From:"), startPicker,
	            new Label("To:"), endPicker,
	            new Label("Type:"), typeField,
	            requestBtn, backBtn
	        );
        } else {
        	//HR/Admin can see all fields
        	top = new HBox(8,
                    new Label("Emp ID:"), empIdField,
                    new Label("From:"), startPicker,
                    new Label("To:"), endPicker,
                    new Label("Type:"), typeField,
                    requestBtn, backBtn, approveBtn, rejectBtn
                );
        }
        top.setAlignment(Pos.CENTER);

        VBox root = new VBox(15, leavesLabel, top, listView);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 18;");

        Scene scene = new Scene(root, 900, 460);
        stage.setScene(scene);
        stage.setTitle("Leave Management");
        stage.show();
    }

    //Getters
	public TextField getEmpIdField() {
		return empIdField;
	}

	public DatePicker getStartPicker() {
		return startPicker;
	}

	public DatePicker getEndPicker() {
		return endPicker;
	}

	public TextField getTypeField() {
		return typeField;
	}

	public Button getRequestBtn() {
		return requestBtn;
	}

	public Button getBackBtn() {
		return backBtn;
	}

	public Button getApproveBtn() {
		return approveBtn;
	}

	public Button getRejectBtn() {
		return rejectBtn;
	}

	public Label getLeavesLabel() {
		return leavesLabel;
	}
	
	public ListView<Leave> getListView() {
		return listView;
	}
}
