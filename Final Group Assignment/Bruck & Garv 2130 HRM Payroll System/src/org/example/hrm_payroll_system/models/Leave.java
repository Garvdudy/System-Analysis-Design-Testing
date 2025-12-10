package org.example.hrm_payroll_system.models;

import java.io.Serializable;
import java.time.LocalDate;

public class Leave implements Serializable{
	
	private int employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String leaveType;   // Type of Leave: "Sick", "Vacation"
    private String leaveStatus; // "Pending", "Approved", "Rejected"

    public Leave() {}

    public Leave(int employeeId, LocalDate startDate, LocalDate endDate, String type, String status) {
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaveType = type;
        this.leaveStatus = status;
    }

    // Getters & Setters
    public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	public String getLeaveStatus() {
		return leaveStatus;
	}

	public void setLeaveStatus(String leaveStatus) {
		this.leaveStatus = leaveStatus;
	}

    @Override
    public String toString() {
        return "Leave [employeeId=" + employeeId + ", startDate=" + startDate +
               ", endDate=" + endDate + ", type of leave=" + leaveType + ", status=" + leaveStatus + "]";
    }
}