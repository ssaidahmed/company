package com.company.repository;

import com.company.model.Department;
import com.company.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;



@Transactional(readOnly = true)
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {


    @Transactional
    @Modifying
    @Query("DELETE FROM Employee e WHERE e.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT e FROM Employee e LEFT JOIN FETCH e.department WHERE e.id=:id" )
    Department detDepartmentEmp(@Param("id") int id);
}
