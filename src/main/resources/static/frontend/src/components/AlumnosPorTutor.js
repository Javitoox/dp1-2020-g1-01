import React, { Component } from 'react'
import AlumnoComponent from './AlumnoComponent';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import Auth from './Auth';
import { Button } from 'primereact/button';
import { Dialog } from 'primereact/dialog';
import axios from 'axios';


export class AlumnosPorTutor extends Component {
    constructor() {
        super()
        this.state = {
            rowDataInfo:null,
            comprobation: false,
        }

        this.alumnosPorTutorComponent = new AlumnoComponent();
        this.botonInfo = this.botonInfo.bind(this);
        this.mostrarInfoStudent= this.mostrarInfoStudent.bind(this);
        this.mostrarInfo= this.mostrarInfo.bind(this);

    }

    componentDidMount() {
        axios.get(this.props.urlBase + "/auth", {withCredentials: true}).then(res => {
        if(res.data==="tutor"){
            this.setState({comprobation: true})
        }
        })

        this.alumnosPorTutorComponent.getAlumnosPorTutor(this.props.urlBase, this.props.nickUser).then(data =>
            this.setState({ alumnos: data })
        );
    }

    botonInfo(rowData){
        return(
            <React.Fragment>
                <Button icon="pi pi-external-link" className="p-button-rounded p-button-secondary p-mr-2" onClick={() => this.mostrarInfoStudent(rowData)}/>
            </React.Fragment>


        );
    }

    mostrarInfoStudent(rowData){
        console.log(rowData)
        this.setState({rowDataInfo: rowData})
    }

    mostrarInfo(){
        console.log("Estoy entrando " + this.state.rowDataInfo);
        if(this.state.rowDataInfo != null){
          return(
            <Dialog header="Request' information"  visible={true} style={{ width: '30vw' }}  onHide={() => this.setState({rowDataInfo: null})}>
              <h4>Student data:</h4>
              <p><b>Full name:</b> {this.state.rowDataInfo.nombreCompletoUsuario}</p>
              <p><b>Nick:</b> {this.state.rowDataInfo.nickUsuario}</p>
              <p><b>Password</b> {this.state.rowDataInfo.contraseya}</p>
              <p><b>DNI:</b> {this.state.rowDataInfo.dniUsuario}</p>
              <p><b>Email:</b> {this.state.rowDataInfo.correoElectronicoUsuario}</p>
              <p><b>Birthdate:</b> {this.state.rowDataInfo.fechaNacimiento}</p>
              <p><b>Address:</b> {this.state.rowDataInfo.direccionUsuario}</p>
              <p><b>Phone number:</b> {this.state.rowDataInfo.numTelefonoUsuario}</p>
              <p><b>Activities done:</b> {this.state.rowDataInfo.numTareasEntregadas}</p>
              <p><b>Date of enrollment:</b> {this.state.rowDataInfo.fechaMatriculacion}</p>
  
            </Dialog>
            
          );
        }
    }

    render() {
        if (!this.state.comprobation) {
            return <Auth authority="tutor"></Auth>
        } else {
            return (
                <React.Fragment>
                <div className="datatable-templating-demo">
                    <DataTable value={this.state.alumnos}>
                        <Column field="nombreCompletoUsuario" header="Full name"></Column>
                        <Column field="dniUsuario" header="DNI"></Column>
                        <Column header="Info" body={this.botonInfo}></Column>
                    </DataTable>
                </div>
                {this.mostrarInfo()}
                </React.Fragment>

            )
        }
    }
}