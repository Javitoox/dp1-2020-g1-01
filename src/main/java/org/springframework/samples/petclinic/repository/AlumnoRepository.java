package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Alumno;
import org.springframework.web.bind.annotation.RequestParam;


public interface AlumnoRepository extends CrudRepository<Alumno, String> {
	
	@Query(value = "Select * from alumnos where (alumnos.nickUsuario = :nickUsuario)", nativeQuery = true)
	public Alumno findByNick(@Param("nickUsuario") String nickUsuario);
	
	@Query(value = "Select * from alumnos a natural join grupos where (grupos_nombre_grupo= :nombreGrupo)",nativeQuery = true)
    public List<Alumno> findByGroup(@Param("nombreGrupo") String nombreGrupo);
	
	
	@Query(value="SELECT * FROM ALUMNOS WHERE FECHA_SOLICITUD IS NULL",nativeQuery = true)
	public List<Alumno>findStudents();

    @Query(value = "Select * from alumnos natural join grupos where (cursos_curso_de_ingles = :cursoDeIngles "
            + "and grupos_nombre_grupo=nombre_grupo)", nativeQuery = true)
    public List<Alumno> findStudentsByCourse(@Param("cursoDeIngles") String cursoDeIngles);
}
