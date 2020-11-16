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
@Table(name="alumno")

public class Alumno extends Usuario{
	
	@Column(name="numtareasentregadas")
	private Integer numtareasentregadas;
	
	@Column(name="fechamatriculacion")
	@NotBlank
	@NotEmpty
	@NotNull
	private String fechamatriculacion;
	
    @ManyToOne
    @JoinColumn(name = "nicktutor")
    private Tutor tutor;
    
    @OneToMany(mappedBy = "alumno")
    private List<Premiado> premiados;
    
    @ManyToOne
    @JoinColumn(name = "nombregrupo")
    private Grupo grupo;
    
    @OneToMany(mappedBy = "alumno")
    private List<Feedback> feedbacks;
    
    @ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "materialalumno", joinColumns = @JoinColumn(name = "nickusuario"),
			inverseJoinColumns = @JoinColumn(name = "id"))
	private Set<Material> materiales;
    
    @OneToMany(mappedBy = "alumno")
    private List<Pago> pagos;
}