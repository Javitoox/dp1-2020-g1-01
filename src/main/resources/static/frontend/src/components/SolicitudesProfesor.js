import React, { Component } from 'react'
import ExtraccionSolicitudes from './ExtraccionSolicitudes';
import {DataTable} from 'primereact/datatable';
import {Column} from 'primereact/column';

export  class SolicitudesProfesor extends Component {
    

    constructor(){
        super();
         this.state= {};
         this.solicitudesComponent = new ExtraccionSolicitudes();
      }
      componentDidMount(){
        this.solicitudesComponent.getSolicitudes().then(data => this.setState({solicitudes:data}));
 
      }
	
    render() {
        return (
          <React.Fragment>
				<h2 style={{ color: 'white' }}>LISTA DE ALUMNOS</h2>
        <DataTable value={this.state.solicitudes}> 
		            <Column field="nickUsuario" header="Nombre"></Column>
		            <Column field="fechaSolicitud" header="Fecha de la solicitud"></Column>
		        </DataTable>
			</React.Fragment>

        )
    }
    
}


