package org.example.hrm_payroll_system.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.example.hrm_payroll_system.models.Attendance;
import org.example.hrm_payroll_system.models.Department;
import org.example.hrm_payroll_system.models.Employee;
import org.example.hrm_payroll_system.models.Leave;
import org.example.hrm_payroll_system.models.Payroll;
import org.example.hrm_payroll_system.models.Role;
import org.example.hrm_payroll_system.models.User;
import org.example.hrm_payroll_system.utils.PasswordHasher;

public class DataManager {
	
	private static final String EMPLOYEE_FILE = "employees.dat";
	private static final String DEPARTMENT_FILE = "department.dat";
	private static final String PAYROLL_FILE = "payroll.dat";
	private static final String USER_FILE = System.getProperty("user.home") + "/HRM_Payroll_System/users.dat";
	private static final String ATTENDANCE_FILE = "attendance.dat";
	private static final String LEAVE_FILE = "leaves.dat";
	
	//--Employees--
	public static void saveEmployees(List<Employee> employees) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(EMPLOYEE_FILE))){
            ArrayList<Employee> listToSave = new ArrayList<>(employees);
			oos.writeObject(listToSave);
            System.out.println("Employees saved successfully!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<Employee> loadEmployees() {
		File file = new File(EMPLOYEE_FILE);
		if (!file.exists()) {
			System.out.println("empployees.dat not found — creating new file...");
			saveEmployees(new ArrayList<>());
			return new ArrayList<>();
		}
		try (ObjectInputStream ois = new ObjectInputStream (new FileInputStream(EMPLOYEE_FILE))) {
			return (List<Employee>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
	        System.out.println("Employee file not found, returning empty list.");
	        return new ArrayList<>();
		}	
	}
	
	public static Employee getEmployeeById(int id) {
	    return loadEmployees().stream()
	        .filter(e -> e.getId() == id)
	        .findFirst()
	        .orElse(null);
	}

	//--Departments--
	public static void saveDepartments(List<Department> departments) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DEPARTMENT_FILE))){
            ArrayList<Department> listToSave = new ArrayList<>(departments);
			oos.writeObject(listToSave);
            System.out.println("Departments saved successfully!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<Department> loadDepartments() {
		File file = new File(DEPARTMENT_FILE);
		if (!file.exists()) {
			System.out.println("department.dat not found — creating new file...");
			saveDepartments(new ArrayList<>());
			return new ArrayList<>();
		}
		try (ObjectInputStream ois = new ObjectInputStream (new FileInputStream(DEPARTMENT_FILE))) {
			return (List<Department>) ois.readObject();
		} catch (IOException | ClassNotFoundException e) {
	        System.out.println("Department file not found, returning empty list.");
	        return new ArrayList<>();
		}
	}
	
	//--Payrolls--
	public static void savePayrolls(List<Payroll> payrolls) {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PAYROLL_FILE))){
            ArrayList<Payroll> listToSave = new ArrayList<>(payrolls);
			oos.writeObject(listToSave);
            System.out.println("Payrolls saved successfully!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<Payroll> loadPayrolls() {
		File file = new File(PAYROLL_FILE);
		if (!file.exists()) {
			System.out.println("payroll.dat not found — creating new file...");
			savePayrolls(new ArrayList<>());
			return new ArrayList<>();
		}
		
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PAYROLL_FILE))) {
            return (List<Payroll>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Payroll file not found, returning empty list.");
            return new ArrayList<>();
        }
	}
	
	//--Users--
	public static void saveUsers(List<User> users) {
	    // Ensure folder exists
	    File file = new File(USER_FILE);
	    file.getParentFile().mkdirs();

	    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
	        oos.writeObject(new ArrayList<>(users));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	@SuppressWarnings("unchecked")
	public static List<User> loadUsers() {
	    File file = new File(USER_FILE);
	    if (!file.exists()) {
	        initializeUsers(); // ensures default users exist
	    }

	    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
	        return (List<User>) ois.readObject();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ArrayList<>();
	    }
	}

	public static void initializeUsers() {
	    List<User> users = new ArrayList<>();
	    users.add(new User("admin", PasswordHasher.sha256("admin123"), Role.ADMIN, 1));
	    users.add(new User("alice", PasswordHasher.sha256("alice123"), Role.HR, 101));
	    users.add(new User("bruck", PasswordHasher.sha256("bruck123"), Role.EMPLOYEE, 201));

	    saveUsers(users);
	}

	public static User getByUsername(String username) {
	    return loadUsers().stream()
	                      .filter(u -> u.getUsername().equalsIgnoreCase(username))
	                      .findFirst()
	                      .orElse(null);
	}
	
	//--Attendance--
	public static void saveAttendance(List<Attendance> records) {
	    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ATTENDANCE_FILE))) {
	        oos.writeObject(new ArrayList<>(records));
	    } catch (IOException e) { e.printStackTrace(); }
	}

	@SuppressWarnings("unchecked")
	public static List<Attendance> loadAttendance() {
	    File file = new File(ATTENDANCE_FILE);
	    if (!file.exists()) return new ArrayList<>();
	    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
	        return (List<Attendance>) ois.readObject();
	    } catch (IOException | ClassNotFoundException e) { return new ArrayList<>(); }
	}
	
	//--Leave--
	public static void saveLeaves(List<Leave> leaves) {
	    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(LEAVE_FILE))) {
	        oos.writeObject(new ArrayList<>(leaves));
	    } catch (IOException e) { e.printStackTrace(); }
	}

	@SuppressWarnings("unchecked")
	public static List<Leave> loadLeaves() {
	    File file = new File(LEAVE_FILE);
	    if (!file.exists()) return new ArrayList<>();
	    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
	        return (List<Leave>) ois.readObject();
	    } catch (IOException | ClassNotFoundException e) { return new ArrayList<>(); }
	}

}
