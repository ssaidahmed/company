package com.company;


import com.company.model.Department;
import com.company.model.Employee;
import com.company.model.Profession;
import com.company.service.EmployeeService;
import com.company.util.WrapperObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class EmployeeServiceTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @BeforeEach
    void init() {
        Department dep1 = new Department("IT", "Отдел разработки");
        dep1.setId(1);
        Profession prof1 = new Profession("Бухгалтер", "Руководит отделом финансов");
        prof1.setId(1);
        Employee employee1 = new Employee("Сидоров И.В", "Отдел финансов");
        employee1.setId(1);
        employee1.setDepartment(dep1);
        employee1.setProfession(prof1);

        when(employeeService.get(1)).thenReturn(employee1);
    }



    @Test
    void deleteById() throws Exception {
        doNothing().when(employeeService).delete(1);

        mockMvc.perform(delete("/api/employee/1"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(employeeService, times(1)).delete(1);
        assertEquals(0, employeeService.getAll().size());
    }

    @Test
    void saveEmployee() throws Exception{
        Department dep1 = new Department("IT", "Отдел разработки");
        dep1.setId(1);
        Profession prof1 = new Profession("Бухгалтер", "Руководит отделом финансов");
        prof1.setId(1);
        Employee employee1 = new Employee("Сидоров И.В", "Отдел финансов");

        employee1.setDepartment(dep1);
        employee1.setProfession(prof1);

        WrapperObject wrapperObject = new WrapperObject();

        wrapperObject.setName(employee1.getName());
        wrapperObject.setDescription(employee1.getDescription());
        wrapperObject.setDepartmentId(1);
        wrapperObject.setProfessionId(1);
        when(employeeService.save(Mockito.any(Employee.class))).thenReturn(employee1);

        mockMvc.perform(post("/api/employee/")
                .content(om.writeValueAsString(wrapperObject))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", nullValue()))
                .andExpect(jsonPath("$.name", is("Сидоров И.В")))
                .andExpect(jsonPath("$.description", is("Отдел финансов")))
                .andExpect(jsonPath("$.department.id", is(1)))
                .andExpect(jsonPath("$.profession.id", is(1)));


        verify(employeeService, times(1)).save(Mockito.any(Employee.class));

    }

    @Test
    void updateEmployee() throws Exception{
        Employee employee1 = employeeService.get(1);
        WrapperObject wrapperObject = new WrapperObject();
        wrapperObject.setId(employee1.getId());
        employee1.setName("Кукушкин К.П.");
        wrapperObject.setName(employee1.getName());
        employee1.setDescription("Хороший человек");
        wrapperObject.setDescription(employee1.getDescription());
        wrapperObject.setDepartmentId(1);
        wrapperObject.setProfessionId(1);

        when(employeeService.update(Mockito.any(Employee.class))).thenReturn(employee1);

        mockMvc.perform(put("/api/employee/")
                .content(om.writeValueAsString(wrapperObject))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Кукушкин К.П.")))
                .andExpect(jsonPath("$.description", is("Хороший человек")))
                .andExpect(jsonPath("$.department.id", is(1)))
                .andExpect(jsonPath("$.profession.id", is(1)));


        verify(employeeService, times(1)).update(Mockito.any(Employee.class));
    }

    @Test
    void getAll() throws Exception {
        Department dep1 = new Department("IT", "Отдел разработки");
        dep1.setId(1);
        Department dep2 = new Department("Бухгалтерия", "Отдел финансов");
        dep2.setId(2);

        Profession prof1 = new Profession("Бухгалтер", "Руководит отделом финансов");
        prof1.setId(1);
        Profession prof2 = new Profession("Программист back-end", "Отвечает за разработку серверной части");
        prof2.setId(2);

        Employee employee1 = new Employee("Сидоров И.В", "Отдел финансов");
        employee1.setId(1);
        employee1.setDepartment(dep1);
        employee1.setProfession(prof1);

        Employee employee2 = new Employee("Петров Г.П", "Хороший программист");
        employee2.setId(2);
        employee2.setDepartment(dep2);
        employee2.setProfession(prof2);

        List<Employee> employees = Arrays.asList(employee1, employee2);
        when(employeeService.getAll()).thenReturn(employees);
        mockMvc.perform(get("/api/employee/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Сидоров И.В")))
                .andExpect(jsonPath("$[0].description", is("Отдел финансов")))
                .andExpect(jsonPath("$[0].department.id", is(1)))
                .andExpect(jsonPath("$[0].profession.id", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Петров Г.П")))
                .andExpect(jsonPath("$[1].description", is("Хороший программист")))
                .andExpect(jsonPath("$[1].department.id", is(2)))
                .andExpect(jsonPath("$[1].profession.id", is(2)));


        verify(employeeService, times(1)).getAll();
        assertArrayEquals(employees.toArray(), employeeService.getAll().toArray());

    }

    @Test
    void getEmployee() throws Exception {
        Employee employee = employeeService.get(1);


        mockMvc.perform(get("/api/employee/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Сидоров И.В")))
                .andExpect(jsonPath("$.description", is( "Отдел финансов")))
                .andExpect(jsonPath("$.department.id", is(1)))
                .andExpect(jsonPath("$.profession.id", is(1)));


        verify(employeeService, times(2)).get(1);

        assertEquals("Сидоров И.В", employee.getName());
    }
}