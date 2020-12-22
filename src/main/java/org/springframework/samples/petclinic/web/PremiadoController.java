package org.springframework.samples.petclinic.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Premiado;
import org.springframework.samples.petclinic.service.PremiadoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/premiados")
@CrossOrigin("*")
@RestController
public class PremiadoController {
	
	@Autowired
	PremiadoService premiadoService;
	
	@GetMapping("/{fechaWall}")
	public List<Premiado> premiadosPorFecha(@PathVariable("fechaWall") String fechaWall){
		return premiadoService.premiadosPorFecha(fechaWall);
	}
	
	@GetMapping("/ultimaSemana")
	public String obtenerUltimaSemana() {
		return premiadoService.obtenerUltimaSemana();
	}
}
