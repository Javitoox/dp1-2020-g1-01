package org.springframework.samples.petclinic.web;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Curso;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.GrupoService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin("*")
@RequestMapping("/grupos")
public class GrupoController {
	

	//private static final String vista = "grupos/list";
	//private static final String VIEWS_GROUPS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";
	private final GrupoService grupoService;
	private final AlumnoService alumnoService;
	private final static Logger LOGGER = Logger.getLogger("bitacora.subnivel.control");
	
	@Autowired
	public GrupoController(GrupoService grupoService, AlumnoService alumnoService) {
		this.grupoService = grupoService;
		this.alumnoService = alumnoService;
	}
	
	@GetMapping("/all")
	public List<Grupo> listaGrupos() {
		return grupoService.findAll().stream().collect(Collectors.toList());
		
	}
	
	@GetMapping("/{curso}")
	public List<Grupo> listaGruposPorCurso(@PathVariable("curso") String curso) {
		return grupoService.getGrupo(curso);		
	}
	
	/*GESTIÓN DE GRUPOS*/
	
	
	
	/*FIN GESTIÓN DE GRUPOS*/
	
	@GetMapping(value="/grupos?grupo={nombreGrupo}")	
	public List<Alumno> getPersonasByNameOfGroup(@RequestParam("nombreGrupo") String nombreGrupo){
        return alumnoService.getStudentsPerGroup(nombreGrupo);
    }
	
	@PostMapping(value = "/edit?grupo=nombreGrupo&nick_usuario=nick_usu")
    public void processUpdateStudentGroup(@Valid Alumno alumno, BindingResult result,
            @RequestParam("nick_usu") String nick_usuario, @RequestParam("nombreGrupo") String nombreGrupo) {
        if (result.hasErrors()) {
            LOGGER.log(Level.INFO, "Esto no funciona :(");
        }
        else {
            Curso curso= alumnoService.getAlumno(nick_usuario).getGrupos().getCursos();
            Grupo grupo = new Grupo();
            grupo.setNombreGrupo(nombreGrupo);
            grupo.setCursos(curso);
            alumno.setGrupos(grupo);
            this.alumnoService.saveAlumno(alumno);
        }
    }
	
	
	
	

}
