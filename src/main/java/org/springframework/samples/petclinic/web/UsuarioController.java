package org.springframework.samples.petclinic.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
	
	@GetMapping(value = { "/login" }, produces =  MediaType.APPLICATION_JSON_VALUE )
	public void typeOfUser(@RequestParam("username") String nickUsuario, @RequestParam("password") String contraseya,
			HttpServletResponse response) throws IOException {
		String result="http://localhost:3000";
		String type = usuarioService.typeOfUser(nickUsuario, contraseya);
		if(type.equals("Username not exist") || type.equals("Incorrect pasword")) {
			result += "/login?message="+type;
		}else {
			result += "?message="+type;
		}	
		response.sendRedirect(result);
	}
	
}
