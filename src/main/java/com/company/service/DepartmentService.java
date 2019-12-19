package com.company.service;

import com.company.model.Department;
import com.company.repository.DepartmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class DepartmentService {

    private DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }


    public void delete(int id){
        departmentRepository.delete(id);
    }

    @Transactional
    public Department save(Department department){
        if(!department.isNew() && departmentRepository.existsById(department.getId())){
            throw new EntityExistsException("Уже существует объект с таким идентификатором");
        }
        return departmentRepository.save(department);
    }
    @Transactional
    public Department update(Department department){
        if(!department.isNew() && !departmentRepository.existsById(department.getId())){
            throw new EntityNotFoundException("В базе данных нет объекта с таким идентификатором");
        }
        return departmentRepository.save(department);
    }

    public List<Department> getAll(){
        return departmentRepository.findAll();
    }

    public Department get(Integer id){
        if(id == null){
            return null;
        }
        return departmentRepository.findById(id).orElse(null);
    }




}
