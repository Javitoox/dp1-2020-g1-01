package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.GrupoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/grupos")
public class GrupoController {
	

	//private static final String vista = "grupos/list";
	//private static final String VIEWS_GROUPS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";
	private final GrupoService grupoService;
	private final AlumnoService alumnoService;
	
	@Autowired
	public GrupoController(GrupoService grupoService, AlumnoService alumnoService) {
		this.grupoService = grupoService;
		this.alumnoService = alumnoService;
	}
	
	@GetMapping //(value = {"/lista"}) //probando 1 so
	public String listaGrupos(ModelMap model) {
		model.addAttribute("grupos", grupoService.findAll());
		return "grupos/lista";
	}
	
	@GetMapping(value="/grupos/{curso}/lista")
	public String listaGruposPorCurso(@PathVariable("curso") String curso ,ModelMap model) {
		model.addAttribute("gruposCurso", grupoService.getGrupo(curso));
		return "grupos/curso";
	}
	
	@GetMapping(value="/grupos?grupo={nombreGrupo}")
    public String getPersonasByNameOfGroup(@RequestParam("nombreGrupo") String nombreGrupo, ModelMap model){
    	model.addAttribute("alumnosPorGrupo", alumnoService.getStudentsPerGroup(nombreGrupo));
        return "grupos/alumnos";
    }
	
	

}
