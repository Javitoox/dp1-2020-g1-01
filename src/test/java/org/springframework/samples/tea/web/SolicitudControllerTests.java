package org.springframework.samples.tea.web;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.samples.tea.configuration.SecurityConfiguration;
import org.springframework.samples.tea.model.Alumno;
import org.springframework.samples.tea.model.Solicitud;
import org.springframework.samples.tea.model.Tutor;
import org.springframework.samples.tea.service.AlumnoService;
import org.springframework.samples.tea.service.SolicitudService;
import org.springframework.samples.tea.service.TutorService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(controllers= SolicitudController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
@TestInstance(Lifecycle.PER_CLASS)
public class SolicitudControllerTests {

	@MockBean
	private SolicitudService solicitudService;
	@MockBean
	private AlumnoService alumnoService;
    @MockBean
    private TutorService tutorService;
	@Autowired
	private MockMvc mockMvc;
	private  Solicitud solicitud;
	private Solicitud solicitud2;
    private Solicitud solicitud3;

	@MockBean
	private PasswordEncoder passwordEncoder;

	@BeforeAll
	void setup() {
		solicitud = new Solicitud();
		solicitud2 = new Solicitud();
		solicitud3 = new Solicitud();
		Alumno alumno = new Alumno();
		Alumno alumno2 = new Alumno();
		Tutor tutor = new Tutor();
		alumno.setNickUsuario("GonzaloAA");
		alumno.setContraseya("Bebesita7");
		alumno.setDniUsuario("20502443J");
		alumno.setNombreCompletoUsuario("Javi Martinez");
		alumno.setCorreoElectronicoUsuario("javikua7@gmail.com");
		alumno.setNumTelefonoUsuario("677676676");
		alumno.setDireccionUsuario("Calle Pepe");
		alumno.setFechaNacimiento(LocalDate.of(1998, 10, 03));
		alumno2.setNickUsuario("JaviMartinez7");
		alumno2.setContraseya("JaviKuka787");
		alumno2.setDniUsuario("45676787Y");
		alumno2.setNombreCompletoUsuario("Javi Martinez");
		alumno2.setCorreoElectronicoUsuario("javikua7@gmail.com");
		alumno2.setNumTelefonoUsuario("677676676");
		alumno2.setDireccionUsuario("Calle Pepe");
		alumno2.setFechaNacimiento(LocalDate.of(2012, 10, 03));
		tutor.setNickUsuario("TutorGonz");
		tutor.setContraseya("JaviKuka77");
		tutor.setDniUsuario("24502542N");
		tutor.setNombreCompletoUsuario("Gonzalo Alvarez Garcia");
		tutor.setCorreoElectronicoUsuario("gonzalo@gmail.com");
		tutor.setNumTelefonoUsuario("677673676");
		tutor.setDireccionUsuario("Calle Pepe");
		tutor.setFechaNacimiento(LocalDate.of(1990, 10, 02));
		solicitud.setAlumno(alumno);
		solicitud2.setTutor(tutor);
		solicitud2.setAlumno(alumno);
		solicitud3.setAlumno(alumno2);

	}

	@WithMockUser(value = "spring")
	@Test
	void testSendingNewAlumSucces() throws Exception{ //Este test crea un alumno y lo envia bien
	    given(solicitudService.getAlumno(solicitud.getAlumno().getNickUsuario())).willReturn(null);
		mockMvc.perform(post("/requests/sending")
				.contentType(MediaType.APPLICATION_JSON)
				.content(solicitud.toJson())
                .with(csrf()))
        .andExpect(status().isCreated());
	}

	@WithMockUser(value = "spring")
	@Test
	void testSendingNewAlumWithFechaMatriculacion() throws Exception{
	    Solicitud c = new Solicitud();
	    Alumno b = new Alumno();
        b.setNickUsuario("nuke");
        b.setContraseya("JaviKuka77");
        b.setDniUsuario("20502043Y");
        b.setNombreCompletoUsuario("Javi Martinez");
        b.setCorreoElectronicoUsuario("javikua7@gmail.com");
        b.setNumTelefonoUsuario("677676676");
        b.setDireccionUsuario("Calle Pepe");
        b.setFechaNacimiento(LocalDate.of(1998, 10, 03));
        b.setFechaMatriculacion(LocalDate.of(2020,10,03));
        c.setAlumno(b);
		given(solicitudService.getAlumnoByIdOrNif(any(),any())).willReturn(b);
		mockMvc.perform(post("/requests/sending")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(c.toJson3()))
        .andExpect(status().isOk());
	}

	@WithMockUser(value = "spring")
	@Test
	void testSendingNewAlumWithFechaBaja() throws Exception{
		Solicitud c = new Solicitud();
	    Alumno b = new Alumno();
        b.setNickUsuario("nuke");
        b.setContraseya("JaviKuka77");
        b.setDniUsuario("20502043Y");
        b.setNombreCompletoUsuario("Javi Martinez");
        b.setCorreoElectronicoUsuario("javikua7@gmail.com");
        b.setNumTelefonoUsuario("677676676");
        b.setDireccionUsuario("Calle Pepe");
        b.setFechaNacimiento(LocalDate.of(1998, 10, 03));
        b.setFechaBaja(LocalDate.of(2020,10,03));
        c.setAlumno(b);
		given(solicitudService.getAlumnoByIdOrNif(any(),any())).willReturn(b);

		mockMvc.perform(post("/requests/sending")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(c.toJson3_1()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", is("The student has already been withdrawn")));
	}

	@WithMockUser(value = "spring")
	@Test
	void testSendingNewAlumForUpdate() throws Exception{
		Solicitud c = new Solicitud();
	    Alumno alumno = new Alumno();
	    alumno.setNickUsuario("GonzaloAA");
		alumno.setContraseya("Bebesita7");
		alumno.setDniUsuario("20502443J");
		alumno.setNombreCompletoUsuario("Javi Martinez");
		alumno.setCorreoElectronicoUsuario("javikua7@gmail.com");
		alumno.setNumTelefonoUsuario("677676676");
		alumno.setDireccionUsuario("Calle Pepe");
		alumno.setFechaNacimiento(LocalDate.of(1998, 10, 03));
		c.setAlumno(alumno);
		Alumno alumno2 = new Alumno();
		alumno2.setNickUsuario("GonzaloAA");
		alumno2.setContraseya("$2a$10$W0n5qgTKUkxNzMtDUBYGG.cP5LGg1ohMsnlr1GLjktpGND6VO./T2");
		alumno2.setDniUsuario("20502443J");
		alumno2.setNombreCompletoUsuario("Javi Martinez");
		alumno2.setCorreoElectronicoUsuario("javikua7@gmail.com");
		alumno2.setNumTelefonoUsuario("677676676");
		alumno2.setDireccionUsuario("Calle Pepe");
		alumno2.setFechaNacimiento(LocalDate.of(1998, 10, 03));
		given(solicitudService.getAlumnoByIdOrNif(any(),any())).willReturn(alumno2);
		given(passwordEncoder.matches(any(), any())).willReturn(true);

		mockMvc.perform(post("/requests/sending")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(c.toJson()))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$", is("Successful shipment")));
	}

	@WithMockUser(value = "spring")
	@Test
	void testSendingNewAlumForUpdateWithErrorCredentials() throws Exception{
		Solicitud c = new Solicitud();
	    Alumno alumno = new Alumno();
	    alumno.setNickUsuario("GonzaloAA");
		alumno.setContraseya("Prueba123");
		alumno.setDniUsuario("20502443J");
		alumno.setNombreCompletoUsuario("Javi Martinez");
		alumno.setCorreoElectronicoUsuario("javikua7@gmail.com");
		alumno.setNumTelefonoUsuario("677676676");
		alumno.setDireccionUsuario("Calle Pepe");
		alumno.setFechaNacimiento(LocalDate.of(1998, 10, 03));
		c.setAlumno(alumno);
		Alumno alumno2 = new Alumno();
		alumno2.setNickUsuario("GonzaloAA");
		alumno2.setContraseya("$2a$10$W0n5qgTKUkxNzMtDUBYGG.cP5LGg1ohMsnlr1GLjktpGND6VO./T2");
		alumno2.setDniUsuario("20502443J");
		alumno2.setNombreCompletoUsuario("Javi Martinez");
		alumno2.setCorreoElectronicoUsuario("javikua7@gmail.com");
		alumno2.setNumTelefonoUsuario("677676676");
		alumno2.setDireccionUsuario("Calle Pepe");
		alumno2.setFechaNacimiento(LocalDate.of(1998, 10, 03));
		given(solicitudService.getAlumnoByIdOrNif(any(),any())).willReturn(alumno2);
		given(passwordEncoder.matches(any(), any())).willReturn(false);

		mockMvc.perform(post("/requests/sending")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(c.toJson()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", is("The student already exists and his credentials are incorrect")));
	}

	@WithMockUser(value = "spring")
	@Test
	void testSendingNewAlumError() throws Exception{
		Solicitud a = new Solicitud();
		Alumno doble = new Alumno();
		doble.setNickUsuario("");
		doble.setContraseya("");
		doble.setDniUsuario("45676787das3Y");
		doble.setNombreCompletoUsuario("Javi Martinez");
		doble.setCorreoElectronicoUsuario("javikua7@gmail.com");
		doble.setNumTelefonoUsuario("977676676");
		doble.setDireccionUsuario("Calle Pepe");
		doble.setFechaNacimiento(LocalDate.of(2000, 10, 03));
		a.setAlumno(doble);
		mockMvc.perform(post("/requests/sending")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(a.toJson()))
        .andExpect(status().isNonAuthoritativeInformation());
	}

	//SENDING ALL

	@WithMockUser(value = "spring")
	@Test
	void testSendingAllSucces() throws Exception{
		given(solicitudService.getAlumno(solicitud2.getAlumno().getNickUsuario())).willReturn(null);
		given(solicitudService.getTutor(solicitud2.getTutor().getNickUsuario())).willReturn(null);
		mockMvc.perform(post("/requests/sendingAll")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(solicitud2.toJson2()))
        .andExpect(status().isCreated());
	}
    @WithMockUser(value = "spring")
    @Test
    void testSendingNewAlumForUpdateAll() throws Exception{
        Solicitud c = new Solicitud();
        Alumno alumno = new Alumno();
        alumno.setNickUsuario("GonzaloAA");
        alumno.setContraseya("Bebesita7");
        alumno.setDniUsuario("20502443J");
        alumno.setNombreCompletoUsuario("Javi Martinez");
        alumno.setCorreoElectronicoUsuario("javikua7@gmail.com");
        alumno.setNumTelefonoUsuario("677676676");
        alumno.setDireccionUsuario("Calle Pepe");
        alumno.setFechaNacimiento(LocalDate.of(1998, 10, 03));
        c.setAlumno(alumno);
        c.setTutor(solicitud2.getTutor());
        Alumno alumno2 = new Alumno();
        alumno2.setNickUsuario("GonzaloAA");
        alumno2.setContraseya("$2a$10$W0n5qgTKUkxNzMtDUBYGG.cP5LGg1ohMsnlr1GLjktpGND6VO./T2");
        alumno2.setDniUsuario("20502443J");
        alumno2.setNombreCompletoUsuario("Javi Martinez");
        alumno2.setCorreoElectronicoUsuario("javikua7@gmail.com");
        alumno2.setNumTelefonoUsuario("677676676");
        alumno2.setDireccionUsuario("Calle Pepe");
        alumno2.setFechaNacimiento(LocalDate.of(1998, 10, 03));
        given(solicitudService.getAlumnoByIdOrNif(any(),any())).willReturn(alumno2);
        given(solicitudService.getTutorByIdOrNif(solicitud2.getTutor().getNickUsuario(),solicitud2.getTutor().getDniUsuario())).willReturn(null);
        given(passwordEncoder.matches(any(), any())).willReturn(true);

        mockMvc.perform(post("/requests/sendingAll")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(c.toJson2()))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$", is("Successful shipment")));
    }
    @WithMockUser(value = "spring")
    @Test
    void testSendingNewAlumTutorForUpdateAll() throws Exception{
        Solicitud c = new Solicitud();
        Tutor tutor = new Tutor();
        tutor.setNickUsuario("GonzaloAA");
        tutor.setContraseya("Bebesita7");
        tutor.setDniUsuario("20502443J");
        tutor.setNombreCompletoUsuario("Javi Martinez");
        tutor.setCorreoElectronicoUsuario("javikua7@gmail.com");
        tutor.setNumTelefonoUsuario("677676676");
        tutor.setDireccionUsuario("Calle Pepe");
        tutor.setFechaNacimiento(LocalDate.of(1998, 10, 03));
        Alumno alumno = new Alumno();
        alumno.setNickUsuario("GonzaloAA");
        alumno.setContraseya("Bebesita7");
        alumno.setDniUsuario("20502443J");
        alumno.setNombreCompletoUsuario("Javi Martinez");
        alumno.setCorreoElectronicoUsuario("javikua7@gmail.com");
        alumno.setNumTelefonoUsuario("677676676");
        alumno.setDireccionUsuario("Calle Pepe");
        alumno.setFechaNacimiento(LocalDate.of(1998, 10, 03));
        c.setAlumno(alumno);
        c.setTutor(tutor);
        Alumno alumno2 = new Alumno();
        alumno2.setNickUsuario("GonzaloAA");
        alumno2.setContraseya("$2a$10$W0n5qgTKUkxNzMtDUBYGG.cP5LGg1ohMsnlr1GLjktpGND6VO./T2");
        alumno2.setDniUsuario("20502443J");
        alumno2.setNombreCompletoUsuario("Javi Martinez");
        alumno2.setCorreoElectronicoUsuario("javikua7@gmail.com");
        alumno2.setNumTelefonoUsuario("677676676");
        alumno2.setDireccionUsuario("Calle Pepe");
        alumno2.setFechaNacimiento(LocalDate.of(1998, 10, 03));
        Tutor tutor2 = new Tutor();
        tutor2.setNickUsuario("GonzaloAA");
        tutor2.setContraseya("$2a$10$W0n5qgTKUkxNzMtDUBYGG.cP5LGg1ohMsnlr1GLjktpGND6VO./T2");
        tutor2.setDniUsuario("20502443J");
        tutor2.setNombreCompletoUsuario("Javi Martinez");
        tutor2.setCorreoElectronicoUsuario("javikua7@gmail.com");
        tutor2.setNumTelefonoUsuario("677676676");
        tutor2.setDireccionUsuario("Calle Pepe");
        tutor2.setFechaNacimiento(LocalDate.of(1998, 10, 03));
        given(solicitudService.getAlumnoByIdOrNif(any(),any())).willReturn(alumno2);
        given(solicitudService.getTutorByIdOrNif(any(),any())).willReturn(tutor2);
        given(passwordEncoder.matches(any(), any())).willReturn(true);
        mockMvc.perform(post("/requests/sendingAll")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(c.toJson2()))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$", is("Successful shipment")));
    }
    @WithMockUser(value = "spring")
    @Test
    void testSendingUpdateAlumTutorForUpdateAll() throws Exception{
        Solicitud c = new Solicitud();
        Tutor tutor = new Tutor();
        tutor.setNickUsuario("GonzaloAA");
        tutor.setContraseya("Bebesita7");
        tutor.setDniUsuario("20502443J");
        tutor.setNombreCompletoUsuario("Javi Martinez");
        tutor.setCorreoElectronicoUsuario("javikua7@gmail.com");
        tutor.setNumTelefonoUsuario("677676676");
        tutor.setDireccionUsuario("Calle Pepe");
        tutor.setFechaNacimiento(LocalDate.of(1998, 10, 03));
        c.setAlumno(solicitud2.getAlumno());
        c.setTutor(tutor);
        Tutor tutor2 = new Tutor();
        tutor2.setNickUsuario("GonzaloAA");
        tutor2.setContraseya("$2a$10$W0n5qgTKUkxNzMtDUBYGG.cP5LGg1ohMsnlr1GLjktpGND6VO./T2");
        tutor2.setDniUsuario("20502443J");
        tutor2.setNombreCompletoUsuario("Javi Martinez");
        tutor2.setCorreoElectronicoUsuario("javikua7@gmail.com");
        tutor2.setNumTelefonoUsuario("677676676");
        tutor2.setDireccionUsuario("Calle Pepe");
        tutor2.setFechaNacimiento(LocalDate.of(1998, 10, 03));
        given(solicitudService.getAlumnoByIdOrNif(any(),any())).willReturn(null);
        given(solicitudService.getTutorByIdOrNif(any(),any())).willReturn(tutor2);
        given(passwordEncoder.matches(any(), any())).willReturn(true);
        mockMvc.perform(post("/requests/sendingAll")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(c.toJson2()))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$", is("Successful shipment")));
    }
    @WithMockUser(value = "spring")
    @Test
    void testSendingUpdateStudentCreateTutorWrong() throws Exception{
	    Alumno a = new Alumno();
        a.setNickUsuario("GonzaloAA");
        a.setContraseya("JaviKuka787");
        a.setDniUsuario("20502443J");
        a.setNombreCompletoUsuario("Javi Martinez");
        a.setCorreoElectronicoUsuario("javikua7@gmail.com");
        a.setNumTelefonoUsuario("677676676");
        a.setDireccionUsuario("Calle Pepe");
        a.setFechaNacimiento(LocalDate.of(1998, 10, 03));
        given(solicitudService.getAlumnoByIdOrNif(solicitud2.getAlumno().getNickUsuario(), solicitud2.getAlumno().getDniUsuario())).willReturn(a);
        given(solicitudService.getTutorByIdOrNif(solicitud2.getTutor().getNickUsuario(), solicitud2.getTutor().getDniUsuario())).willReturn(null);
        mockMvc.perform(post("/requests/sendingAll")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(solicitud2.toJson2()))
            .andExpect(status().isOk());
    }

    @WithMockUser(value = "spring")
    @Test
    void testSendingWithErrors() throws Exception{
        given(solicitudService.getAlumno(solicitud2.getAlumno().getNickUsuario())).willReturn(solicitud2.getAlumno());
        solicitud2.getAlumno().setNumTelefonoUsuario("219837219837982374932");
        given(solicitudService.getTutor(solicitud2.getTutor().getNickUsuario())).willReturn(null);
        mockMvc.perform(post("/requests/sendingAll")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(solicitud2.toJson2()))
            .andExpect(status().isNonAuthoritativeInformation());
    }

	@WithMockUser(value = "spring")
	@Test
	void testSendingAllUpdatedTutorCreatedStudent() throws Exception{
		given(solicitudService.getAlumno(solicitud2.getAlumno().getNickUsuario())).willReturn(null);
		given(solicitudService.getTutor(solicitud2.getTutor().getNickUsuario())).willReturn(solicitud2.getTutor());
		mockMvc.perform(post("/requests/sendingAll")
				.with(csrf())
				.contentType(MediaType.APPLICATION_JSON)
				.content(solicitud2.toJson2()))
        .andExpect(status().isCreated());
	}
	@WithMockUser(value = "spring")
	@Test
	void testSendingAllUpdatedAll() throws Exception {
        given(solicitudService.getAlumno(solicitud2.getAlumno().getNickUsuario())).willReturn(solicitud2.getAlumno());
        given(solicitudService.getTutor(solicitud2.getTutor().getNickUsuario())).willReturn(solicitud2.getTutor());
        mockMvc.perform(post("/requests/sendingAll")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(solicitud2.toJson2()))
            .andExpect(status().isCreated());
    }

    @WithMockUser(value = "spring")
    @Test
    void testSendingNewAlumWithFechaMatriculacionAll() throws Exception{
        Solicitud c = new Solicitud();
        Alumno b = new Alumno();
        b.setNickUsuario("nuke");
        b.setContraseya("JaviKuka77");
        b.setDniUsuario("20502043Y");
        b.setNombreCompletoUsuario("Javi Martinez");
        b.setCorreoElectronicoUsuario("javikua7@gmail.com");
        b.setNumTelefonoUsuario("677676676");
        b.setDireccionUsuario("Calle Pepe");
        b.setFechaNacimiento(LocalDate.of(1998, 10, 03));
        b.setFechaMatriculacion(LocalDate.of(2020,10,03));
        c.setAlumno(b);
        Tutor t = new Tutor();
        t.setNickUsuario("tutonuke");
        t.setContraseya("JaviKuka77");
        t.setDniUsuario("24509842D");
        t.setNombreCompletoUsuario("Gonzalo Alvarez Garcia");
        t.setCorreoElectronicoUsuario("gonzalo@gmail.com");
        t.setNumTelefonoUsuario("677673676");
        t.setDireccionUsuario("Calle Pepe");
        t.setFechaNacimiento(LocalDate.of(1990, 10, 02));
        c.setTutor(t);
        given(solicitudService.getAlumnoByIdOrNif(any(),any())).willReturn(b);
        given(solicitudService.getTutorByIdOrNif(any(),any())).willReturn(t);
        mockMvc.perform(post("/requests/sendingAll")
            .with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(c.toJson5()))
            .andExpect(status().isOk());
    }
	@WithMockUser(value = "spring")
	@Test
	void testShowPendingRequest() throws Exception {
		given(this.solicitudService.getAllSolicitudes()).willReturn(new ArrayList<>());
		mockMvc.perform(get("/requests/pending")).andExpect(status().isOk());
	}

	@WithMockUser(value = "spring")
	@Test
	void testShouldDenyPendingRequest() throws Exception {
		given(this.solicitudService.getAlumno(any(String.class))).willReturn(new Alumno());
		mockMvc.perform(put("/requests/decline/marrambla").with(csrf())).andExpect(status().isOk());
	}

	@WithMockUser(value = "spring")
	@Test
	void testShouldAcceptPendingRequest() throws Exception {
		given(this.solicitudService.getAlumno(any(String.class))).willReturn(new Alumno());
		mockMvc.perform(put("/requests/accept/marrambla").with(csrf())).andExpect(status().isOk());
	}

}
