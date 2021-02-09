package org.springframework.samples.tea.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.samples.tea.model.Alumno;
import org.springframework.samples.tea.model.Premiado;
import org.springframework.samples.tea.model.WallOfFame;
import org.springframework.samples.tea.repository.PremiadoRepository;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
public class PremiadoServiceTests {

	@Mock
	private WallOfFameService wallOfFameService;
	@Mock
	private PremiadoRepository premiadoRepository;

	@InjectMocks
	protected PremiadoService premiadoService;


	@Test
	void shouldNotExistWallOfFameWhenInsertAnAwardedStudent() throws IOException {
		Alumno a = new Alumno();
		a.setNickUsuario("javi56");
		String descripcion = "El mejor de la clase";
		String fechaWall = "2020-W50";
		Path directorioImagenes =Paths.get("src//main//resources//static//frontend//public/photosWall");
		String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
	    Path rutaCompleta = Paths.get(rutaAbsoluta + "//Javi.jpg");
		MultipartFile file = new MockMultipartFile("file.jpg", Files.readAllBytes(rutaCompleta));
		when(wallOfFameService.getWallById(any(String.class))).thenReturn(Optional.empty());

		premiadoService.insertarPremiado(a, fechaWall, descripcion, file);

		verify(wallOfFameService, times(1)).saveWallOfFame(any(WallOfFame.class));
		verify(premiadoRepository, times(1)).save(any(Premiado.class));

	}

	@Test
	void shouldExistWallOfFameWhenInsertAnAwardedStudent() throws IOException {
		Alumno a = new Alumno();
		a.setNickUsuario("javi56");
		String descripcion = "El mejor de la clase";
		String fechaWall = "2020-W50";
		Path directorioImagenes =Paths.get("src//main//resources//static//frontend//public/photosWall");
		String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
	    Path rutaCompleta = Paths.get(rutaAbsoluta + "//Javi.jpg");
		MultipartFile file = new MockMultipartFile("file.jpg", Files.readAllBytes(rutaCompleta));
		when(wallOfFameService.getWallById(any(String.class))).thenReturn(Optional.of(new WallOfFame()));

		premiadoService.insertarPremiado(a, fechaWall, descripcion, file);

		verify(wallOfFameService, times(0)).saveWallOfFame(any(WallOfFame.class));
		verify(premiadoRepository, times(1)).save(any(Premiado.class));

	}

	@Test
	void shouldEditOnlyFile() throws IOException{
		Path directorioImagenes =Paths.get("src//main//resources//static//frontend//public/photosWall");
		String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
	    Path rutaCompleta = Paths.get(rutaAbsoluta + "//Javi.jpg");
		MultipartFile file = new MockMultipartFile("file.jpg", Files.readAllBytes(rutaCompleta));
		premiadoService.editarPremiado(2, "", file, "Maribel");
	}

	@Test
	void shouldEditOnlyDescriptionAndExistPremiado() throws DataAccessException, IOException {
		when(premiadoRepository.findById(any(Integer.class))).thenReturn(Optional.of(new Premiado()));
		premiadoService.editarPremiado(2, "The best student", null, "Maribel");
		verify(premiadoRepository, times(1)).save(any(Premiado.class));
	}


	@Test
	void shouldEditOnlyDescriptionAndNotExistPremiado() throws DataAccessException, IOException {
		when(premiadoRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
		premiadoService.editarPremiado(2, "The best student", null, "Maribel");
		verify(premiadoRepository, times(0)).save(any(Premiado.class));

	}


	@Test
	void shouldNotDeletePremiadoNotExist() {
		when(premiadoRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
		premiadoService.deletePremiadoById(1);
		verify(premiadoRepository, times(0)).deleteById(any(Integer.class));
		verify(premiadoRepository, times(0)).premiadosPorFecha(any(String.class));
		verify(wallOfFameService, times(0)).deleteWallOfFame(any(WallOfFame.class));
	}

	@Test
	void shouldDeleteOnlyPremiado(){
		WallOfFame w = new WallOfFame();
		w.setFechaWall("2020-W50");
		Premiado p = new Premiado();
		p.setWalloffames(w);
		List<Premiado>listaConPremiados= new ArrayList<>();
		listaConPremiados.add(p);
		when(premiadoRepository.findById(any(Integer.class))).thenReturn(Optional.of(p));
		when(premiadoRepository.premiadosPorFecha(any(String.class))).thenReturn(listaConPremiados);

		premiadoService.deletePremiadoById(1);

		verify(premiadoRepository, times(1)).deleteById(any(Integer.class));
		verify(premiadoRepository, times(1)).premiadosPorFecha(any(String.class));
		verify(wallOfFameService, times(0)).deleteWallOfFame(any(WallOfFame.class));
	}

	@Test
	void shouldDeletePremiadoAndEmptyWall(){
		WallOfFame w = new WallOfFame();
		w.setFechaWall("2020-W50");
		Premiado p = new Premiado();
		p.setWalloffames(w);
		List<Premiado>listaSinPremiados= new ArrayList<>();
		when(premiadoRepository.findById(any(Integer.class))).thenReturn(Optional.of(p));
		when(premiadoRepository.premiadosPorFecha(any(String.class))).thenReturn(listaSinPremiados);

		premiadoService.deletePremiadoById(1);

		verify(premiadoRepository, times(1)).deleteById(any(Integer.class));
		verify(premiadoRepository, times(1)).premiadosPorFecha(any(String.class));
		verify(wallOfFameService, times(1)).deleteWallOfFame(any(WallOfFame.class));
	}



}
