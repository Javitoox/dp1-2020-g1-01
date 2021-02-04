package org.springframework.samples.petclinic.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Material;
import org.springframework.samples.petclinic.model.Profesor;
import org.springframework.samples.petclinic.model.TipoMaterial;
import org.springframework.samples.petclinic.repository.MaterialRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class MaterialService {
	
	private MaterialRepository materialRepository;
	private ProfesorService profesorService;
	private TipoMaterialService tipoMaterialService;

	@Autowired
	public MaterialService(MaterialRepository materialRepository, ProfesorService profesorService, TipoMaterialService tipoMaterialService) {
		super();
		this.materialRepository = materialRepository;
		this.profesorService = profesorService;
		this.tipoMaterialService = tipoMaterialService;
	}
	 
	public List<Material> getMaterialPorProfesor(String nickProfesor){
		return materialRepository.getMaterialByProfesor(nickProfesor);		
	}

	public List<Material> getMaterialPorAlumno(String nickUsuario){
		return materialRepository.getMaterialByAlumno(nickUsuario);		
	}
	
	public Material uploadMaterial(MultipartFile pdf, String nickProfesor) throws IOException {
		Material m = new Material();
		Profesor profesor = profesorService.getProfesor(nickProfesor);
		m.setNombreMaterial(pdf.getOriginalFilename());
		m.setFechaSubida(LocalDate.now());
		TipoMaterial tipo = tipoMaterialService.findById("Homework");
		m.setTipoMaterial(tipo);
		m.setProfesores(profesor);
		
		Material material = materialRepository.save(m);
		
		Path directorioImagenes =Paths.get("src//main//resources//static//frontend//public/material");
		String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
		  	 
	    byte[] bytes = pdf.getBytes(); 
	    Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + material.getId() + ".pdf");
	    Files.write(rutaCompleta, bytes);
		return material; 
		  
	}
	
	public Material findMaterialById(Integer id) {
		return materialRepository.findById(id).get();
	}

	public void delete(Material m) {
		materialRepository.delete(m);
	}

	
}
