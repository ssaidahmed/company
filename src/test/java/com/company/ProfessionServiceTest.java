package com.company;

import com.company.model.Profession;
import com.company.service.ProfessionService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class ProfessionServiceTest {
    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfessionService professionService;

    @BeforeEach
    void init() {
        Profession prof1 = new Profession("Бухгалтер", "Руководит отделом финансов");
        prof1.setId(1);
        when(professionService.get(1)).thenReturn(prof1);
    }
    @Test
    void deleteProfession() throws Exception {
        doNothing().when(professionService).delete(1);

        mockMvc.perform(delete("/api/profession/1"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(professionService, times(1)).delete(1);
    }

    @Test
    void save() throws Exception {
        Profession prof1 = new Profession("Бухгалтер", "Руководит отделом финансов");
        prof1.setId(1);
        when(professionService.save(Mockito.any(Profession.class))).thenReturn(prof1);

        mockMvc.perform(post("/api/profession/")
                .content(om.writeValueAsString(prof1))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Бухгалтер")))
                .andExpect(jsonPath("$.description", is("Руководит отделом финансов")));



        verify(professionService, times(1)).save(any(Profession.class));
    }

    @Test
    void update() throws Exception {
        Profession prof1 = professionService.get(1);
        prof1.setName("Киллер");
        prof1.setDescription("Устраняет конкурентов");
        when(professionService.update(Mockito.any(Profession.class))).thenReturn(prof1);

        mockMvc.perform(put("/api/profession/")
                .content(om.writeValueAsString(prof1))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Киллер")))
                .andExpect(jsonPath("$.description", is("Устраняет конкурентов")));



        verify(professionService, times(1)).update(any(Profession.class));

    }

    @Test
    void getAll() throws Exception{
        Profession prof1 = new Profession("Бухгалтер", "Руководит отделом финансов");
        prof1.setId(1);
        Profession prof2 = new Profession("Программист back-end", "Отвечает за разработку серверной части");
        prof2.setId(2);

        List<Profession> professions = Arrays.asList(prof1, prof2);
        when(professionService.getAll()).thenReturn(professions);
        mockMvc.perform(get("/api/profession/"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Бухгалтер")))
                .andExpect(jsonPath("$[0].description", is("Руководит отделом финансов")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Программист back-end")))
                .andExpect(jsonPath("$[1].description", is("Отвечает за разработку серверной части")));



        verify(professionService, times(1)).getAll();
        assertArrayEquals(professions.toArray(), professionService.getAll().toArray());
    }

    @Test
    void getProfession() throws Exception {
        Profession profession = professionService.get(1);

        mockMvc.perform(get("/api/profession/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Бухгалтер")))
                .andExpect(jsonPath("$.description", is("Руководит отделом финансов")));


        verify(professionService, times(2)).get(1);
        assertEquals("Бухгалтер", profession.getName());
    }
}