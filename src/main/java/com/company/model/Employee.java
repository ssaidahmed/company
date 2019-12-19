package com.company.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private String name;

    private String description;

    @ManyToOne( cascade = {CascadeType.DETACH,  CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "department_id")

    @JsonManagedReference
    private Department department;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToOne(cascade = {CascadeType.DETACH,  CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "profession_id")
    private Profession profession;

    public Employee() {
    }

    public Employee(String name, String note) {
        this.name = name;
        this.description = note;
    }

    public boolean isNew() {
        return this.id == null;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(getId(), employee.getId()) &&
                Objects.equals(getName(), employee.getName()) &&
                Objects.equals(getDescription(), employee.getDescription()) &&
                Objects.equals(getDepartment(), employee.getDepartment()) &&
                Objects.equals(getProfession(), employee.getProfession());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getDepartment(), getProfession());
    }

    @Override
    public String toString() {
        return "\nEmployee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", department=" + department +
                ", profession=" + profession +
                '}';
    }
}
