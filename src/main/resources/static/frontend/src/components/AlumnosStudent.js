import React, { Component } from 'react'
import AlumnoComponent from './AlumnoComponent';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import GrupoComponent from './GrupoComponent';
import { Dialog } from 'primereact/dialog';
import AssignmentComponent from './AssignmentComponent';
import "../css/students.css";


export class AlumnosStudent extends Component {

    constructor(props) {
        super(props);
        this.state = {
            alumno:{
            nickUsuario: "",
            contraseya: "",
            dniUsuario: "",
            nombreCompletoUsuario: "",
            correoElectronicoUsuario:"",
            numTelefonoUsuario: "",
            numTelefonoUsuario2: "",
            direccionUsuario: "",
            fechaNacimiento: "",
            numTareasEntregadas:"",
            fechaMatriculacion: "",
            fechaSolicitud:"",
            fechaBaja:"",
            tutores:{
                nickUsuario: "",
                contraseya: "",
                dniUsuario: "",
                nombreCompletoUsuario: "",
                correoElectronicoUsuario: "",
                numTelefonoUsuario: "",
                numTelefonoUsuario2: "",
                direccionUsuario: "",
                fechaNacimiento: "",
            },
            grupos: {
                nombreGrupo: "",
                cursos: {
                    cursoDeIngles:""
            }
            }
            },
            rowDataInfo:null,
            comprobation: true,
            listaGrupos:{
                nombreGrupo: ""
            },
            profesor:""
            
        }
        this.alumnos = new AlumnoComponent();
        this.grupos = new GrupoComponent();
        this.asig= new AssignmentComponent();
        this.botonInfo= this.botonInfo.bind(this);
        this.mostrarInfoStudent= this.mostrarInfoStudent.bind(this);
        this.mostrarInfo= this.mostrarInfo.bind(this);
        this.mostrarTabla = this.mostrarTabla.bind(this);
    }

    componentDidMount() {
        this.alumnos.getAlumnoInfo(this.props.urlBase, this.props.nickUser).then(data => this.setState({ alumno: data })).catch(error => this.setState({ comprobation: false }));
        this.mostrarTabla();
        this.alumnos.getAlumnoInfo(this.props.urlBase, this.props.nickUser).then(data => this.asig.getTeacherByGroup(this.props.urlBase,data.grupos.nombreGrupo).then(data => this.setState({ profesor: data })));

    }
 
    
    mostrarTabla(){
        this.alumnos.getAlumnoInfo(this.props.urlBase, this.props.nickUser).then(data => this.alumnos.getStudentsByNameOfGroup(this.props.urlBase, data.grupos.nombreGrupo).then(data => this.setState({ alumnos: data })));
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
              <p><b>Full name:</b> {this.state.rowDataInfo.nombreCompletoUsuario}</p>
              <p><b>Email:</b> {this.state.rowDataInfo.correoElectronicoUsuario}</p>
              <p><b>Birthdate:</b> {this.state.rowDataInfo.fechaNacimiento}</p>
              </Dialog>
            
          );
        }
      }
      teacher(){
          if(this.state.profesor.length===1){
              return <div>  {this.state.profesor}</div>


          }else{
            var t = this.state.profesor
            var i =0
            var s="";
            while(i<t.length){
                if(i===(t.length-1)){
                    s+=(String(t[i]))

                }else{
                    s+=(String(t[i])+",")
                }
                i+=1
            }
            return s
          }


      }
      info(){
          if(this.state.alumno.grupos.nombreGrupo!==null && this.state.profesor!==null){
            return <div>
            <h5>Group:</h5> {this.state.alumno.grupos.nombreGrupo}
            <h5>Course:</h5> {this.state.alumno.grupos.cursos.cursoDeIngles}
            <h5>Teacher:</h5> {this.teacher()}
            <h5>Number of homeworks delivered:</h5> {this.state.alumno.numTareasEntregadas}
            <h5>Enrolment date:</h5> {this.state.alumno.fechaMatriculacion}
            </div>
          }else if(this.state.alumno.grupos.nombreGrupo===null){
            return <div>
            <h5>Group:</h5> --
            <h5>Course:</h5> --
            <h5>Teacher:</h5> --
            <h5>Number of homeworks delivered:</h5> {this.state.alumno.numTareasEntregadas}
            <h5>Enrolment date:</h5> {this.state.alumno.fechaMatriculacion}
            </div>
          }else if(this.state.alumno.grupos.nombreGrupo!==null && this.state.profesor===null){
            return <div>
            <h5>Group:</h5> {this.state.alumno.grupos.nombreGrupo}
            <h5>Course:</h5> {this.state.alumno.grupos.cursos.cursoDeIngles}
            <h5>Teacher:</h5> --
            <h5>Number of homeworks delivered:</h5> {this.state.alumno.numTareasEntregadas}
            <h5>Enrolment date:</h5> {this.state.alumno.fechaMatriculacion}
            </div>
          }
      }

      tutor(){
          if(this.state.alumno.tutores.nombreCompletoUsuario!==null){
            return  <div>
            <h5>Full name:</h5> {this.state.alumno.tutores.nombreCompletoUsuario}
            <h5>Bitrhdate:</h5> {this.state.alumno.tutores.fechaNacimiento}
            </div>

          }else{
             return <div>
               <h5>Full name:</h5> --
               <h5>Bitrhdate:</h5> --
               </div>

          }
      }

    

    render() {
        const paginatorLeft = <Button type="button" icon="pi pi-refresh" className="p-button-text" />;
        const paginatorRight = <Button type="button" icon="pi pi-cloud" className="p-button-text" />;
                return (
                    <React.Fragment>
                        <div className="container"></div>
                        <div className="row justify-content-start">
                            <div>&nbsp;</div>
                            <div className="col-8">
                            <DataTable value={this.state.alumnos} paginator
                                paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink RowsPerPageDropdown"
                                currentPageReportTemplate="Showing {first} to {last} of {totalRecords}" rows={10} rowsPerPageOptions={[5,10,20]}
                                paginatorLeft={paginatorLeft} paginatorRight={paginatorRight}>
                                <Column header="Info" body={this.botonInfo}></Column>
                                <Column field="nombreCompletoUsuario" header="Full name"></Column>
                            </DataTable>
                            <div>&nbsp;</div>
                            <div className="hijo col-12"><div><div className="gi"><h5>Tutor's info:</h5></div>
                                                        {this.tutor()}
                                                        
                                                        <div>&nbsp;</div></div></div>
                            </div>


                        <div className="hijo col-3"><div><div className="gi"><h5>Group's info:</h5></div>
                                                        {this.info()}
                                                       <div>&nbsp;</div></div></div></div>
                        {this.mostrarInfo()}
                    </React.Fragment>
                )
            }
        }
    