import React, { Component } from 'react'
import ExtraccionSolicitudes from './ExtraccionSolicitudes';
import {DataTable} from 'primereact/datatable';
import {Column} from 'primereact/column';
import {Button} from 'primereact/button';
import { Dialog } from 'primereact/dialog';
import Auth from './Auth';

export  class SolicitudesProfesor extends Component {
    
    constructor(){
      super();
         this.state= {
          redirect:false,
          rowDataInfo:null,
          comprobation: true
         };
         
         this.solicitudesComponent = new ExtraccionSolicitudes();
         this.delete= this.delete.bind(this);
         this.accept= this.accept.bind(this);
         this.boton= this.boton.bind(this);
         this.mostrarTabla= this.mostrarTabla.bind(this);
         this.mostrarDatosTutor = this.mostrarDatosTutor.bind(this);
         this.mostrarInfoStudent = this.mostrarInfoStudent.bind(this);
         this.mostrarInfoRequest = this.mostrarInfoRequest.bind(this);
      }
     
    componentDidMount(){      
      this.mostrarTabla();
    }
    
    
    boton(rowData){
      return (    
        <React.Fragment>
            <Button className="mr-3" label="Deny"  onClick={() => this.delete(rowData)} />
            <Button className="mr-3" label="Accept"  onClick={() => this.accept(rowData)} />
            <Button label="Info"  icon="pi pi-external-link" onClick={() => this.mostrarInfoStudent(rowData)} />
            
        </React.Fragment>)
    }

    mostrarInfoStudent(rowData){
      console.log(rowData)
      this.setState({rowDataInfo: rowData})
    }

    mostrarInfoRequest(){
      console.log("Estoy entrando " + this.state.rowDataInfo);
      if(this.state.rowDataInfo != null){
        return(
          <Dialog header="Request' information"  visible={true} style={{ width: '30vw' }}  onHide={() => this.setState({rowDataInfo: null})}>
            <h4>Student data:</h4>
            <p><b>Nick:</b> {this.state.rowDataInfo.nickUsuario}</p>
            <p><b>DNI:</b> {this.state.rowDataInfo.dniUsuario}</p>
            <p><b>Full name:</b> {this.state.rowDataInfo.nombreCompletoUsuario}</p>
            <p><b>Email:</b> {this.state.rowDataInfo.correoElectronicoUsuario}</p>
            <p><b>Birthdate:</b> {this.state.rowDataInfo.fechaNacimiento}</p>
            <p><b>Address:</b> {this.state.rowDataInfo.direccionUsuario}</p>
            <p><b>Phone number:</b> {this.state.rowDataInfo.numTelefonoUsuario}</p>

            {this.mostrarDatosTutor(this.state.rowDataInfo)}
          </Dialog>
          
        );
      }
    }

    mostrarDatosTutor(rowData){
      if(rowData.tutores != null){
        return(
          <React.Fragment>
            <h4>Tutor data:</h4>
            <p><b>Nick:</b> {rowData.tutores.nickUsuario}</p>
            <p><b>DNI:</b> {rowData.tutores.dniUsuario}</p>
            <p><b>Full name:</b> {rowData.tutores.nombreCompletoUsuario}</p>
            <p><b>Email:</b> {rowData.tutores.correoElectronicoUsuario}</p>
            <p><b>Birthdate:</b> {rowData.tutores.fechaNacimiento}</p>
            <p><b>Address:</b> {rowData.tutores.direccionUsuario}</p>
            <p><b>Phone number:</b> {rowData.tutores.numTelefonoUsuario}</p>

          </React.Fragment>
        );
      }
    }


    mostrarTabla(){
      this.solicitudesComponent.getSolicitudes(this.props.urlBase).then(data => this.setState({solicitudes:data})).catch(error => this.setState({comprobation: false}));
    }

    async delete(rowData){
      var result = window.confirm("Are you sure you want to decline the request?");
      if(result){
        await this.solicitudesComponent.denyRequest(this.props.urlBase, rowData.nickUsuario);
        this.mostrarTabla();
      } 
   }
   
    async accept(rowData){
      var result = window.confirm("Are you sure you want to accept the request?");
      if(result){
        await this.solicitudesComponent.acceptRequest(this.props.urlBase, rowData.nickUsuario);
        this.mostrarTabla();
      }
 }

    render() {

      if (!this.state.comprobation) {
        return <Auth authority="teacher"></Auth>
      } else {
        const header = (
          <div className="table-header">
              Requests
          </div>
        );
          const footer = `There're ${this.state.solicitudes ? this.state.solicitudes.length : 0} pending requests.`;
            return (
              <div className="datatable-templating-demo">
              <div className="card">
                  <DataTable value={this.state.solicitudes} header={header} footer={footer}>
                      <Column field="nombreCompletoUsuario" header="Name"></Column>
                      <Column field="fechaSolicitud" header="Request date"></Column>
                      <Column body={this.boton}></Column>
                  </DataTable>
                  {this.mostrarInfoRequest()}

              </div>
              </div>
            )
      }
    }
    
}


