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
	@Query("SELECT a FROM Alumno a JOIN a.pagos p WHERE a.nick_usuario LIKE p.alumnos_nick_usuario")

	public List<Alumno> findPagosByStudents();

}
