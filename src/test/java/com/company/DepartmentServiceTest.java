package com.company;

import com.company.model.Department;
import com.company.service.DepartmentService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class DepartmentServiceTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    @BeforeEach
    void init() {
        Department dep = new Department("IT", "Отдел разработки");
        dep.setId(1);
        when(departmentService.get(1)).thenReturn(dep);
    }

    @Test
    void deleteById() throws Exception {
        doNothing().when(departmentService).delete(1);

        mockMvc.perform(delete("/api/department/1"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(departmentService, times(1)).delete(1);
    }

    @Test
    void save() throws Exception {
        Department dep = new Department("IT", "Отдел разработки");
        dep.setId(1);
        when(departmentService.save(Mockito.any(Department.class))).thenReturn(dep);

        mockMvc.perform(post("/api/department/")
                .content(om.writeValueAsString(dep))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("IT")))
                .andExpect(jsonPath("$.description", is("Отдел разработки")))
                .andExpect(jsonPath("$.parentDepartment", nullValue()));


        verify(departmentService, times(1)).save(any(Department.class));
    }

    @Test
    void update() throws Exception {
        Department dep = departmentService.get(1);
        dep.setName("ИТ профсоюз");
        dep.setDescription("Сплоченный");
        when(departmentService.update(Mockito.any(Department.class))).thenReturn(dep);

        mockMvc.perform(put("/api/department/")
                .content(om.writeValueAsString(dep))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("ИТ профсоюз")))
                .andExpect(jsonPath("$.description", is("Сплоченный")))
                .andExpect(jsonPath("$.parentDepartment", nullValue()));


        verify(departmentService, times(1)).update(any(Department.class));


        assertEquals(dep, departmentService.get(1));
    }

    @Test
    void getAllDep() throws Exception {
        Department dep1 = new Department("IT", "Отдел разработки");
        dep1.setId(1);
        Department dep2 = new Department("Бухгалтерия", "Отдел финансов");
        dep2.setId(2);

        List<Department> departments = Arrays.asList(dep1, dep2);
        when(departmentService.getAll()).thenReturn(departments);
        mockMvc.perform(get("/api/department/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("IT")))
                .andExpect(jsonPath("$[0].description", is("Отдел разработки")))
                .andExpect(jsonPath("$[0].parentDepartment", nullValue()))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Бухгалтерия")))
                .andExpect(jsonPath("$[1].description", is("Отдел финансов")))
                .andExpect(jsonPath("$[1].parentDepartment", nullValue()));


        verify(departmentService, times(1)).getAll();
        assertArrayEquals(departments.toArray(), departmentService.getAll().toArray());

    }

    @Test
    void getDep() throws Exception {
        Department department = departmentService.get(1);
        mockMvc.perform(get("/api/department/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("IT")))
                .andExpect(jsonPath("$.description", is( "Отдел разработки")))
                .andExpect(jsonPath("$.parentDepartment", nullValue()));



        verify(departmentService, times(2)).get(1);
        assertEquals("IT", department.getName());
    }
    @Test
    void getDepByIdNullDep() {
        Department dep = departmentService.get(1000);
        assertNull(dep);
    }
}