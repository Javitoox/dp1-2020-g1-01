package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UsuarioController {
	
	private final UsuarioService usuarioService;
	
	@Autowired
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
//	@GetMapping(value = { "/login" })
//	public ResponseEntity<String> typeOfUser(@RequestParam("username") String nickUsuario, @RequestParam("password") String contraseya,
//			HttpServletRequest request) {
//		String type = usuarioService.typeOfUser(nickUsuario, contraseya);
//		if(type != "Username not exist" && type != "Incorrect password") {
//			HttpSession session = request.getSession(true);
//			session.setAttribute("type", type);
//			session.setAttribute("nickUsuario", nickUsuario);
//			log.info(nickUsuario+ " login: "+session.getAttribute("type"));
//		}
//		return ResponseEntity.ok(type);
//	}
//	
//	@GetMapping(value = { "/auth" })
//	public ResponseEntity<String> authentication(HttpServletRequest request) {
//		String type = "usuario";
//		HttpSession session = request.getSession(false);
//		if(session != null && session.getAttribute("type") != null)
//			type = (String) session.getAttribute("type");
//		log.info("Auth: "+type);
//		return ResponseEntity.ok(type);
//	}
//	
//	@DeleteMapping(value = { "/logout" })
//	public ResponseEntity<String> logout(HttpServletRequest request) {
//		HttpSession session = request.getSession(false);
//		if(session != null)
//			session.invalidate();
//		return ResponseEntity.ok("Succesfull logout");
//	}
	
}
