package com.company.controllers;

import com.company.model.Department;
import com.company.model.Employee;
import com.company.model.Profession;
import com.company.service.DepartmentService;
import com.company.service.EmployeeService;
import com.company.service.ProfessionService;
import com.company.util.WrapperObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {

    private EmployeeService employeeService;
    private DepartmentService departmentService;
    private ProfessionService professionService;

    public EmployeeController(EmployeeService employeeService, DepartmentService departmentService, ProfessionService professionService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.professionService = professionService;
    }

    @RequestMapping(value = "employee", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getAllEmployees() {
        return employeeService.getAll();
    }
    @RequestMapping(value = "employee/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee getEmployee(@PathVariable Integer id) {
        return employeeService.get(id);
    }

    @RequestMapping(value = "employee", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> createEmployee(@RequestBody WrapperObject wrapperObject) throws URISyntaxException {
        try {

            Employee employee = new Employee();
            employee.setName(wrapperObject.getName());
            employee.setDescription(wrapperObject.getDescription());
            employee.setDepartment(departmentService.get(wrapperObject.getDepartmentId()));
            employee.setProfession(professionService.get(wrapperObject.getProfessionId()));
            Employee result = employeeService.save(employee);

            return ResponseEntity.created(new URI("/api/employee/" + result.getId())).body(result);
        } catch (EntityExistsException e) {
            return new ResponseEntity<Employee>(HttpStatus.CONFLICT);
        }
    }

    @RequestMapping(value = "employee", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> updateEmployee(@RequestBody WrapperObject wrapper) throws URISyntaxException {
        if (wrapper.getId() == null) {
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }

        try {
            Employee employee = new Employee();
            employee.setId(wrapper.getId());
            employee.setName(wrapper.getName());
            employee.setDescription(wrapper.getDescription());
            employee.setDepartment(departmentService.get(wrapper.getDepartmentId()));
            employee.setProfession(professionService.get(wrapper.getProfessionId()));


            Employee result = employeeService.update(employee);

            return ResponseEntity.created(new URI("/api/employee/" + result.getId())).body(result);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteEmployee(@PathVariable Integer id) {
        employeeService.delete(id);

        return ResponseEntity.ok().build();
    }

    public void createOrUpdate(){

    }

}
