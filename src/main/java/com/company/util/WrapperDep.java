package com.company.util;

import org.springframework.lang.Nullable;

public class WrapperDep {

    private Integer id;
    private String name;
    private String description;
    private Integer parentDepartmentId;

    public WrapperDep() {
    }

    public WrapperDep(Integer id, String name, String description, Integer departmentId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.parentDepartmentId = departmentId;
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

    public Integer getParentDepartmentId() {
        return parentDepartmentId;
    }

    public void setParentDepartmentId(@Nullable Integer parentDepartmentId) {
        this.parentDepartmentId = parentDepartmentId;
    }
}
