package org.example.hrm_payroll_system.utils;

import org.example.hrm_payroll_system.models.User;

public class AppSession {
	private static final AppSession INSTANCE = new AppSession();
	private User currentUser;
	
	private AppSession() {
		
	}
	
	public static AppSession getInstance() {
		return INSTANCE;
	}
	
    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public void clear() {
        currentUser = null;
    }
}

