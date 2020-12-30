package org.springframework.samples.petclinic.web;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.BodyPremiado;
import org.springframework.samples.petclinic.model.Premiado;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.PremiadoService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/premiados")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@Slf4j
public class PremiadoController {
	
	private PremiadoService premiadoService;
	
	private AlumnoService alumnoService;
	
	@Autowired
	public PremiadoController(PremiadoService premiadoService, AlumnoService alumnoService) {
		this.premiadoService = premiadoService;
		this.alumnoService = alumnoService; 
	}
	
	@GetMapping("/{fechaWall}")
	public ResponseEntity<?> premiadosPorFecha(@PathVariable("fechaWall") String fechaWall, HttpServletRequest request){
    	HttpSession session = request.getSession(false);
    	log.info("Has iniciado sesion como: "+ session.getAttribute("type"));
    	if(session != null && (session.getAttribute("type") == "tutor" || session.getAttribute("type") == "alumno" || session.getAttribute("type") == "profesor")) {
    		List<Premiado> premiados = premiadoService.premiadosPorFecha(fechaWall);
    		return ResponseEntity.ok(premiados);
    	}else {
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    	}
	}
	

	@GetMapping("/ultimaSemana")
	public String obtenerUltimaSemana() {
		return premiadoService.obtenerUltimaSemana();
	}
	
	
	@PostMapping(value="/añadirPremiado/{fechaWall}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) 
	public ResponseEntity<?>añadirPremiado(@PathVariable("fechaWall") String fechaWall,
			@Valid @ModelAttribute BodyPremiado body,  BindingResult result, HttpServletRequest request){
		
		if(result.hasErrors()) {
			return new ResponseEntity<>(result.getFieldErrors(), HttpStatus.NON_AUTHORITATIVE_INFORMATION);
		}
		
		HttpSession session = request.getSession(false);
		
		System.out.println("Imagen con tamaño: "+body.getPhoto().getSize());

		log.info("Has iniciado sesion como: "+ session.getAttribute("type"));
		 if(session != null && session.getAttribute("type") == "profesor") {
			 String nickUsuario = body.getNickUsuario();
			 String description = body.getDescription();
			 MultipartFile file = body.getPhoto();
			 
			 Alumno alumno = alumnoService.getAlumno(nickUsuario);
			 if(alumno != null) {
				 premiadoService.insertarPremiado(nickUsuario, fechaWall, description, file);
				 return new ResponseEntity<>(HttpStatus.CREATED);
			 }else {
				 return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			 }
		 }else { 
			 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		 }
		 
   }
	
	@PutMapping(value="/editarPremiado", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> editarPremiado(@RequestParam(value = "photo", required = false) MultipartFile file, @RequestParam("id") Integer id, @RequestParam("description") String descripcion,
			 @RequestParam("nickUsuario") String nickUsuario, HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);

		log.info("Has iniciado sesion como: "+ session.getAttribute("type"));
		if(session != null && session.getAttribute("type") == "profesor") {
			log.info("Editanto el premiado cuya id es : "+id);
			
			if(file !=null) {
				Path directorioImagenes = Paths.get("src//main//resources//static//frontend//public/photosWall");
				String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
				
				try {
					byte[] bytes = file.getBytes();
					Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + nickUsuario + ".jpg");
					Files.write(rutaCompleta, bytes);
					
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			
			if(!descripcion.isEmpty()) {
				log.info("Changing description: "+descripcion);
				premiadoService.editarPremiado(id, descripcion);
			}
			return new ResponseEntity<>(HttpStatus.CREATED);
		}else {
			 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}
		
	}
	
	@DeleteMapping(value="/borrarPremiado/{id}")
	public ResponseEntity<?> deletePremiado(@PathVariable("id") Integer id, HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);

		log.info("Has iniciado sesion como: "+ session.getAttribute("type"));
		if(session != null && session.getAttribute("type") == "profesor") {
			premiadoService.deletePremiadoById(id);
			return ResponseEntity.ok().build();
		}else {
			 return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); 
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
