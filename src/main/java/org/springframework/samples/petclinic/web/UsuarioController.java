package org.springframework.samples.petclinic.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.service.UsuarioService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class UsuarioController {
	
	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping(value = { "/login" })
	public String typeOfUser(@RequestParam("username") String nickUsuario, @RequestParam("password") String contraseya) {
		String type = usuarioService.typeOfUser(nickUsuario, contraseya);
		return type;
	}
	
}
