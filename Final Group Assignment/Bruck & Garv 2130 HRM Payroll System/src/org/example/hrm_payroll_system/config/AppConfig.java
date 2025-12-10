package org.example.hrm_payroll_system.config;

import java.nio.file.Path;
import java.nio.file.Paths;

public class AppConfig {
	
	//Constants for configuration
	public static final Path DATA_DIR = Paths.get(System.getProperty("user.dir"), "resources", "data");
    public static final Path USERS_JSON = DATA_DIR.resolve("users.json");
    public static final Path EMPLOYEES_JSON = DATA_DIR.resolve("employees.json");
    public static final Path DEPARTMENTS_JSON = DATA_DIR.resolve("departments.json");
    public static final Path PAYROLLS_JSON = DATA_DIR.resolve("payrolls.json");
    public static final Path ATTENDANCE_JSON = DATA_DIR.resolve("attendance.json");
    
    // Payroll defaults
    public static final double DEFAULT_TAX_RATE = 0.10; // 10%
    public static final double DEFAULT_OT_MULTIPLIER = 1.5;
    public static final int STANDARD_MONTHLY_HOURS = 160;
}
