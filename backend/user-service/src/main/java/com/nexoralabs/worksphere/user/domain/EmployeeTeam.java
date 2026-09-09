package com.nexoralabs.worksphere.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee_team")
public class EmployeeTeam extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "team_id", nullable = false)
    private Team team;
    public Employee getEmployee() { return employee; }
    public void setEmployee(Employee employee) { this.employee = employee; }
    public Team getTeam() { return team; }
    public void setTeam(Team team) { this.team = team; }
}
