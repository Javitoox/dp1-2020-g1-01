package org.springframework.samples.tea.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.tea.configuration.SecurityConfiguration;
import org.springframework.samples.tea.model.Profesor;
import org.springframework.samples.tea.service.TutorService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers= TutorController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
    excludeAutoConfiguration= SecurityConfiguration.class)
public class TutorControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    TutorService tutorService;

    @WithMockUser(value = "spring")
    @Test
    void testShouldObtainTeachersByGroup() throws Exception {
        List<String> correosProfesores = new ArrayList<>();
        Profesor p = new Profesor();
        p.setCorreoElectronicoUsuario("maribel@gmail.com");
        correosProfesores.add(p.getCorreoElectronicoUsuario());

        given(tutorService.getTeacherByGroup(any(String.class))).willReturn(correosProfesores);
        mockMvc.perform(get("/tutores/teacherByGroup/{nombreGrupo}", "group2")
            .with(csrf())).andDo(print())
            .andExpect(status().isOk());
    }

}

