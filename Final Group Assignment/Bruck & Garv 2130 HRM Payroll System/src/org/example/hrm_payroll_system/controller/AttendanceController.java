package org.example.hrm_payroll_system.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.example.hrm_payroll_system.dao.AttendanceDAO;
import org.example.hrm_payroll_system.models.Attendance;
import org.example.hrm_payroll_system.models.User;
import org.example.hrm_payroll_system.services.AttendanceService;
import org.example.hrm_payroll_system.utils.AlertUtil;
import org.example.hrm_payroll_system.utils.AppSession;
import org.example.hrm_payroll_system.view.AttendanceView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class AttendanceController {
	private AttendanceView view;
    private AttendanceService attendanceService;
    private MainController mainController;
    private ObservableList<Attendance> attendanceList;

    public AttendanceController(MainController mainController) {
        this.mainController = mainController;
        this.view = new AttendanceView();
        this.attendanceService = new AttendanceService();

        setupActions();
    }

    private void setupActions() {
        User currentUser = AppSession.getInstance().getCurrentUser();

        view.getClockInBtn().setOnAction(e -> {
            try {
                String msg = attendanceService.clockIn(currentUser);
                AlertUtil.showInfo(msg);
                refreshList();
            } catch (Exception ex) {
                AlertUtil.showError(ex.getMessage());
            }
        });

        view.getClockOutBtn().setOnAction(e -> {
            try {
                String msg = attendanceService.clockOut(currentUser);
                AlertUtil.showInfo(msg);
                refreshList();
            } catch (Exception ex) {
                AlertUtil.showError(ex.getMessage());
            }
        });

        view.getBackBtn().setOnAction(e -> mainController.showMainView());
    }

    private void refreshList() {
        User currentUser = AppSession.getInstance().getCurrentUser();
        List<Attendance> data = attendanceService.getAttendanceForUser(currentUser);
        if (attendanceList == null) {
            attendanceList = FXCollections.observableArrayList(data);
        } else {
            attendanceList.setAll(data);
        }
        view.getListView().setItems(attendanceList);
    }

    public void show(Stage stage) {
        refreshList();
        view.show(stage);
    }
}