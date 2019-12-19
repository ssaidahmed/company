package com.company;

import com.company.model.Department;
import com.company.model.Employee;
import com.company.model.Profession;
import com.company.repository.DepartmentRepository;
import com.company.repository.EmployeeRepository;
import com.company.repository.ProfessionRepository;
import com.company.service.DepartmentService;
import com.company.service.EmployeeService;
import com.company.service.ProfessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class CompanyApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CompanyApplication.class, args);

	}
	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private ProfessionRepository professionRepository;

	@Override
	public void run(String... args) throws Exception {
		Department dep1 = new Department("IT", "Отдел разработки");
		Department dep2 = new Department("Бухгалтерия", "Отдел финансов");
		Department dep3 = new Department("Администрация", "Управление компанией");
		List<Department> departmentList = Arrays.asList(dep1, dep2, dep3);



		Profession prof1 = new Profession("Бухгалтер", "Руководит отделом финансов");
		Profession prof2 = new Profession("Программист back-end", "Отвечает за разработку серверной части");
		Profession prof3 = new Profession("Программист front-end", "Отвечает за разработку клиентской части");
		Profession prof4 = new Profession("Директор", "Руководит компанией");

		List<Profession> professions = Arrays.asList(prof1, prof2, prof3, prof4);



		Employee employee1 = new Employee("Сидоров И.В", "Отдел финансов");
		employee1.setDepartment(dep2);
		employee1.setProfession(prof1);

		Employee employee2 = new Employee("Петров Г.П", "Хороший программист");
		employee2.setDepartment(dep1);
		employee2.setProfession(prof2);

		Employee employee3 = new Employee("Сидоров Г.П", "Плохой программист");
		employee3.setDepartment(dep1);
		employee3.setProfession(prof3);
		dep1.setEmployees(Arrays.asList(employee2, employee3));

		Employee employee4 = new Employee("Неруководов Г.П", "Главный человек в компании");
		employee4.setDepartment(dep3);
		employee4.setProfession(prof4);

		dep3.setEmployees(Collections.singletonList(employee4));
		dep2.setEmployees(Collections.singletonList(employee1));

		List<Employee> employees = Arrays.asList(employee1, employee2, employee3, employee4);



		departmentRepository.saveAll(departmentList);
		professionRepository.saveAll(professions);
		employeeRepository.saveAll(employees);



	}
}
