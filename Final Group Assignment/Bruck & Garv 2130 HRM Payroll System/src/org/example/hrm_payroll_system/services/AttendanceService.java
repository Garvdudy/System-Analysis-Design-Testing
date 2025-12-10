package org.example.hrm_payroll_system.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.example.hrm_payroll_system.models.Attendance;
import org.example.hrm_payroll_system.models.Role;
import org.example.hrm_payroll_system.models.User;
import org.example.hrm_payroll_system.persistence.DataManager;

public class AttendanceService {

	// Clock in / Clock out logic
	// Clock in
    public String clockIn(User currentUser) {
        int employeeId = currentUser.getEmployeeId();
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        List<Attendance> records = DataManager.loadAttendance();
        Attendance record = records.stream()
                .filter(r -> r.getEmployeeId() == employeeId && r.getDate().equals(today))
                .findFirst()
                .orElse(new Attendance(employeeId, today, null, null));

        if (record.getCheckIn() == null) {
            record.setCheckIn(now);
            if (!records.contains(record)) records.add(record);
            DataManager.saveAttendance(records);
            return "Clock-in recorded at " + now;
        } else {
            return "You have already clocked in today.";
        }
    }

    // Clock out
    public String clockOut(User currentUser) {
        int employeeId = currentUser.getEmployeeId();
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        List<Attendance> records = DataManager.loadAttendance();
        Attendance record = records.stream()
                .filter(r -> r.getEmployeeId() == employeeId && r.getDate().equals(today))
                .findFirst()
                .orElse(null);

        if (record == null || record.getCheckIn() == null) {
            return "You must clock in before clocking out.";
        }

        if (record.getCheckOut() != null) {
            return "You have already clocked out today.";
        }

        record.setCheckOut(now);
        DataManager.saveAttendance(records);
        return "Clock-out recorded at " + now;
    }

    // Get all attendance for an employee
    public List<Attendance> getAttendanceByEmployee(User currentUser, int targetEmployeeId) {
        if(currentUser.getRole() == Role.EMPLOYEE && currentUser.getEmployeeId() != targetEmployeeId) {
            throw new SecurityException("Employees can only view their own attendance!");
        }

        return DataManager.loadAttendance()
                .stream()
                .filter(a -> a.getEmployeeId() == targetEmployeeId)
                .toList();
    }

    // Get all attendance (for HR/Admin)
    public List<Attendance> getAllAttendance(User currentUser) {
        if(currentUser.getRole() == Role.EMPLOYEE) {
            throw new SecurityException("Employees cannot access all attendance records!");
        }
        return DataManager.loadAttendance();
    }
    
 // Fetch attendance based on role
    public List<Attendance> getAttendanceForUser(User currentUser) {
        List<Attendance> allAttendance = DataManager.loadAttendance();
        if (currentUser.getRole() == Role.EMPLOYEE) {
            // Employees see only their own records
            return allAttendance.stream()
                    .filter(a -> a.getEmployeeId() == currentUser.getEmployeeId())
                    .collect(Collectors.toList());
        } else {
            // HR/Admin see all
            return allAttendance;
        }
    }
}