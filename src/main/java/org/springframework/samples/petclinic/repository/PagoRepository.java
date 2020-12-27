package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.samples.petclinic.model.Pago;

public interface PagoRepository extends CrudRepository<Pago, Integer> {
	
//	@Query("SELECT a FROM Alumno a JOIN a.pagos p WHERE a.nickUsuario=p.alumnos.nickUsuario")
//    @Query("SELECT alumno,grupo FROM Alumno alumno JOIN alumno.grupos grupo WHERE grupo.cursos.cursoDeIngles LIKE :cursoDeIngles%")

//	@Query(value="SELECT * FROM Alumnos a NATURAL JOIN Pagos p WHERE a.nick_usuario=p.alumnos_nick_usuario", nativeQuery=true)
	@Query("SELECT a FROM Pago p JOIN p.alumnos a WHERE a.nickUsuario = p.alumnos.nickUsuario")
	public List<Alumno> findPagosByStudents();
	
//	@Query("SELECT a FROM Pago p JOIN p.alumnos a WHERE a.nickUsuario != p.alumnos.nickUsuario")
//	@Query("SELECT * FROM Alumno a WHERE a.nickUsuario NOT IN (SELECT p.alumnos.nickUsuario FROM Pago p)")
	@Query("SELECT a FROM Alumno a WHERE a.nickUsuario IN (SELECT a.nickUsuario FROM Alumno a WHERE NOT EXISTS (SELECT p.alumnos.nickUsuario FROM Pago p where a.nickUsuario=p.alumnos.nickUsuario)) ")
	public List<Alumno> findNoPagosByStudents();

}
