package org.example.hrm_payroll_system.models;

import java.io.Serializable;

public class Employee implements Serializable{

	private int id;
	private String firstName;
	private String lastName;
	private String department;
	private double baseSalary;
	
	//Constructor
	public Employee(int id, String firstName, String lastName, String department, double baseSalary) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.department = department;
		this.baseSalary = baseSalary;
	}
	
	//Getterrs and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public double getBaseSalary() {
		return baseSalary;
	}
	public void setBaseSalary(double baseSalary) {
		this.baseSalary = baseSalary;
	}

	//Methods
	
	//toString
	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", department="
				+ department + ", baseSalary=" + baseSalary + "]";
	}
}
