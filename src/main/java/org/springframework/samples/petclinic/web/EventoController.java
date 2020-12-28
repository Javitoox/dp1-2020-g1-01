package org.springframework.samples.petclinic.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Evento;
import org.springframework.samples.petclinic.service.EventoService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("/events")
public class EventoController {
	
	private static final Logger log = LoggerFactory.logger(EventoController.class);
	
	private final EventoService eventoService;
	
	@Autowired
	public EventoController(EventoService eventoService) {
		this.eventoService = eventoService;
	}
	
	@GetMapping("/all")
	public ResponseEntity<?> getAllEvents(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute("type") == "profesor") {
			return ResponseEntity.ok(eventoService.getAll());
		}else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PutMapping("/update/{id}/{start}/{end}")
	public ResponseEntity<?> updateEvent(@PathVariable("id") Integer id, @PathVariable("start") String start,
			@PathVariable("end") String end, HttpServletRequest request){
		HttpSession session = request.getSession(false);
		if(session != null && session.getAttribute("type") == "profesor") {
			Evento evento = eventoService.updateDateEvent(id, start, end);
			if(evento == null) {
				return new ResponseEntity<>("Event not found", HttpStatus.NOT_FOUND);
			}else {
				log.info("Event update: "+evento);
				return ResponseEntity.ok(evento);
			}
		}else {
			log.info("No auth");
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

}
