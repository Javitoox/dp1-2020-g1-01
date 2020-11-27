import React, { Component } from 'react'
import ExtraccionSolicitudes from './ExtraccionSolicitudes';
import {DataTable} from 'primereact/datatable';
import {Column} from 'primereact/column';
import {Button} from 'primereact/button';


export  class SolicitudesProfesor extends Component {
    

    constructor(){
      super();
         this.state= {};
         this.solicitudesComponent = new ExtraccionSolicitudes();
      }
    componentDidMount(){
      this.solicitudesComponent.getSolicitudes(this.props.urlBase).then(data => this.setState({solicitudes:data}));
 
      }
  
    actionBodyTemplate(){
      return (
        <React.Fragment>
            <Button  />
            <Button  />
        </React.Fragment>
       )
    }

    render() {
      const header = (
        <div className="table-header">
            Requests
        </div>
    );
      const footer = `There're ${this.state.solicitudes ? this.state.solicitudes.length : 0} requests.`;
        return (
          <div className="datatable-templating-demo">
          <div className="card">
              <DataTable value={this.state.solicitudes} header={header} footer={footer}>
                  <Column field="nickUsuario" header="Username"></Column>
                  <Column field="dniUsuario" header="DNI"></Column>
                  <Column field="nombreCompletoUsuario" header="Name"></Column>
                  <Column field="fechaSolicitud" header="Request date"></Column>
                  <Column field="nickUsuarioTutor" header="Tutor username"></Column>
                  <Column body={this.actionBodyTemplate}></Column>
              </DataTable>
          </div>
          </div>
        )
    }
    
}


