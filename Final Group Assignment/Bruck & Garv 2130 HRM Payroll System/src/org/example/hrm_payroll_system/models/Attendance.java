package org.example.hrm_payroll_system.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Attendance implements Serializable{

	private int employeeId;
    private LocalDate date;
    private LocalTime clockIn;
    private LocalTime clockOut;

    public Attendance() {}

    public Attendance(int employeeId, LocalDate date, LocalTime checkIn, LocalTime checkOut) {
        this.employeeId = employeeId;
        this.date = date;
        this.clockIn = checkIn;
        this.clockOut = checkOut;
    }

    // Getters & Setters
	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getCheckIn() {
		return clockIn;
	}

	public void setCheckIn(LocalTime checkIn) {
		this.clockIn = checkIn;
	}

	public LocalTime getCheckOut() {
		return clockOut;
	}

	public void setCheckOut(LocalTime checkOut) {
		this.clockOut = checkOut;
	}

    @Override
    public String toString() {
        return "Attendance [employeeId=" + employeeId + ", date=" + date +
               ", checkIn=" + clockIn + ", checkOut=" + clockOut + "]";
    }
}