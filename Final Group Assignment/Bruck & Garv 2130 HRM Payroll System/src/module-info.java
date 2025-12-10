module HRM_Payroll_System {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires com.google.gson;

    opens org.example.hrm_payroll_system to javafx.fxml;
    opens org.example.hrm_payroll_system.models to com.google.gson;
    exports org.example.hrm_payroll_system;
}
