package org.example.hrm_payroll_system.view;

import org.example.hrm_payroll_system.models.Department;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DepartmentReportView {
	private ComboBox<Department> departmentComboBox;
    private Button generateBtn;
    private TextArea reportArea;
	private Button backBtn;

    public DepartmentReportView() {
        departmentComboBox = new ComboBox<>();
        generateBtn = new Button("Generate Report");
        reportArea = new TextArea();
        reportArea.setEditable(false);
        reportArea.setWrapText(true);
        backBtn = new Button("Back");

    }

    public void show(Stage stage, ObservableList<Department> departments) {
		//Title
		Label title = new Label("Generate Department Payroll Report");
        title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

    	departmentComboBox.setItems(departments);
        departmentComboBox.setPromptText("Select Department");

        HBox topBox = new HBox(10, departmentComboBox, generateBtn);
        topBox.setAlignment(Pos.CENTER);

        VBox root = new VBox(15, title, topBox, reportArea, backBtn);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-padding: 20;");

        Scene scene = new Scene(root, 850, 400);
        stage.setScene(scene);
        stage.setTitle("Department Payroll Report");
        stage.show();
    }

    // Getters
    public ComboBox<Department> getDepartmentComboBox() {
        return departmentComboBox;
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