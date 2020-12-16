import React, { Component } from 'react'
import ExtraccionSolicitudes from './ExtraccionSolicitudes';
import {DataTable} from 'primereact/datatable';
import {Column} from 'primereact/column';
import {Button} from 'primereact/button';

export  class SolicitudesProfesor extends Component {
    

    constructor(){
      super();
         this.state= {
          redirect:false
         };
         this.solicitudesComponent = new ExtraccionSolicitudes();
         this.delete= this.delete.bind(this);
         this.accept= this.accept.bind(this);
         this.boton= this.boton.bind(this);
         this.mostrarTabla= this.mostrarTabla.bind(this);
      }
    componentDidMount(){
        this.mostrarTabla();
      }
    
    boton(rowData){
      return (    
        <React.Fragment>
            <Button label="Deny"  onClick={() => this.delete(rowData)} />
            <Button label="Accept"  onClick={() => this.accept(rowData)} />
        </React.Fragment>)
    }

    mostrarTabla(){
      this.solicitudesComponent.getSolicitudes(this.props.urlBase).then(data => this.setState({solicitudes:data}));
    }

    delete(rowData){
      this.solicitudesComponent.denyRequest(rowData.nickUsuario);
      this.mostrarTabla();
   }
   
   accept(rowData){
    this.solicitudesComponent.acceptRequest(rowData.nickUsuario);
    this.mostrarTabla();
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
                  <Column field="tutores.nickUsuarioTutor" header="Tutor username"></Column>
                  <Column body={this.boton}></Column>
              </DataTable>
          </div>
          </div>
        )
    }
    
}


