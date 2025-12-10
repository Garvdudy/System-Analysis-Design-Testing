package org.example.hrm_payroll_system.controller;

import java.util.ArrayList;
import java.util.List;

import org.example.hrm_payroll_system.models.Role;
import org.example.hrm_payroll_system.models.User;
import org.example.hrm_payroll_system.persistence.DataManager;
import org.example.hrm_payroll_system.utils.AppSession;
import org.example.hrm_payroll_system.utils.PasswordHasher;

public class LoginController {
	private List<User> users = new ArrayList<>();
	
	public LoginController() {}
	
	public boolean authenticate(String username, String password) {
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            return false;
        }

        // Lookup user by username
        User user = DataManager.getByUsername(username);

        if (user == null) return false;

        // Hash the input password and compare with stored password
        String hashedInput = PasswordHasher.sha256(password);

        if (hashedInput.equals(user.getHashedPassword())) {
            // Successful login
            AppSession.getInstance().setCurrentUser(user);
            return true;
        }

        return false;
    }
}