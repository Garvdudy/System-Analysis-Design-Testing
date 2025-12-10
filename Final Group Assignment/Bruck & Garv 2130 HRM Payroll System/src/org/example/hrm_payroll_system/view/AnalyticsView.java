package org.example.hrm_payroll_system.view;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.example.hrm_payroll_system.controller.MainController;
import org.example.hrm_payroll_system.models.Attendance;
import org.example.hrm_payroll_system.models.Employee;
import org.example.hrm_payroll_system.models.Leave;
import org.example.hrm_payroll_system.persistence.DataManager;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AnalyticsView {
	private Button backBtn;
	
    public AnalyticsView() {
        backBtn = new Button("Back");
    }
    
    public Button getBackBtn() {
        return backBtn;
    }

	public void showAnalytics(Stage stage) {
		MainController mainController = new MainController(stage);
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20));

        // Toggle buttons for analytics type
        ToggleButton salaryBtn = new ToggleButton("Department Salary Distribution");
        ToggleButton attendanceBtn = new ToggleButton("Attendance Rate Chart");
        ToggleButton leavesBtn = new ToggleButton("Leaves Taken per Employee");

        ToggleGroup toggleGroup = new ToggleGroup();
        salaryBtn.setToggleGroup(toggleGroup);
        attendanceBtn.setToggleGroup(toggleGroup);
        leavesBtn.setToggleGroup(toggleGroup);
        salaryBtn.setSelected(true);

        HBox toggleBox = new HBox(10, salaryBtn, attendanceBtn, leavesBtn);
        toggleBox.setAlignment(Pos.CENTER);
        toggleBox.setPadding(new Insets(10));
        root.setTop(toggleBox);

        // Chart container
        BorderPane chartContainer = new BorderPane();
        root.setCenter(chartContainer);

        // Show initial chart
        chartContainer.setCenter(createSalaryChart());

        // Toggle actions
        salaryBtn.setOnAction(e -> chartContainer.setCenter(createSalaryChart()));
        attendanceBtn.setOnAction(e -> chartContainer.setCenter(createAttendanceChart()));
        leavesBtn.setOnAction(e -> chartContainer.setCenter(createLeavesChart()));

        HBox bottomBox = new HBox(backBtn);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(10));
        root.setBottom(bottomBox);
        backBtn.setOnAction(e -> mainController.showMainView());
        
        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("Analytics Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    private BarChart<String, Number> createLeavesChart() {
    	List<Employee> employees = DataManager.loadEmployees();
        List<Leave> leaves = DataManager.loadLeaves();

        // Count leaves per employee
        Map<String, Long> leaveCounts = employees.stream().collect(Collectors.toMap(
                        e -> e.getFirstName() + " " + e.getLastName(),
                        e -> leaves.stream()
                                .filter(l -> l.getEmployeeId() == e.getId())
                                .count()
                ));

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Employee");
        yAxis.setLabel("Leaves Taken");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Leaves Taken Per Employee");

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Leaves Count");

        leaveCounts.forEach((name, count) -> 
            series.getData().add(new XYChart.Data<>(name, count))
        );

        barChart.getData().add(series);

        return barChart;
    }
	private PieChart createSalaryChart() {
        List<Employee> employees = DataManager.loadEmployees();
        Map<String, Double> deptSalary = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.summingDouble(Employee::getBaseSalary)));

        PieChart pieChart = new PieChart();
        deptSalary.forEach((dept, salary) -> pieChart.getData().add(new PieChart.Data(dept, salary)));
        pieChart.setTitle("Department Salary Distribution");

        return pieChart;
    }

    private BarChart<String, Number> createAttendanceChart() {
        List<Employee> employees = DataManager.loadEmployees();
        List<Attendance> attendance = DataManager.loadAttendance();

        // Determine total working days
        Set<java.time.LocalDate> totalWorkingDays = attendance.stream()
                .map(Attendance::getDate)
                .collect(Collectors.toSet());
        int totalDays = totalWorkingDays.size() > 0 ? totalWorkingDays.size() : 1; // avoid divide by zero

        // Calculate attendance rate %
        Map<String, Double> attendanceRate = employees.stream()
                .collect(Collectors.toMap(
                        e -> e.getFirstName() + " " + e.getLastName(),
                        e -> attendance.stream()
                                .filter(a -> a.getEmployeeId() == e.getId() && a.getCheckIn() != null)
                                .map(Attendance::getDate)
                                .distinct()
                                .count() * 100.0 / totalDays
                ));

        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis(0, 100, 10); // 0% to 100%
        xAxis.setLabel("Employee");
        yAxis.setLabel("Attendance Rate (%)");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Attendance Rate");

        attendanceRate.forEach((emp, rate) -> series.getData().add(new XYChart.Data<>(emp, rate)));
        barChart.getData().add(series);
        barChart.setTitle("Employee Attendance Rate (%)");

        return barChart;
    }
}