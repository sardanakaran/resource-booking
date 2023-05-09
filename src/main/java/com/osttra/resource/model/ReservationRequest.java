package com.osttra.resource.model;

import java.util.Date;


public class ReservationRequest {

    private String employeeEmail;
    private String employeeName;
    private Long resourceId;
    private Date date;


    public ReservationRequest() {
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ReservationRequest(String employeeEmail, String employeeName, Long resourceId, Date date) {
        this.employeeEmail = employeeEmail;
        this.employeeName = employeeName;
        this.resourceId = resourceId;
        this.date = date;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public Date getDate() {
        return date;
    }
}

