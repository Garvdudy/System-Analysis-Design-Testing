package org.example.hrm_payroll_system.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.example.hrm_payroll_system.models.Department;
import org.example.hrm_payroll_system.models.Employee;
import org.example.hrm_payroll_system.models.Payroll;

public class DataManager {
	private static final String EMPLOYEE_FILE = "employees.dat";
	private static final String DEPARTMENT_FILE = "department.dat";
	private static final String PAYROLL_FILE = "payroll.dat";
	
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

}
