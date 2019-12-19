package com.company.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private String name;

    private String description;

    @ManyToOne()
    @JoinColumn(name = "parent_dep_id")
    @Nullable
    @JsonBackReference
    @JsonManagedReference
    private Department parentDepartment;

    @OneToMany(mappedBy="parentDepartment" )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private List<Department> departments;

    @OneToMany(targetEntity = Employee.class, mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)

    @JsonBackReference
    private List<Employee> employees;

    public Department() {
    }

    public Department(String name, String note) {
        this.name = name;
        this.description = note;
    }

    public boolean isNew() {
        return this.id == null;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Department getParentDepartment() {
        return parentDepartment;
    }

    public void setParentDepartment(@Nullable Department parentDepartment) {
        this.parentDepartment = parentDepartment;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        if(employees != null){
            employees.forEach(d -> d.setDepartment(this));
        }
        this.employees = employees;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Department that = (Department) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getParentDepartment(), that.getParentDepartment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getParentDepartment());
    }

    @Override
    public String toString() {
        return "\nDepartment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", parentDepartment=" + (parentDepartment /*== null ? "Нет родительского отдела" : parentDepartment.getName()*/)+
                ", employees=" + employees.size() +
                '}';
    }
}
