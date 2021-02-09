package org.springframework.samples.tea.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.tea.model.AsignacionProfesor;
import org.springframework.samples.tea.model.AsignacionProfesorKey;
import org.springframework.samples.tea.model.Grupo;
import org.springframework.samples.tea.model.Profesor;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class TutorRepositoryTests {

    private static Grupo grupo;

    @Autowired
    protected ProfesorRepository profesorRepository;

    @Autowired
    protected TutorRepository tutorRepository;

    @Autowired
    protected GrupoRepository grupoRepository;

    @Autowired
    protected AsignacionProfesorRepository asignacionProfesorRepository;

    @BeforeEach
    void data() throws DataAccessException{
        Profesor p = new Profesor();
        p.setNickUsuario("marrambla");
        p.setDniUsuario("99876566W");
        p.setFechaNacimiento(LocalDate.of(2000, 06, 22));
        p.setNombreCompletoUsuario("Maria Dolores Garcia");
        p.setDireccionUsuario("Triana de Sevilla");
        p.setCorreoElectronicoUsuario("mariadeldolor@gmail.com");
        p.setContraseya("Pollito009");
        p.setNumTelefonoUsuario("698898989");
        profesorRepository.save(p);

        grupo = new Grupo();
        grupo.setNombreGrupo("group2");
        grupoRepository.save(grupo);

        AsignacionProfesor asignacion = new AsignacionProfesor();
        asignacion.setGrupo(grupo);
        asignacion.setProfesor(p);
        asignacion.setId(new AsignacionProfesorKey());
        asignacionProfesorRepository.save(asignacion);
    }

    @Test
    @Transactional
    void testGetTeacherByGroup() throws DataAccessException {
        List<String> correosProfesores = tutorRepository.getTeacherByGroup(grupo.getNombreGrupo());
        assertThat(correosProfesores.size()).isGreaterThan(0);
    }
}
