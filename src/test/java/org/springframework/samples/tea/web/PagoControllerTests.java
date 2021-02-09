package org.springframework.samples.tea.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.samples.tea.configuration.SecurityConfiguration;
import org.springframework.samples.tea.model.Alumno;
import org.springframework.samples.tea.model.Pago;
import org.springframework.samples.tea.model.TipoPago;
import org.springframework.samples.tea.service.AlumnoService;
import org.springframework.samples.tea.service.PagoService;
import org.springframework.samples.tea.service.TipoPagoService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebMvcTest(controllers = PagosController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class PagoControllerTests {

	private final static String CONCEPTO = "PRIMERA_MATRICULA";
	private final static String NICK_USUARIO = "Evelyn";

	private static Pago p;

	@MockBean
	private PagoService pagoService;

	@Autowired
    private MockMvc mockMvc;

	@MockBean
	private  TipoPagoService tipoPagoService;

	@MockBean
	private AlumnoService alumnoService;

	@MockBean
	private BindingResult bindingResult;

	@BeforeEach
	void setup() {
		p = new Pago();
		Alumno alumno = new Alumno();
		alumno.setNickUsuario("JaviMartinez7");
		alumno.setContraseya("JaviKuka787");
		alumno.setDniUsuario("45676787Y");
		alumno.setNombreCompletoUsuario("Javi Martinez");
		alumno.setCorreoElectronicoUsuario("javikua7@gmail.com");
		alumno.setNumTelefonoUsuario("677676676");
		alumno.setDireccionUsuario("Calle Pepe");
		alumno.setFechaNacimiento(LocalDate.parse("2000-08-13"));
		alumno.setFechaSolicitud(LocalDate.parse("2018-08-13"));
		alumno.setVersion(0);
		TipoPago tipoPago = new TipoPago();
		p.setTipo(tipoPago);
		p.setConcepto(CONCEPTO);
		p.setFecha(LocalDate.now());
		p.setId(30);
    	p.setAlumnos(alumno);

	}
	@WithMockUser(value = "spring")
	@Test
	void testShowPaymentsSet() throws Exception {
		given(this.pagoService.getAllPayments()).willReturn(new HashSet<>());
		mockMvc.perform(get("/pagos")).andExpect(status().isOk());
	}


	@WithMockUser(value = "spring")
	@Test
	void testStudentListByConcept() throws Exception {
		given(this.pagoService.getStudentsByPayment(CONCEPTO)).willReturn(new ArrayList<>());
		mockMvc.perform(get("/pagos/{concepto}", CONCEPTO)).andExpect(status().isOk());
	}

	@WithMockUser(value = "spring")
	@Test
	void testStudentNotPaidListByConcept() throws Exception {
		given(this.pagoService.getStudentsNoPayment(CONCEPTO)).willReturn(new ArrayList<>());
		mockMvc.perform(get("/pagos/notPaid/{concepto}", CONCEPTO)).andExpect(status().isOk());
	}

	@WithMockUser(value = "Evelyn", authorities = {"alumno"})
	@Test
	void testNotPaidListByStudent() throws Exception {
		given(this.pagoService.getNoPaymentByStudent(NICK_USUARIO)).willReturn(new ArrayList<>());
		mockMvc.perform(get("/pagos/notPaidByStudent/{nickUsuario}", NICK_USUARIO)).andExpect(status().isOk());
	}

	@WithMockUser(value = "JaviDeKuka", authorities = {"profesor"})
	@Test
	void testNotPaidListByStudentIsOkAsTeacher() throws Exception {
		given(this.pagoService.getNoPaymentByStudent(NICK_USUARIO)).willReturn(new ArrayList<>());
		mockMvc.perform(get("/pagos/notPaidByStudent/{nickUsuario}", "Paco")).andExpect(status().isOk());
	}

	@WithMockUser(value = "Evelyn", authorities = {"alumno"})
	@Test
	void testNotPaidListByStudentIsUnauthorized() throws Exception {
		given(this.pagoService.getNoPaymentByStudent(NICK_USUARIO)).willReturn(new ArrayList<>());
		mockMvc.perform(get("/pagos/notPaidByStudent/{nickUsuario}", "Paco")).andExpect(status().isUnauthorized());
	}



	@WithMockUser(value = "Evelyn", authorities = {"alumno"})
	@Test
	void testPaymentsListByStudent() throws Exception {
		given(this.pagoService.getPaymentsByStudent(NICK_USUARIO)).willReturn(new ArrayList<>());
		mockMvc.perform(get("/pagos/paidByStudent/{nickUsuario}", NICK_USUARIO)).andExpect(status().isOk());
	}

	@WithMockUser(value = "Evelyn", authorities = {"alumno"})
	@Test
	void testPaymentsListByStudentIsUnathorized() throws Exception {
		given(this.pagoService.getPaymentsByStudent(NICK_USUARIO)).willReturn(new ArrayList<>());
		mockMvc.perform(get("/pagos/paidByStudent/{nickUsuario}", "Paco")).andExpect(status().isUnauthorized());
	}

	@WithMockUser(value = "spring")
	@Test
	void testStudentsHaveNotPaidList() throws Exception {
		given(this.pagoService.getNameStudentByNoPago()).willReturn(new ArrayList<>());
		mockMvc.perform(get("/pagos/studentsNotPaid")).andExpect(status().isOk());
	}


	/*HAY QUE CORREGIR ESTO*/
	@WithMockUser(value = "spring", authorities = {"profesor"})
	@Test
	void testSendingNewPaymentSucces() throws Exception{

		p = new Pago();
		Alumno alumno = new Alumno();
		alumno.setNickUsuario("JaviMartinez7");
		TipoPago tipoPago = new TipoPago();
		p.setTipo(tipoPago);
		p.setConcepto(CONCEPTO);
		p.setFecha(LocalDate.now());
		p.setId(30);
    	p.setAlumnos(alumno);

		given(this.alumnoService.getAlumno(any(String.class))).willReturn(alumno);

		mockMvc.perform(post("/pagos/new")
				.contentType(MediaType.APPLICATION_JSON)
			    .content(p.toJson())
			    .with(csrf()))
		.andExpect(status().isCreated());
	}
	@WithMockUser(value = "spring", authorities = {"profesor"})
	@Test
	void testSendingNewPaymentNoSuccesWithNicknameNull() throws Exception{

		p = new Pago();
		Alumno alumno = new Alumno();
		alumno.setNickUsuario("");
		TipoPago tipoPago = new TipoPago();
		p.setTipo(tipoPago);
		p.setConcepto(CONCEPTO);
		p.setFecha(LocalDate.now());
		p.setId(30);
    	p.setAlumnos(alumno);

		given(this.alumnoService.getAlumno(any(String.class))).willReturn(alumno);

		mockMvc.perform(post("/pagos/new")
				.contentType(MediaType.APPLICATION_JSON)
			    .content(p.toJson())
			    .with(csrf()))
		.andExpect(status().isNonAuthoritativeInformation());
	}

	@WithMockUser(value = "spring", authorities = {"profesor"})
	@Test
	void testSendingNewPaymentNOSuccesWithConceptNull() throws Exception{

		p = new Pago();
		Alumno alumno = new Alumno();
		alumno.setNickUsuario("JaviMartinez7");
		TipoPago tipoPago = new TipoPago();
		p.setTipo(tipoPago);
		p.setConcepto("");
		p.setFecha(LocalDate.now());
		p.setId(30);
    	p.setAlumnos(alumno);

		given(this.alumnoService.getAlumno(any(String.class))).willReturn(alumno);

		mockMvc.perform(post("/pagos/new")
				.contentType(MediaType.APPLICATION_JSON)
			    .content(p.toJson())
			    .with(csrf()))
		.andExpect(status().isNonAuthoritativeInformation());
	}

	@WithMockUser(value = "spring", authorities = {"profesor"})
	@Test
	void testSendingNewPaymentNoSuccesWithTypeNull() throws Exception{

		p = new Pago();
		Alumno alumno = new Alumno();
		alumno.setNickUsuario("JaviMartinez7");
		TipoPago tipoPago = new TipoPago();
		p.setTipo(tipoPago);
		p.setConcepto(CONCEPTO);
		p.setFecha(LocalDate.now());
		p.setId(30);
    	p.setAlumnos(alumno);

		given(this.alumnoService.getAlumno(any(String.class))).willReturn(alumno);

		mockMvc.perform(post("/pagos/new")
				.contentType(MediaType.APPLICATION_JSON)
			    .content(p.toJson2())
			    .with(csrf()))
		.andExpect(status().isNonAuthoritativeInformation());
	}

	@WithMockUser(value = "spring", authorities = {"profesor"})
	@Test
	void testSendingNewPaymentNoSuccesWithStudentNull() throws Exception{

		p = new Pago();
		Alumno alumno = new Alumno();
		alumno.setNickUsuario("JaviMartinez7");
		TipoPago tipoPago = new TipoPago();
		p.setTipo(tipoPago);
		p.setConcepto(CONCEPTO);
		p.setFecha(LocalDate.now());
		p.setId(30);
    	p.setAlumnos(alumno);

		given(this.alumnoService.getAlumno(any(String.class))).willReturn(null);

		mockMvc.perform(post("/pagos/new")
				.contentType(MediaType.APPLICATION_JSON)
			    .content(p.toJson())
			    .with(csrf()))
		.andExpect(status().isNoContent());
	}

	@WithMockUser(value = "spring", authorities = {"profesor"})
	@Test
	void testSendingNewPaymentSuccesWithStudentWithdrawnDateNull() throws Exception{

		p = new Pago();
		Alumno alumno = new Alumno();
		alumno.setNickUsuario("JaviMartinez7");
		alumno.setFechaBaja(LocalDate.parse("2018-01-01"));
		TipoPago tipoPago = new TipoPago();
		p.setTipo(tipoPago);
		p.setConcepto(CONCEPTO);
		p.setFecha(LocalDate.parse("2018-02-01"));
		p.setId(30);
    	p.setAlumnos(alumno);

		given(this.alumnoService.getAlumno(any(String.class))).willReturn(alumno);

		mockMvc.perform(post("/pagos/new")
				.contentType(MediaType.APPLICATION_JSON)
			    .content(p.toJson3())
			    .with(csrf()))
		.andExpect(status().isNoContent());
	}
}
