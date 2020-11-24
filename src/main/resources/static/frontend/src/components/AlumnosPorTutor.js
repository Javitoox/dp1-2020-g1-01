import React, { Component } from 'react'
import ExtraccionAlumnosByTutor from './ExtraccionAlumnosByTutor';
import {DataTable} from 'primereact/datatable';
import {Column} from 'primereact/column';

export  class AlumnosPorTutor extends Component {
    constructor(){
        super()
        this.state={
            nickUsuario:"marrambla2",
        }
        this.alumnosPorTutorComponent = new ExtraccionAlumnosByTutor();
    }
    componentDidMount(){
        this.alumnosPorTutorComponent.getAlumnosPorTutor().then(data => this.setState({alumnos:data}));
    }

    render(){
        console.log(this.state.alumnos);
        return(
            <div className="datatable-templating-demo">
            <div className="card"></div>
            <DataTable value={this.state.alumnos}>
                  <Column field="nickUsuario" header="NickUsuario"></Column>
                  <Column field="contraseya" header="Contraseya"></Column>
                  <Column field="dniUsuario" header="Dni Usuario"></Column>
                  <Column field="nombreCompletoUsuario" header="Nombre Completo"></Column>
                  <Column field="correoElectronicoUsuario" header="correo ElectronicoUsuario"></Column>
                  <Column field="numTelefonoUsuario" header="Número de Teléfono"></Column>
                  <Column field="direccionUsuario" header="Direccion"></Column>
                  <Column field="fechaNacimiento" header="Fecha Nacimiento"></Column>
                  <Column field="numTareasEntregadas" header="Num Tareas Entregadas"></Column>
                  <Column field="fechaMatriculacion" header="fecha Matriculacion"></Column>
            </DataTable>
            </div>
       )
    }
}