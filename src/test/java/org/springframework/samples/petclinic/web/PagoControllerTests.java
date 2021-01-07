package org.springframework.samples.petclinic.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.samples.petclinic.configuration.SecurityConfiguration;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.AsignacionProfesor;
import org.springframework.samples.petclinic.model.Curso;
import org.springframework.samples.petclinic.model.Grupo;
import org.springframework.samples.petclinic.model.Pago;
import org.springframework.samples.petclinic.model.TipoCurso;
import org.springframework.samples.petclinic.service.AlumnoService;
import org.springframework.samples.petclinic.service.AsignacionProfesorService;
import org.springframework.samples.petclinic.service.GrupoService;
import org.springframework.samples.petclinic.service.PagoService;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebMvcTest(controllers = PagosController.class,
excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class),
excludeAutoConfiguration= SecurityConfiguration.class)
public class PagoControllerTests {
	
	private final static String CONCEPTO = "PRIMERA_MATRICULA";
	private final static String TIPO = "BIZUM";
	private final static String NICK_USUARIO = "Evelyn";
	

	
	@MockBean
	private PagoService pagoService;
	private Pago pago;
	
	@Autowired
    private MockMvc mockMvc;
	
	
	@BeforeEach
	void setup() {
		pago = new Pago();
		Alumno alumno = new Alumno();
		alumno.setNickUsuario("JaviMartinez7");
		alumno.setContraseya("JaviKuka787");
		alumno.setDniUsuario("45676787Y");
		alumno.setNombreCompletoUsuario("Javi Martinez");
		alumno.setCorreoElectronicoUsuario("javikua7@gmail.com");
		alumno.setNumTelefonoUsuario("677676676");
		alumno.setDireccionUsuario("Calle Pepe");
		alumno.setFechaNacimiento(LocalDate.parse("2000-08-13"));
		alumno.setFechaSolicitud(LocalDate.now());
		pago.setConcepto(CONCEPTO);
		pago.setFecha(LocalDate.now());
		pago.setId(30);
		pago.setTipo(TIPO);
		pago.setAlumnos(alumno);
		
	}
	@WithMockUser(value = "spring")
	@Test
	void testShowPaymentsSet() throws Exception {
		given(this.pagoService.getAllPayments()).willReturn(new HashSet<>());
		mockMvc.perform(get("/pagos").sessionAttr("type","profesor")).andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testDontShowAssignationListByTeacherIfLoggedAsAlumn() throws Exception {
		given(this.pagoService.getAllPayments()).willReturn(new HashSet<>());
		mockMvc.perform(get("/pagos").sessionAttr("type","alumno")).andExpect(status().isUnauthorized());
	}
	
	@WithMockUser(value = "spring")
    @Test
    void testDontShowAListGroupIfNotLogged() throws Exception{
		given(this.pagoService.getAllPayments()).willReturn(new HashSet<>());
		mockMvc.perform(get("/pagos").sessionAttr("type","null")).andExpect(status().isUnauthorized());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testStudentListByConcept() throws Exception {
		given(this.pagoService.getStudentsByPayment(CONCEPTO)).willReturn(new ArrayList<>());
		mockMvc.perform(get("/pagos/{concepto}", CONCEPTO).sessionAttr("type","profesor")).andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testStudentListByConceptIfLoggedAsAlumn() throws Exception {
		given(this.pagoService.getStudentsByPayment(CONCEPTO)).willReturn(new ArrayList<>());
		mockMvc.perform(get("/pagos/{concepto}", CONCEPTO).sessionAttr("type","alumno")).andExpect(status().isUnauthorized());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testStudentListByConceptIfNotLogged() throws Exception {
		given(this.pagoService.getStudentsByPayment(CONCEPTO)).willReturn(new ArrayList<>());
		mockMvc.perform(get("/pagos/{concepto}", CONCEPTO).sessionAttr("type","null")).andExpect(status().isUnauthorized());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testStudentNotPaidListByConcept() throws Exception {
		given(this.pagoService.getStudentsNoPayment(CONCEPTO)).willReturn(new ArrayList<>());
		mockMvc.perform(get("/pagos/notPaid/{concepto}", CONCEPTO).sessionAttr("type","profesor")).andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testStudentNotPaidListByConceptIfLoggedAsAlumn() throws Exception {
		given(this.pagoService.getStudentsNoPayment(CONCEPTO)).willReturn(new ArrayList<>());
		mockMvc.perform(get("/pagos/notPaid/{concepto}", CONCEPTO).sessionAttr("type","alumno")).andExpect(status().isUnauthorized());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testStudentNotPaidListByConceptIfNotLogged() throws Exception {
		given(this.pagoService.getStudentsNoPayment(CONCEPTO)).willReturn(new ArrayList<>());
		mockMvc.perform(get("/pagos/notPaid/{concepto}", CONCEPTO).sessionAttr("type","null")).andExpect(status().isUnauthorized());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testNotPaidListByStudent() throws Exception {
		given(this.pagoService.getNoPaymentByStudent(NICK_USUARIO)).willReturn(new ArrayList<>());
		mockMvc.perform(get("/pagos/notPaidByStudent/{nickUsuario}", NICK_USUARIO).sessionAttr("type","alumno")).andExpect(status().isOk());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testNotPaidListByStudentIfLoggedAsAlumn() throws Exception {
		given(this.pagoService.getNoPaymentByStudent(NICK_USUARIO)).willReturn(new ArrayList<>());
		mockMvc.perform(get("/pagos/notPaidByStudent/{nickUsuario}", NICK_USUARIO).sessionAttr("type","profesor")).andExpect(status().isUnauthorized());
	}
	
	@WithMockUser(value = "spring")
	@Test
	void testNotPaidListByStudentIfNotLogged() throws Exception {
		given(this.pagoService.getNoPaymentByStudent(NICK_USUARIO)).willReturn(new ArrayList<>());
		mockMvc.perform(get("/pagos/notPaidByStudent/{nickUsuario}", NICK_USUARIO).sessionAttr("type","null")).andExpect(status().isUnauthorized());
	}
	@WithMockUser(value = "spring")
	@Test
	void testSendingNewPaymentSucces() throws Exception{
		Gson gson = new Gson();
		String jsonString = gson.toJson(pago);
		log.info("Informa: "+jsonString);
		
		mockMvc.perform(post("/pagos/new")
				.contentType(MediaType.APPLICATION_JSON)
			    .content(jsonString)
			    .with(csrf()))
		.andExpect(status().isOk());
	}

}
