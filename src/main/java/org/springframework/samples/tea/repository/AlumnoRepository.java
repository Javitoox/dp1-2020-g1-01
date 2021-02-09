package org.springframework.samples.tea.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.tea.model.Alumno;


public interface AlumnoRepository extends CrudRepository<Alumno, String> {

	@Query(value = "SELECT a FROM Alumno a WHERE a.nickUsuario = :nickUsuario OR a.dniUsuario = :nif")
	public Alumno findByNickAndNif(@Param("nickUsuario") String nickUsuario, @Param("nif") String nif);

	@Query("SELECT a FROM Alumno a JOIN a.grupos g WHERE g.nombreGrupo = :nombreGrupo and a.fechaMatriculacion IS NOT null and a.fechaBaja IS null")
    public List<Alumno> findByGroup(@Param("nombreGrupo") String nombreGrupo);

	@Query("SELECT alumno FROM Alumno alumno WHERE (alumno.fechaMatriculacion IS not null and alumno.fechaBaja IS null)")
	public List<Alumno>findStudents();

    @Query("SELECT alumno,grupo FROM Alumno alumno JOIN alumno.grupos grupo WHERE grupo.cursos.cursoDeIngles = :cursoDeIngles and alumno.fechaMatriculacion IS NOT null and alumno.fechaBaja IS null")
    public List<Alumno> findStudentsByCourse(@Param("cursoDeIngles") String cursoDeIngles);

    @Query("SELECT alumno FROM Alumno alumno where (alumno.tutores.nickUsuario LIKE :nickTutor% and alumno.fechaMatriculacion IS NOT null and alumno.fechaBaja IS null)")
    public List<Alumno> findStudentsByTutor(@Param("nickTutor")String nickTutor);

    @Query("SELECT a.nickUsuario FROM Alumno a WHERE a.fechaMatriculacion IS NOT null and a.fechaBaja IS null AND a.nickUsuario IN ( SELECT p.alumnos.nickUsuario FROM Pago p GROUP BY p.alumnos.nickUsuario "
			+ "HAVING COUNT (p.concepto) = (SELECT COUNT(DISTINCT p.concepto ) FROM Pago p) )")
	public List<String> findStudentsAbleToDelete();

    @Query("SELECT a.nickUsuario FROM Alumno a WHERE a.fechaMatriculacion IS NOT null and a.fechaBaja IS null AND a.nickUsuario NOT IN ( SELECT a.nickUsuario FROM Alumno a WHERE a.grupos.nombreGrupo <> '')")
	public List<String> findSudentsWithNoGroups();
}
