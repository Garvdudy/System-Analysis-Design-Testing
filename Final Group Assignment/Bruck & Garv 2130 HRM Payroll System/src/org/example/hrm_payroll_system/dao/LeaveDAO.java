package org.example.hrm_payroll_system.dao;

import java.util.List;

import org.example.hrm_payroll_system.models.Leave;
import org.example.hrm_payroll_system.persistence.DataManager;

public class LeaveDAO {
	public static List<Leave> loadAll() {
        return DataManager.loadLeaves();
    }

    public static void saveAll(List<Leave> list) {
        DataManager.saveLeaves(list);
    }

    public static void add(Leave leave) {
        var list = loadAll();
        list.add(leave);
        saveAll(list);
    }

    public static void update(Leave updated) {
        var list = loadAll();
        for (int i = 0; i < list.size(); i++) {
            Leave l = list.get(i);
            if (l.getEmployeeId() == updated.getEmployeeId()
                && l.getStartDate().equals(updated.getStartDate())
                && l.getEndDate().equals(updated.getEndDate())) {
                list.set(i, updated);
                break;
            }
        }
        saveAll(list);
    }
    
    
}