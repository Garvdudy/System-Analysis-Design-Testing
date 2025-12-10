package org.example.hrm_payroll_system.dao;

import java.util.List;

import org.example.hrm_payroll_system.models.Employee;
import org.example.hrm_payroll_system.persistence.DataManager;

public class EmployeeDAO {

    public List<Employee> getAllEmployees() {
        return DataManager.loadEmployees();
    }

    public void addEmployee(Employee employee) {
        List<Employee> employees = DataManager.loadEmployees();
        employees.add(employee);
        DataManager.saveEmployees(employees);
    }

    public void updateEmployee(Employee employee) {
        List<Employee> employees = DataManager.loadEmployees();
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == employee.getId()) {
                employees.set(i, employee);
                break;
            }
        }
        DataManager.saveEmployees(employees);
    }

    public void deleteEmployee(int id) {
        List<Employee> employees = DataManager.loadEmployees();
        employees.removeIf(e -> e.getId() == id);
        DataManager.saveEmployees(employees);
    }

    public Employee getEmployeeById(int id) {
        return DataManager.getEmployeeById(id);
    }
}