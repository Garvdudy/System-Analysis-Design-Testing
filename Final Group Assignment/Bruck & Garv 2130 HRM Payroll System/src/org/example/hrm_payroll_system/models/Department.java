package org.example.hrm_payroll_system.models;

import java.io.Serializable;

public class Department implements Serializable{
	private int departmentId;
	private String name;
	private int managerId;
	private int numOfEmployees;
	
	//Constructor
	public Department(int departmentId, String name, int managerId, int numOfEmployees) {
		super();
		this.departmentId = departmentId;
		this.name = name;
		this.managerId = managerId;
		this.numOfEmployees = numOfEmployees;
	}
	
	//Getters and Setters
	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public int getNumOfEmployees() {
		return numOfEmployees;
	}

	public void setNumOfEmployees(int numOfEmployees) {
		this.numOfEmployees = numOfEmployees;
	}

	//Methods
	
	//toString
	@Override
	public String toString() {
		return "Department [departmentId=" + departmentId + ", name=" + name + ", managerId=" + managerId
				+ ", numOfEmployees=" + numOfEmployees + "]";
	}
}
