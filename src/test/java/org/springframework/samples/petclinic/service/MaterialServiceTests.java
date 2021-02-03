package org.springframework.samples.petclinic.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.samples.petclinic.model.Material;
import org.springframework.samples.petclinic.model.Profesor;
import org.springframework.samples.petclinic.model.TipoMaterial;
import org.springframework.samples.petclinic.repository.MaterialRepository;
import org.springframework.web.multipart.MultipartFile;


@ExtendWith(MockitoExtension.class) 
@TestInstance(Lifecycle.PER_CLASS) 
public class MaterialServiceTests {
	
	@Mock
	MaterialRepository materialRepository;
	
	@Mock
	ProfesorService profesorService;
	
	@Mock
	TipoMaterialService tipoMaterialService;
	
	@InjectMocks
	MaterialService materialService;
	
	@BeforeAll
	void data() { 
	}
	
	@Test
	void shouldUploadMaterial() throws IOException{
		when(profesorService.getProfesor(any(String.class))).thenReturn(new Profesor());
		when(tipoMaterialService.findById(any(String.class))).thenReturn(new TipoMaterial());
		when(materialRepository.save(any(Material.class))).thenReturn(new Material());
		Path directorioImagenes =Paths.get("src//main//resources//static//frontend//public/material");
		String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
	    Path rutaCompleta = Paths.get(rutaAbsoluta + "//1.pdf");
		MultipartFile pdf = new MockMultipartFile("1.pdf", Files.readAllBytes(rutaCompleta));
		
		materialService.uploadMaterial(pdf, "maribelrb");
		
		verify(materialRepository, times(1)).save(any(Material.class));
	}

	
}
