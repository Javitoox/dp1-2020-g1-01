package org.springframework.samples.petclinic.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Data
@Table(name="alumnos")

public class Alumno extends Usuario{
	
	@Column(name="num_tareas_entregadas")
	private Integer numTareasEntregadas;
	
	@Column(name="fecha_matriculacion")
	@NotBlank
	@NotEmpty
	@NotNull
	private String fechaMatriculacion;
	
    @ManyToOne
    @JoinColumn(name = "nick_tutor")
    private Tutor tutores;
    
    @OneToMany(mappedBy = "alumnos")
    private List<Premiado> premiados;
    
    
    @OneToMany(mappedBy = "alumnos")
    private List<Feedback> feedbacks;
    
    @ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "material_alumno", joinColumns = @JoinColumn(name = "nick_usuario"),
			inverseJoinColumns = @JoinColumn(name = "id"))
	private Set<Material> materiales;
    
    @OneToMany(mappedBy = "alumnos")
    private List<Pago> pagos;
}