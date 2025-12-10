module HRM_Payroll_System {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;

    opens org.example.hrm_payroll_system to javafx.fxml;
    exports org.example.hrm_payroll_system;
}
