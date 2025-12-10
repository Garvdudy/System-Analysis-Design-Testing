package org.example.hrm_payroll_system.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.example.hrm_payroll_system.models.Leave;
import org.example.hrm_payroll_system.models.Role;
import org.example.hrm_payroll_system.models.User;
import org.example.hrm_payroll_system.persistence.DataManager;

public class LeaveService {
	
    public void requestLeave(User currentUser, LocalDate start, LocalDate end, String leaveType) {
        if (currentUser.getRole() == Role.EMPLOYEE) {
            // Employees can only request for themselves
            addLeave(currentUser.getEmployeeId(), start, end, leaveType);
        } else {
            throw new SecurityException("Only employees can request leave.");
        }
    }
    
    private void addLeave(int employeeId, LocalDate start, LocalDate end, String type) {
        List<Leave> leaves = DataManager.loadLeaves();
        leaves.add(new Leave(employeeId, start, end, type, "Pending"));
        DataManager.saveLeaves(leaves);
    }

    public List<Leave> getLeavesByEmployee(int employeeId) {
        return DataManager.loadLeaves().stream()
                .filter(l -> l.getEmployeeId() == employeeId)
                .collect(Collectors.toList());
    }
    
    // Fetch leaves based on role
    public List<Leave> getLeavesForUser(User currentUser) {
        List<Leave> allLeaves = DataManager.loadLeaves();
        if (currentUser.getRole() == Role.EMPLOYEE) {
            return allLeaves.stream()
                    .filter(l -> l.getEmployeeId() == currentUser.getEmployeeId())
                    .collect(Collectors.toList());
        } else {
            // HR/Admin see all leaves
            return allLeaves;
        }
    }

    // HR/Admin approves a leave
    public void approveLeave(User currentUser, Leave leave) {
        if (currentUser.getRole() == Role.EMPLOYEE) {
            throw new SecurityException("Employees cannot approve leave.");
        }
        leave.setLeaveStatus("Approved");
        updateLeave(leave);
    }

    // HR/Admin rejects a leave
    public void rejectLeave(User currentUser, Leave leave) {
        if (currentUser.getRole() == Role.EMPLOYEE) {
            throw new SecurityException("Employees cannot reject leave.");
        }
        leave.setLeaveStatus("Rejected");
        updateLeave(leave);
    }

    private void updateLeave(Leave leave) {
        List<Leave> leaves = DataManager.loadLeaves();
        for (int i = 0; i < leaves.size(); i++) {
            Leave l = leaves.get(i);
            if (l.getEmployeeId() == leave.getEmployeeId()
                    && l.getStartDate().equals(leave.getStartDate())
                    && l.getEndDate().equals(leave.getEndDate())) {
                leaves.set(i, leave);
                break;
            }
        }
        DataManager.saveLeaves(leaves);
    }
}