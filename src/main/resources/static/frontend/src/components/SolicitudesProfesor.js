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
      const header = (
        <div className="table-header">
            Requests
        </div>
    );
    const footer = `There's ${this.state.solicitudes ? this.state.solicitudes.length : 0} requests.`;
        return (
          <div className="datatable-templating-demo">
          <div className="card">
              <DataTable value={this.state.solicitudes} header={header} footer={footer}>
                  <Column field="nickUsuario" header="Name"></Column>
                  <Column field="fechaSolicitud" header="Application date"></Column>
              </DataTable>
          </div>
          </div>
        )
    }
    
}


