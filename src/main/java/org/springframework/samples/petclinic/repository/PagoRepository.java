package org.springframework.samples.petclinic.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Pago;

public interface PagoRepository extends CrudRepository<Pago, Integer> {
	
	@Query("SELECT p.concepto FROM Pago p")
	public 	Set<String> allPagos();
	
	@Query("SELECT a FROM Pago p JOIN p.alumnos a WHERE a.nickUsuario = p.alumnos.nickUsuario AND p.concepto = :concepto")
	public List<Alumno> findStudentsByPago(@Param("concepto") String concepto);
	
	@Query("SELECT a FROM Alumno a WHERE a.nickUsuario IN (SELECT a.nickUsuario FROM Alumno a WHERE NOT EXISTS"
			+ " (SELECT p.alumnos.nickUsuario FROM Pago p where a.nickUsuario=p.alumnos.nickUsuario AND p.concepto = :concepto))  ")
	public List<Alumno> findStudentByNoPago(@Param("concepto") String concepto);
	
	//SELECT p.concepto from pagos p where p.concepto not in (SELECT pp.concepto from pagos pp where pp.ALUMNOS_NICK_USUARIO ='Javi')
	@Query("SELECT distinct(p.concepto) FROM Pago p WHERE p.concepto NOT IN (SELECT pp.concepto FROM Pago pp WHERE pp.alumnos.nickUsuario = :nickUsuario)")
	public List<String> findNoPaymentByStudent(@Param("nickUsuario") String nickUsuario);
}
