import React, { Component } from 'react'
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import { connect } from 'react-redux';
import AlumnoComponent from './AlumnoComponent';
import GrupoComponent from './GrupoComponent';
import { Dropdown } from 'primereact/dropdown';



class AssignStudent extends Component  {
   
    nickUsuario = this.nickUsuario.bind(this);
    alumnos = new AlumnoComponent();
    grupos = new GrupoComponent();
    handleNG = this.handleNG.bind(this);    
        state = { 
   
        nickUsuario:this.props.astudent.nickUsuario,
        contraseya: this.props.astudent.contraseya,
        dniUsuario: this.props.astudent.dniUsuario,
        nombreCompletoUsuario: this.props.astudent.nombreCompletoUsuario,
        correoElectronicoUsuario: this.props.astudent.correoElectronicoUsuario,
        numTelefonoUsuario: this.props.astudent.numTelefonoUsuario,
        numTelefonoUsuario2: this.props.astudent.numTelefonoUsuario2,
        direccionUsuario: this.props.astudent.direccionUsuario,
        fechaNacimiento: this.props.astudent.fechaNacimiento,
        numTareasEntregadas: this.props.astudent.numTareasEntregadas,
        fechaMatriculacion: this.props.astudent.fechaMatriculacion,
        fechaSolicitud: this.props.astudent.fechaSolicitud,
        fechaBaja: this.props.astudent.fechaBaja,
        tutores: {
            nickUsuario: this.props.astudent.tutores.nickUsuario,
            contraseya: this.props.astudent.tutores.nickUsuario,
            dniUsuario: this.props.astudent.tutores.nickUsuario,
            nombreCompletoUsuario: this.props.astudent.tutores.nickUsuario,
            correoElectronicoUsuario: this.props.astudent.tutores.nickUsuario,
            numTelefonoUsuario: this.props.astudent.tutores.nickUsuario,
            numTelefonoUsuario2: this.props.astudent.tutores.nickUsuario,
            direccionUsuario: this.props.astudent.tutores.nickUsuario,
            fechaNacimiento: this.props.astudent.tutores.nickUsuario,
            fechaMatriculacion: this.props.astudent.tutores.nickUsuario,
            fechaSolicitud: this.props.astudent.tutores.nickUsuario
        },
        grupos: {
            nombreGrupo: this.props.astudent.grupos.nombreGrupo,
            cursos: {
            cursoDeIngles: this.props.astudent.grupos.cursos.cursoDeIngles
        }
        },

        listaGrupos:{
           nombreGrupo: ""
        }   
        
    

      
    }
    nickUsuario(event) {
        this.setState({ nickUsuario: event.target.value });
    }

    handleNG(event) {
        this.setState({ nombreGrupo: event.target.value });
    }  
    
    componentDidMount() {
        this.grupos.getAllGroupNames().then(data => this.setState({ listaGrupos: data }));
        
    }
    
    allGroupNames(){

        var t=this.state.listaGrupos
        var i=0
        var groupSelectItems = [
            
        ];
        while(i<t.length){        
        groupSelectItems.push(         
            { label: String(t[i]) , value: String(t[i]) })        
        i+=1
        }
        return groupSelectItems
    }

    assign  = event => {
        event.preventDefault();
        const alumno ={
            nickUsuario:this.state.nickUsuario,
            contraseya: this.state.contraseya,
            dniUsuario: this.state.dniUsuario,
            nombreCompletoUsuario: this.state.nombreCompletoUsuario,
            correoElectronicoUsuario: this.state.correoElectronicoUsuario,
            numTelefonoUsuario: this.state.numTelefonoUsuario,
            numTelefonoUsuario2: this.state.numTelefonoUsuario2,
            direccionUsuario: this.state.direccionUsuario,
            fechaNacimiento: this.state.fechaNacimiento,
            numTareasEntregadas: this.state.numTareasEntregadas,
            fechaMatriculacion: this.state.fechaMatriculacion,
            fechaSolicitud: this.state.fechaSolicitud,
            fechaBaja: this.state.fechaBaja,
            tutores: {
                nickUsuario: this.state.tutores.nickUsuario,
                contraseya: this.state.tutores.nickUsuario,
                dniUsuario: this.state.tutores.nickUsuario,
                nombreCompletoUsuario: this.state.tutores.nickUsuario,
                correoElectronicoUsuario: this.state.tutores.nickUsuario,
                numTelefonoUsuario: this.state.tutores.nickUsuario,
                numTelefonoUsuario2: this.state.tutores.nickUsuario,
                direccionUsuario: this.state.tutores.nickUsuario,
                fechaNacimiento: this.state.tutores.nickUsuario,
                fechaMatriculacion: this.state.tutores.nickUsuario,
                fechaSolicitud: this.state.tutores.nickUsuario
            },
            grupos: {
                nombreGrupo: this.state.grupos.nombreGrupo,
                cursos: {
                    cursoDeIngles: this.state.grupos.cursos.cursoDeIngles
            }
            }
        }
        this.alumnos.assign(alumno).then(data => {
          this.setState({
            nickUsuario: "",
            nombreGrupo: ""
          });
        })
      }
    
    
    render() {
        console.log(this.props.student)
        return (
            <div>
                <div className="c">
                    <div className="login request">
                        <form onSubmit={this.assign}  >
                            <div className="t"><div><h5>Assign Student</h5></div></div>
                            <div className="i">
                                <div className="p-inputgroup">
                                    <InputText placeholder="Username" name="alumno.nickUsuario" type="text" value={this.state.nickUsuario} onChange={this.nickUsuario}  />
                                 </div>
                                 </div>

                                 <div className="i">
                                <div className="p-inputgroup">
                                    <Dropdown placeholder="Select a group" name="grupo.nombreGrupo" value={this.state.nombreGrupo} options={this.allGroupNames()} onChange={this.handleNG} />
                                </div>
                                </div>

                                <div className="b">
                                <div className="i">
                                    <Button className="p-button-secondary" label="Guardar" icon="pi pi-fw pi-check"/>

                                </div>
                            </div>

                        </form>
                    </div>
                </div>
            </div>
        );
    }
}

function mapStateToProps(state) { //metodo para poder pillar datos del store
    return {
        astudent: state.astudent //le pasamos a nuestra variable student la informacion del estudiante almacenada en el store
    }
}

export default connect(mapStateToProps)(AssignStudent); 