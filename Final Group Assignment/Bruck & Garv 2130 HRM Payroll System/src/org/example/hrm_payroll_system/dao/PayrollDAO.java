package org.example.hrm_payroll_system.dao;

import java.util.List;

import org.example.hrm_payroll_system.models.Payroll;
import org.example.hrm_payroll_system.persistence.DataManager;

public class PayrollDAO {
	public List<Payroll> getAllPayrolls() {
        return DataManager.loadPayrolls();
    }

    public void addPayroll(Payroll payroll) {
        List<Payroll> payrolls = DataManager.loadPayrolls();
        payrolls.add(payroll);
        DataManager.savePayrolls(payrolls);
    }

    public void updatePayroll(Payroll payroll) {
        List<Payroll> payrolls = DataManager.loadPayrolls();
        for (int i = 0; i < payrolls.size(); i++) {
            if (payrolls.get(i).getEmployeeId() == payroll.getEmployeeId()) {
                payrolls.set(i, payroll);
                break;
            }
        }
        DataManager.savePayrolls(payrolls);
    }

    public void deletePayroll(int id) {
        List<Payroll> payrolls = DataManager.loadPayrolls();
        payrolls.removeIf(p -> p.getEmployeeId() == id);
        DataManager.savePayrolls(payrolls);
    }
}