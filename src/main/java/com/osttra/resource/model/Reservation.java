package com.osttra.resource.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Reservation", uniqueConstraints = {@UniqueConstraint(columnNames = {"resource_id", "date", "employee_email"})})
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Column(name = "employee_email")
    private String employeeEmail;

    @Column(name = "employee_name")
    private String employeeName;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private Resource resource;

    public Reservation() {
    }

    public Reservation(Date date, String employeeEmailId, String employeeName, Resource resource) {
        this.date = date;
        this.employeeEmail = employeeEmailId;
        this.employeeName = employeeName;
        this.resource = resource;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public Resource getResource() {
        return resource;
    }

    public long getId() {
        return id;
    }
}
