package com.ofentse.leavemanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "leave_types")
public class LeaveType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String name;

    private int daysPerYear;

    public LeaveType() {}

    public Long getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getDaysPerYear() { return daysPerYear; }
    public void setDaysPerYear(int daysPerYear) { this.daysPerYear = daysPerYear; }
}