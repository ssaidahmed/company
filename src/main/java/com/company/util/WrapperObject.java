package com.company.util;

import java.io.Serializable;

public class WrapperObject implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;

    private String name;

    private String description;

    private Integer departmentId;

    private Integer professionId;

    public WrapperObject() {
    }



    public WrapperObject(Integer id, String name, String description, Integer departmentId, Integer professionId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.departmentId = departmentId;
        this.professionId = professionId;
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

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getProfessionId() {
        return professionId;
    }

    public void setProfessionId(Integer professionId) {
        this.professionId = professionId;
    }

}
