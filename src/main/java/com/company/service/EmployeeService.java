package com.company.service;

import com.company.model.Employee;
import com.company.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    public void delete(int id){
        employeeRepository.delete(id);
    }

    @Transactional
    public Employee save(Employee employee){
        if(!employee.isNew() && employeeRepository.existsById(employee.getId())){
            throw new EntityExistsException("Уже существует объект с таким идентификатором");
        }
        return employeeRepository.save(employee);
    }
    @Transactional
    public Employee update(Employee employee){
        if(!employee.isNew() && !employeeRepository.existsById(employee.getId())){
            throw new EntityNotFoundException("В базе данных нет объекта с таким идентификатором");
        }
        return employeeRepository.save(employee);
    }

    public List<Employee> getAll(){
        return employeeRepository.findAll();
    }

    public Employee get(int id){
        return employeeRepository.findById(id).orElse(null);
    }

}
