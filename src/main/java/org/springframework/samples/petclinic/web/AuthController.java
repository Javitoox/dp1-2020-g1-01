package org.springframework.samples.petclinic.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class AuthController {
	
	@GetMapping(path = "/basicauth")
    public ResponseEntity<String> auth(Authentication authentication, HttpServletRequest request) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();
		String auth = userDetails.getAuthorities().iterator().next().getAuthority();
		
		log.info("Autenticación realizada-> Username: "+username+" Auth: "+auth);
        return ResponseEntity.ok(auth);
    }
	
}
