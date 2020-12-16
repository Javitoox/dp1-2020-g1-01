import React, { Component } from 'react'
import MetodosTutor from './MetodosTutor';
import {DataTable} from 'primereact/datatable';
import {Column} from 'primereact/column';

export  class AlumnosPorTutor extends Component {
    constructor(){
        super()
        this.state={
            
        }
        this.alumnosPorTutorComponent = new MetodosTutor();
    }
    componentDidMount(){
        console.log(this.props.nickUser);
        this.alumnosPorTutorComponent.getAlumnosPorTutor(this.props.urlBase, this.props.nickUser).then(data => this.setState({alumnos:data}));
    }

    render(){
        console.log(this.state.alumnos);
        return(
            <div className="datatable-templating-demo">
            <DataTable value={this.state.alumnos}>
                  <Column field="nickUsuario" header="Nick User"></Column>
                  <Column field="contraseya" header="Password"></Column>
                  <Column field="dniUsuario" header="DNI"></Column>
                  <Column field="nombreCompletoUsuario" header="Full name"></Column>
                  <Column field="correoElectronicoUsuario" header="Email"></Column>
                  <Column field="numTelefonoUsuario" header="Phone number"></Column>
                  <Column field="direccionUsuario" header="Address"></Column>
                  <Column field="fechaNacimiento" header="Birthdate"></Column>
                  <Column field="numTareasEntregadas" header="Activities done"></Column>
                  <Column field="fechaMatriculacion" header="Date of enrollment"></Column>
            </DataTable>
            </div>
       )
    }
}