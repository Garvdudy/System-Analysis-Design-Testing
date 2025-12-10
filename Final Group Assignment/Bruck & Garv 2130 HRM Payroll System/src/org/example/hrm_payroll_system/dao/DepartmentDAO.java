package org.example.hrm_payroll_system.dao;

import java.util.List;

import org.example.hrm_payroll_system.models.Department;
import org.example.hrm_payroll_system.persistence.DataManager;

public class DepartmentDAO {
	public List<Department> getAllDepartments() {
        return DataManager.loadDepartments();
    }

    public void addDepartment(Department department) {
        List<Department> departments = DataManager.loadDepartments();
        departments.add(department);
        DataManager.saveDepartments(departments);
    }

    public void updateDepartment(Department department) {
        List<Department> departments = DataManager.loadDepartments();
        for (int i = 0; i < departments.size(); i++) {
            if (departments.get(i).getDepartmentId() == department.getDepartmentId()) {
                departments.set(i, department);
                break;
            }
        }
        DataManager.saveDepartments(departments);
    }

    public void deleteDepartment(int id) {
        List<Department> departments = DataManager.loadDepartments();
        departments.removeIf(d -> d.getDepartmentId() == id);
        DataManager.saveDepartments(departments);
    }
}