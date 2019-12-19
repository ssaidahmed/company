package com.company.controllers;

import com.company.model.Department;
import com.company.service.DepartmentService;
import com.company.util.WrapperDep;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class DepartmentController {

    private DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @RequestMapping(value = "department", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Department> getAll() {
        return departmentService.getAll();
    }

    @RequestMapping(value = "department/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Department getEmployee(@PathVariable Integer id) {
        return departmentService.get(id);
    }

    @RequestMapping(value = "department", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Department> create(@RequestBody WrapperDep wrapperDep) throws URISyntaxException {
        try {
            Department department = new Department();
            department.setName(wrapperDep.getName());
            department.setDescription(wrapperDep.getDescription());


            department.setParentDepartment(departmentService.get(wrapperDep.getParentDepartmentId()));

            Department result = departmentService.save(department);
            return ResponseEntity.created(new URI("/api/department/" + result.getId())).body(result);
        } catch (EntityExistsException e) {
            return new ResponseEntity<Department>(HttpStatus.CONFLICT);
        }
    }



    @RequestMapping(value = "department", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Department> update(@RequestBody WrapperDep wrapperDep) throws URISyntaxException {
        if (wrapperDep.getId() == null) {
            return new ResponseEntity<Department>(HttpStatus.NOT_FOUND);
        }

        try {

            Department department = new Department();
            department.setId(wrapperDep.getId());
            department.setName(wrapperDep.getName());
            department.setDescription(wrapperDep.getDescription());

            if (wrapperDep.getParentDepartmentId() != null) {
                department.setParentDepartment(departmentService.get(wrapperDep.getParentDepartmentId()));
            }

            Department result = departmentService.update(department);

            return ResponseEntity.created(new URI("/api/department/" + result.getId())).body(result);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<Department>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/department/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        departmentService.delete(id);

        return ResponseEntity.ok().build();
    }

}
