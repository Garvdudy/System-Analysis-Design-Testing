package org.example.hrm_payroll_system.models;

import java.io.Serializable;

public class Payroll implements Serializable{

	private int employeeId;
	private double overtime;
	private double bonus;
	private  double tax;
	private double netSalary;
	
	//Constructor
	public Payroll(int employeeId, double overtime, double bonus, double tax, double netSalary) {
		super();
		this.employeeId = employeeId;
		this.overtime = overtime;
		this.bonus = bonus;
		this.tax = tax;
		this.netSalary = netSalary;
	}

	//Getters and Setters
	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public double getOvertime() {
		return overtime;
	}

	public void setOvertime(double overtime) {
		this.overtime = overtime;
	}

	public double getBonus() {
		return bonus;
	}

	public void setBonus(double bonus) {
		this.bonus = bonus;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public double getNetSalary() {
		return netSalary;
	}

	public void setNetSalary(double netSalary) {
		this.netSalary = netSalary;
	}
	
	//Methods
	
	//toString
	@Override
	public String toString() {
		return "Payroll [employeeId=" + employeeId + ", overtime=" + overtime + ", bonus=" + bonus + ", tax=" + tax
				+ ", netSalary=" + netSalary + "]";
	}
	

	
	
	
	
}
