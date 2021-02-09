package org.springframework.samples.tea.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.tea.model.*;
import org.springframework.samples.tea.model.Alumno;
import org.springframework.samples.tea.model.Evento;
import org.springframework.samples.tea.model.Grupo;
import org.springframework.samples.tea.model.Inscripcion;
import org.springframework.samples.tea.repository.AlumnoRepository;
import org.springframework.samples.tea.repository.GrupoRepository;
import org.springframework.stereotype.Service;

@Service
public class AlumnoService {

	private AlumnoRepository alumnoRepository;
	private GrupoRepository grupoRepository;
	private InscripcionService inscripcionService;


	@Autowired
	public AlumnoService(AlumnoRepository alumnoRepository,GrupoRepository grupoRepository, InscripcionService inscripcionService) {
		this.alumnoRepository = alumnoRepository;
		this.grupoRepository = grupoRepository;
		this.inscripcionService = inscripcionService;
		this.grupoRepository = grupoRepository;
	}

	public List<Alumno> getStudentsPerGroup(String nombreGrupo) {
        return alumnoRepository.findByGroup(nombreGrupo);
    }

	public List<Alumno> getAllAlumnos() {
		return alumnoRepository.findStudents();
	}

	public Alumno getAlumno(String nickUsuario) {
		return alumnoRepository.findById(nickUsuario).orElse(null);
	}

	public Alumno getAlumnoByIdOrNif(String nickUsuario, String nif) {
		return alumnoRepository.findByNickAndNif(nickUsuario, nif);
	}

	@Transactional
	public void deleteStudents(Alumno alumno) throws DataAccessException {
		alumno.setFechaMatriculacion(null);
		alumno.setFechaBaja(LocalDate.now());
		alumno.setGrupos(null);
		alumno.setNumTareasEntregadas(0);
		alumnoRepository.save(alumno);
	}

	@Transactional
	public void deleteStudent(String id) throws DataAccessException {
		alumnoRepository.deleteById(id);
	}

	@Transactional
	public Alumno saveAlumno(Alumno alumno) throws DataAccessException {
		if (alumno.getGrupos() == null) {
            Alumno a = alumnoRepository.findByNickAndNif(alumno.getNickUsuario(), alumno.getDniUsuario());
            alumno.setGrupos(a.getGrupos());
        }
			if(alumno.getTutores()==null) {
            Alumno a = alumnoRepository.findByNickAndNif(alumno.getNickUsuario(), alumno.getDniUsuario());
            alumno.setTutores(a.getTutores());
        }
		   return  alumnoRepository.save(alumno);
	 }

	@Transactional
    public Alumno saveAlumn(Alumno alumno) throws DataAccessException {
        return alumnoRepository.save(alumno);
    }

	@Transactional
    public void saveAlumnAsign(Alumno alumno, String nombreGrupo) throws DataAccessException {
        Grupo g = grupoRepository.findById(nombreGrupo).get();
        alumno.setGrupos(g);
        alumnoRepository.save(alumno);
    }

    public List<Alumno> getStudentsByCourse(String cursoDeIngles){
        return alumnoRepository.findStudentsByCourse(cursoDeIngles);
    }

    public List<String> getStudentsWithNoGroups(){
        return alumnoRepository.findSudentsWithNoGroups();
    }

    public List<Alumno>getAllMyStudents(String nickTutor){
    	return alumnoRepository.findStudentsByTutor(nickTutor);
    }

    public List<String> getStudentsToDelete(){
    	return alumnoRepository.findStudentsAbleToDelete();
    }

    public void asignInscripcionesAlumnos(Evento evento, String tipoCurso, String type) {
    	List<Alumno> alumnosCurso = getStudentsByCourse(tipoCurso);
		Integer idInscripcion = inscripcionService.lastId()+1;
		for(Alumno a: alumnosCurso) {
			Inscripcion i = new Inscripcion();
			i.setId(idInscripcion);
			i.setFecha(LocalDate.now());
			if (type.equals("Internal"))
				i.setRegistrado(true);
			else
				i.setRegistrado(false);
			i.setEvento(evento);
			i.setAlumno(a);
			inscripcionService.saveInscripcion(i);
			idInscripcion++;
		}
	}


}


