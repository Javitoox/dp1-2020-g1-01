import React, { Component } from 'react'
import AlumnoComponent from './AlumnoComponent';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import GrupoComponent from './GrupoComponent';
import { Dialog } from 'primereact/dialog';

export class AlumnosStudent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            curso: "allCourses",
            grupo: "allGroups",
            nickUsuario: "",
            contraseya: "",
            dniUsuario: "",
            nombreCompletoUsuario: "",
            correoElectronicoUsuario: "",
            numTelefonoUsuario: "",
            direccionUsuario: "",
            fechaNacimiento:"",
            numTareasEntregadas :"",
            fechaMatriculacion: "",
            groupSelectItems: "",
            rowDataInfo:null,
            comprobation: false,
            listaGrupos:{
                nombreGrupo: ""
            }
            
        }
        this.alumnos = new AlumnoComponent();
        this.grupos = new GrupoComponent();
        this.botonInfo= this.botonInfo.bind(this);
        this.mostrarInfoStudent= this.mostrarInfoStudent.bind(this);
        this.mostrarInfo= this.mostrarInfo.bind(this);
        this.mostrarTabla = this.mostrarTabla.bind(this);
    }

    componentDidMount() {
        this.mostrarTabla();
    }
 
    
    mostrarTabla(){
        this.alumnos.getStudentsByNameOfGroup(this.props.urlBase,'grupo1').then(data => this.setState({ alumnos: data }));
    }

    botonInfo(rowData){
        return(
            <React.Fragment>
                <Button icon="pi pi-external-link" className="p-button-rounded p-button-secondary p-mr-2" onClick={() => this.mostrarInfoStudent(rowData)}/>
            </React.Fragment>


        );
    }

    mostrarInfoStudent(rowData){
        this.setState({rowDataInfo: rowData})
    }

    mostrarInfo(){
        if(this.state.rowDataInfo != null){
          return(
            <Dialog header="Student' information"  visible={true} style={{ width: '30vw' }}  onHide={() => this.setState({rowDataInfo: null})}>
              <h4>Student data:</h4>
              <p><b>Nick:</b> {this.state.rowDataInfo.nickUsuario}</p>
              <p><b>DNI:</b> {this.state.rowDataInfo.dniUsuario}</p>
              <p><b>Full name:</b> {this.state.rowDataInfo.nombreCompletoUsuario}</p>
              <p><b>Email:</b> {this.state.rowDataInfo.correoElectronicoUsuario}</p>
              <p><b>Birthdate:</b> {this.state.rowDataInfo.fechaNacimiento}</p>
              <p><b>Address:</b> {this.state.rowDataInfo.direccionUsuario}</p>
              <p><b>Phone number:</b> {this.state.rowDataInfo.numTelefonoUsuario}</p>
              </Dialog>
            
          );
        }
      }

    

    render() {
            console.log(this.state)

                const courseSelectItems = [
                    { label: 'All courses', value: 'allCourses' },
                    { label: 'A1', value: 'A1' },
                    { label: 'A2', value: 'A2' },
                    { label: 'B1', value: 'B1' },
                    { label: 'B2', value: 'B2' },
                    { label: 'C1', value: 'C1' },
                    { label: 'C2', value: 'C2' },
                    { label: 'Free learning', value: 'APRENDIZAJELIBRE' }
                ];
                return (
                    <React.Fragment>

                        <div className="datatable-templating-demo">
                            <div>&nbsp;</div>
                            <DataTable value={this.state.alumnos}>
                                <Column header="Info" body={this.botonInfo}></Column>
                                <Column field="nombreCompletoUsuario" header="Full name"></Column>
                                <Column field="nickUsuario" header="Nickname"></Column>
                            </DataTable>
                        </div>
                        {this.mostrarInfo()}
                    </React.Fragment>
                )
            }
        }
    