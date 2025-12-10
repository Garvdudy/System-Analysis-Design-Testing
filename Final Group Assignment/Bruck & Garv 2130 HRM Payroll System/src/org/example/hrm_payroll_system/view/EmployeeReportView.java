package org.example.hrm_payroll_system.view;

import org.example.hrm_payroll_system.models.Employee;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EmployeeReportView {
	private ComboBox<Employee> employeeComboBox;
    private Button generateBtn;
    private TextArea reportArea;
	private Button backBtn;


    public EmployeeReportView() {
        employeeComboBox = new ComboBox<>();
        generateBtn = new Button("Generate Report");
        backBtn = new Button("Back");
        reportArea = new TextArea();
        reportArea.setEditable(false);
        reportArea.setWrapText(true);
    }

    public void show(Stage stage, ObservableList<Employee> employees) {
		//Title
		Label title = new Label("Generate Department Payroll Report");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

        employeeComboBox.setItems(employees);
        employeeComboBox.setPromptText("Select Employee");

        HBox topBox = new HBox(10, employeeComboBox, generateBtn);
        topBox.setAlignment(Pos.CENTER);

        VBox root = new VBox(15, title, topBox, reportArea, backBtn);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 20;");

        Scene scene = new Scene(root, 850, 400);
        stage.setScene(scene);
        stage.setTitle("Employee Payroll Report");
        stage.show();
    }

    // Getters
    public ComboBox<Employee> getEmployeeComboBox() {
        return employeeComboBox;
    }

    public Button getGenerateBtn() {
        return generateBtn;
    }

    public TextArea getReportArea() {
        return reportArea;
    }

	public Button getBackBtn() {
		return backBtn;
	}
}