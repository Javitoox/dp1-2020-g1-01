package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Alumno;


public interface AlumnoRepository extends CrudRepository<Alumno, String> {
	
	@Query(value = "Select * from alumnos where (alumnos.nick_usuario = :nickUsuario)", nativeQuery = true)
	public Alumno findByNick(@Param("nickUsuario") String nickUsuario);
	
	@Query("SELECT a FROM Alumno a JOIN a.grupos g WHERE g.nombreGrupo = :nombreGrupo")
    public List<Alumno> findByGroup(@Param("nombreGrupo") String nombreGrupo);
	
	@Query("SELECT alumno FROM Alumno alumno WHERE alumno.fechaMatriculacion IS not null")
	public List<Alumno>findStudents();

    @Query("SELECT alumno,grupo FROM Alumno alumno JOIN alumno.grupos grupo WHERE grupo.cursos.cursoDeIngles LIKE :cursoDeIngles%")
    public List<Alumno> findStudentsByCourse(@Param("cursoDeIngles") String cursoDeIngles);
    
    @Query("SELECT alumno FROM Alumno alumno where (alumno.tutores.nickUsuario LIKE :nickTutor% and alumno.fechaMatriculacion IS NOT null)")
    public List<Alumno> findStudentsByTutor(@Param("nickTutor")String nickTutor);    
}
