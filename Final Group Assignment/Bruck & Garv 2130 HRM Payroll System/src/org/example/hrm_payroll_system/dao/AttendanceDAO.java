package org.example.hrm_payroll_system.dao;

import java.util.List;

import org.example.hrm_payroll_system.models.Attendance;
import org.example.hrm_payroll_system.persistence.DataManager;

public class AttendanceDAO {
	public static List<Attendance> loadAll() {
        return DataManager.loadAttendance();
    }

    public static void saveAll(List<Attendance> list) {
        DataManager.saveAttendance(list);
    }

    public static void add(Attendance record) {
        var list = loadAll();
        list.add(record);
        saveAll(list);
    }

    public static void delete(Attendance record) {
        var list = loadAll();
        list.remove(record);
        saveAll(list);
    }
}
