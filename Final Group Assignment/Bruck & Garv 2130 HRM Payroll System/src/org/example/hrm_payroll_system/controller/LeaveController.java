package org.example.hrm_payroll_system.controller;

import java.time.LocalDate;
import java.util.List;

import org.example.hrm_payroll_system.dao.LeaveDAO;
import org.example.hrm_payroll_system.models.Leave;
import org.example.hrm_payroll_system.models.Role;
import org.example.hrm_payroll_system.models.User;
import org.example.hrm_payroll_system.persistence.DataManager;
import org.example.hrm_payroll_system.services.LeaveService;
import org.example.hrm_payroll_system.utils.AlertUtil;
import org.example.hrm_payroll_system.utils.AppSession;
import org.example.hrm_payroll_system.view.LeaveView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class LeaveController {
	private LeaveView view;
    private LeaveService leaveService;
    private ObservableList<Leave> leavesList;
    private MainController mainController;

    public LeaveController(MainController mainController) {
        this.mainController = mainController;
        this.view = new LeaveView();
        this.leaveService = new LeaveService();
        setupActions();
        refreshList(); // Initialize list immediately
    }

    private void setupActions() {
        User currentUser = AppSession.getInstance().getCurrentUser();

        // Employee: Request leave
        if (currentUser.getRole() == Role.EMPLOYEE) {
            view.getRequestBtn().setOnAction(e -> requestLeave(currentUser));
        } else {
            view.getRequestBtn().setDisable(true); // HR/Admin cannot request leave
        }

        // HR/Admin: Approve/Reject leave
        if (currentUser.getRole() != Role.EMPLOYEE) {
            view.getApproveBtn().setOnAction(e -> updateSelectedLeave(currentUser, true));
            view.getRejectBtn().setOnAction(e -> updateSelectedLeave(currentUser, false));
        } else {
            view.getApproveBtn().setDisable(true);
            view.getRejectBtn().setDisable(true);
        }

        // Back button
        view.getBackBtn().setOnAction(e -> mainController.showMainView());
    }

    private void requestLeave(User currentUser) {
        LocalDate start = view.getStartPicker().getValue();
        LocalDate end = view.getEndPicker().getValue();
        String type = view.getTypeField().getText().trim();

        if (start == null || end == null || type.isEmpty()) {
            AlertUtil.showError("Please fill in all fields.");
            return;
        }

        if (end.isBefore(start)) {
            AlertUtil.showError("End date cannot be before start date.");
            return;
        }

        leaveService.requestLeave(currentUser, start, end, type);
        refreshList();
        AlertUtil.showInfo("Leave request submitted successfully!");
    }

    private void updateSelectedLeave(User currentUser, boolean approve) {
        Leave selected = view.getListView().getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertUtil.showError("Select a leave first.");
            return;
        }

        if (approve) {
            leaveService.approveLeave(currentUser, selected);
        } else {
            leaveService.rejectLeave(currentUser, selected);
        }

        refreshList();
        AlertUtil.showInfo("Leave updated successfully!");
    }

    private void refreshList() {
        User currentUser = AppSession.getInstance().getCurrentUser();
        List<Leave> data = leaveService.getLeavesForUser(currentUser);

        if (leavesList == null) {
            leavesList = FXCollections.observableArrayList(data);
        } else {
            leavesList.setAll(data);
        }

        view.getListView().setItems(leavesList);
        
        long leavesTaken = DataManager.loadLeaves()
                .stream()
                .filter(l -> l.getEmployeeId() == currentUser.getEmployeeId())
                .count();
        view.getLeavesLabel().setText("Leaves Taken: " + leavesTaken);
    }

    public void show(Stage stage) {
        view.show(stage, leavesList, AppSession.getInstance().getCurrentUser());
    }
}