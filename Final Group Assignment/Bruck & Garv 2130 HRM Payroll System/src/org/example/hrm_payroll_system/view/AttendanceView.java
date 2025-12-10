package org.example.hrm_payroll_system.view;

import java.util.List;

import org.example.hrm_payroll_system.models.Attendance;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AttendanceView {
	private TextField empIdField;
    private Button clockInBtn;
    private Button clockOutBtn;
    private Button backBtn;
    private ListView<Attendance> listView;

    public AttendanceView() {
        empIdField = new TextField();
        empIdField.setPromptText("Employee ID");
        clockInBtn = new Button("Clock In");
        clockOutBtn = new Button("Clock Out");
        backBtn = new Button("Back");
        listView = new ListView<>();
    }

    public void show(Stage stage) {
        HBox controls = new HBox(10, clockInBtn, clockOutBtn, backBtn);
        controls.setAlignment(Pos.CENTER);

        VBox root = new VBox(12, controls, listView);
        root.setStyle("-fx-padding: 20;");
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 760, 420);
        stage.setScene(scene);
        stage.setTitle("Attendance / Timesheet");
        stage.show();
    }

    public TextField getEmpIdField() { return empIdField; }
    public Button getClockInBtn() { return clockInBtn; }
    public Button getClockOutBtn() { return clockOutBtn; }
    public Button getBackBtn() { return backBtn; }
    public ListView<Attendance> getListView() { return listView; }

    public void setAttendanceList(ObservableList<Attendance> list) {
        listView.setItems(list);
    }

    public void showMessage(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg, ButtonType.OK);
        alert.showAndWait();
    }
}