package org.springframework.samples.petclinic.model;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="material")
public class Material extends BaseEntity{
	
	@Enumerated(EnumType.STRING)
    @Column(name="tipomaterial")
    private TipoMaterial tipoMaterial;

    @Column(name="nombrematerial")
    private String nombreMaterial;

    @ManyToOne
    @JoinColumn(name="nickusuario")
    private Profesor profesor;
    
    @OneToMany(mappedBy = "material")
    private List<Feedback> feedbacks;
    
    @ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "materialalumno", joinColumns = @JoinColumn(name = "id"),
			inverseJoinColumns = @JoinColumn(name = "nickusuario"))
	private Set<Alumno> alumnos;
    
    @ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "cursomaterial", joinColumns = @JoinColumn(name = "id"),
			inverseJoinColumns = @JoinColumn(name = "cursodeingles"))
	private Set<Curso> cursos;
}