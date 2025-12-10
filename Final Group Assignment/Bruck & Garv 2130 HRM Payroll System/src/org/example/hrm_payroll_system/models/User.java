package org.example.hrm_payroll_system.models;

import java.io.Serializable;

public class User implements Serializable{
	private String username;
	private String hashedPassword;
	private Role role;
	private int employeeId;
	
	public User() {
		
	}

	public User(String username, String hashedPassword, Role role, int employeeId) {
		super();
		this.username = username;
		this.hashedPassword = hashedPassword;
		this.role = role;
		this.employeeId = employeeId;
	}

	//Getters and Setters
	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getHashedPassword() {
		return hashedPassword;
	}


	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}


	public int getEmployeeId() {
		return employeeId;
	}


	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	//toString

	@Override
	public String toString() {
		return "User [username=" + username + ", role=" + role + ", employeeId=" + employeeId + "]";
	}
	
	
}
