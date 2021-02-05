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
	
	@Query("SELECT a FROM Pago p JOIN p.alumnos a WHERE a.nickUsuario = p.alumnos.nickUsuario AND p.concepto = :concepto AND a.fechaMatriculacion > '1980-01-01'")
	public List<Alumno> findStudentsByPago(@Param("concepto") String concepto);
	
	@Query("SELECT a FROM Alumno a WHERE a.nickUsuario IN (SELECT a.nickUsuario FROM Alumno a WHERE NOT EXISTS"
			+ " (SELECT p.alumnos.nickUsuario FROM Pago p where a.nickUsuario=p.alumnos.nickUsuario AND p.concepto = :concepto)) AND a.fechaMatriculacion > '1980-01-01'  ")
	public List<Alumno> findStudentByNoPago(@Param("concepto") String concepto);

	@Query("SELECT a.nickUsuario FROM Alumno a WHERE a.nickUsuario IN ( SELECT p.alumnos.nickUsuario FROM Pago p GROUP BY p.alumnos.nickUsuario "
			+ "HAVING COUNT (p.concepto) < (SELECT COUNT(DISTINCT p.concepto ) FROM Pago p) ) "
			+ "OR a.nickUsuario  IN  (SELECT a.nickUsuario FROM Alumno a WHERE a.nickUsuario NOT IN ( SELECT p.alumnos.nickUsuario FROM Pago p )  "
			+ "AND a.fechaMatriculacion > '1980-01-01')")
	public List<String> findNameStudentByNoPago();
	
	@Query("SELECT distinct(p.concepto) FROM Pago p WHERE p.concepto NOT IN (SELECT pp.concepto FROM Pago pp WHERE pp.alumnos.nickUsuario = :nickUsuario)")
	public List<String> findNoPaymentByStudent(@Param("nickUsuario") String nickUsuario);
	
	@Query("SELECT p FROM Pago p WHERE p.alumnos.nickUsuario = :nickUsuario")
	public List<Pago> findPaymentsByStudent(@Param("nickUsuario") String nickUsuario);
}
