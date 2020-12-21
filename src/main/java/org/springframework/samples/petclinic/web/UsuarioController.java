package org.springframework.samples.petclinic.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class UsuarioController {
	
	private static final Logger log = LoggerFactory.logger(UsuarioController.class);
	
	private final UsuarioService usuarioService;
	
	@Autowired
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@GetMapping(value = { "/login" })
	public ResponseEntity<String> typeOfUser(@RequestParam("username") String nickUsuario, @RequestParam("password") String contraseya,
			HttpServletRequest request) {
		String type = usuarioService.typeOfUser(nickUsuario, contraseya);
		if(type != "Username not exist" && type != "Incorrect password") {
			HttpSession session = request.getSession(true);
			session.setAttribute("type", type);
			log.info(session.getAttribute("type"));
		}
		return ResponseEntity.ok(type);
	}
	
	@GetMapping(value = { "/auth" })
	public ResponseEntity<String> authentication(HttpServletRequest request) {
		String type = "usuario";
		HttpSession session = request.getSession(false);
		log.info(session == null);
		if(session != null && session.getAttribute("type") != null)
			type = (String) session.getAttribute("type");
		log.info(type);
		return ResponseEntity.ok(type);
	}
	
	@GetMapping(value = { "/logout" })
	public void logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session != null)
			session.invalidate();
	}
	
}
