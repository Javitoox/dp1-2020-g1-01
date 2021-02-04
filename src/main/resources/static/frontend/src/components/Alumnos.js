import React, { Component } from 'react'
import AlumnoComponent from './AlumnoComponent';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { Redirect } from 'react-router-dom';
import { ListBox } from 'primereact/listbox';
import GrupoComponent from './GrupoComponent';
import {selectStudent} from '../actions/index';
import {selectAssignedStudent}  from '../actions/index';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';
import { Dialog } from 'primereact/dialog';
import axios from 'axios';
import Auth from './Auth';
import {CreateGroup} from './CreateGroup';
import {DeleteGroup} from './DeleteGroup'
import AssignStudent from './AssignStudent';



class Alumnos extends Component {

    constructor(props) {
        super(props);
        this.state = {
            curso: "allCourses",
            grupo: "allGroups",
            redirect: false,
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
            formularioCrearGrupo: null,
            formularioDeleteGrupo: null,
            formularioAssginStudent:null,
            listaGrupos:{
                nombreGrupo: ""
            },
            listaEliminables:{
               
            },
            listaSinGrupos:{
               
            },
            listaAllGrupos:{
               
            }
            
        }
        this.alumnos = new AlumnoComponent();
        this.boton = this.boton.bind(this);
        this.grupos = new GrupoComponent();
        this.botonAssign=this.botonAssign.bind(this);
        this.formCreateGrupo=this.formCreateGrupo.bind(this);
        this.formDeleteGrupo=this.formDeleteGrupo.bind(this);
        this.botonGrupos=this.botonGrupos.bind(this);
        this.allGroupNames= this.allGroupNames.bind(this);
        this.botonDelete= this.botonDelete.bind(this);
        this.botonInfo= this.botonInfo.bind(this);
        this.mostrarInfoStudent= this.mostrarInfoStudent.bind(this);
        this.mostrarInfo= this.mostrarInfo.bind(this);
        this.mostrarDatosTutor= this.mostrarDatosTutor.bind(this);
        this.mostrarTabla = this.mostrarTabla.bind(this);
        this.botonDelete = this.botonDelete.bind(this);
        this.formAssignStudent = this.formAssignStudent.bind(this);
    }

    componentDidMount() {
        axios.get(this.props.urlBase + "/auth", {withCredentials: true}).then(res => {
        if(res.data==="profesor"){
            this.setState({comprobation: true})
        }
        })
        this.mostrarTabla();
        this.alumnos.getAlumnosEliminiables(this.props.urlBase).then(data =>  this.setState({ listaEliminables: data }) );
        this.alumnos.getAlumnosSinGrupo(this.props.urlBase).then(data =>  this.setState({ listaSinGrupos: data }) );
    }

    boton(rowData) {
      
        return (    
            <React.Fragment>
                <Button icon="pi pi-pencil" className="p-button-rounded p-button-secondary p-mr-2" onClick={()=> this.edicion(rowData)} />
            </React.Fragment>
        );
    }

    formCreateGrupo() {
        this.setState({
            formularioCrearGrupo: 
            <Dialog visible={true} style={{ width: '40vw' }} onHide={() => this.setState({formularioCrearGrupo: null})}>
                <CreateGroup urlBase={this.props.urlBase}></CreateGroup>
            </Dialog>
        });

    
    }

    formDeleteGrupo() {
        this.setState({
            formularioDeleteGrupo: 
            <Dialog visible={true} style={{ width: '40vw'}} onHide={() => this.setState({formularioDeleteGrupo: null})}>
                <DeleteGroup urlBase={this.props.urlBase}></DeleteGroup>
            </Dialog>
        });
    }

     
     formAssignStudent(data) {
        this.props.selectAssignedStudent(data)
        this.setState({
            formularioAssginStudent: 
            <Dialog visible={true} style={{ width: '40vw'}} onHide={() => this.setState({formularioAssginStudent: null})}>
                <AssignStudent urlBase={this.props.urlBase} list={this.state.listaSinGrupos} listaAll={this.allGroupNames()} tut={''}></AssignStudent>
            </Dialog>
        });
        this.mostrarTabla();
}

    botonGrupos() {
        this.setState({ 
            redirect: "/teacherGroups",
        
    });
    }

    edicion(data) {
        this.props.selectStudent(data) //si os dice que selectStudent no es una funcion comprobad los nombres en matchDispatchToProps y que el import este hecho con el nombre ENTRE LLAVES
        this.setState({ 
            redirect: "/editStudent",
        
    });
}
     
    botonAssign(rowData) {
        return (    
            <React.Fragment>
                <Button icon="pi pi-plus-circle" className="p-button-rounded p-button-secondary p-mr-2" onClick={() => this.formAssignStudent(rowData)} />
            </React.Fragment>
        );
    }   
    
    showSelectCourse(course) {
        if (course !== null) {

            this.setState({ curso: course });
            if (course === "allCourses") {
                this.alumnos.getAllStudents(this.props.urlBase).then(data => this.setState({ alumnos: data }));
                this.setState({ listaGrupos: "" });

            } else {
                this.alumnos.getStudentsByCourse(this.props.urlBase, course).then(data => this.setState({ alumnos: data }));
                this.grupos.getGroupNamesByCourse(course).then(data => this.setState({ listaGrupos: data }));

            }
        }
    }
    showSelectGroup(group) {
        if (group !== null) {
            this.setState({ grupo: group });
            if (group === "allGroups") {
                this.setState({ listaGrupos: "" });
                this.alumnos.getAllStudents(this.props.urlBase).then(data => this.setState({ alumnos: data }));
            } else {
                this.alumnos.getStudentsByNameOfGroup(this.props.urlBase, group).then(data => this.setState({ alumnos: data }));
            }
        }
    }   
    
    mostrarTabla(){
        this.alumnos.getAllStudents(this.props.urlBase).then(data => this.setState({ alumnos: data }));
    }

    botonDelete(rowData) {
        var s= this.state.listaEliminables
        var list=[];
        var i=0
        while(i<s.length){
            list.push(s[i]);
            i+=1
        }
        if(list.includes(String(rowData.nickUsuario))){            
            return (    
                <React.Fragment>
                                      
                    <Button icon="pi pi-trash" className="p-button-rounded p-button-secondary p-mr-2"  onClick={() => this.deleteAlumno(rowData)}/>
                </React.Fragment>
            );
        }
     }     
        
    async deleteAlumno(data){
        var result = window.confirm("Are you sure you want to delete the student?");
        if(result){
            await this.alumnos.deleteAlumno(this.props.urlBase, data.nickUsuario);
            this.mostrarTabla()
          }
    }
    
    allGroupNames(){
        var t=this.state.listaGrupos
        var i=0
        var groupSelectItems = [
            { label: 'All groups' , value: 'allGroups' },
        ];
        while(i<t.length){        
        groupSelectItems.push(         
            { label: String(t[i]) , value: String(t[i]) })        
        i+=1
        }
        return groupSelectItems
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


    render() {
        if (!this.state.comprobation) {
            return <Auth authority="teacher"></Auth>
        } else {
            if (this.state.redirect) {

                if(this.state.redirect==="/editStudent"){
                    return <Redirect
                    to={{
                    pathname: "/editStudent"
                    }}
                /> 
                
                }else if(this.state.redirect==="/teacherGroups"){
                    return <Redirect
                    to={{
                    pathname: "/teacherGroups"
                    }}
                />
                }
                
            }

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

            const paginatorLeft = <Button type="button" icon="pi pi-refresh" className="p-button-text" />;
            const paginatorRight = <Button type="button" icon="pi pi-cloud" className="p-button-text" />;

                return (
                    <React.Fragment>

                        <div className="datatable-templating-demo">
                        {this.state.formularioCrearGrupo} 
                        {this.state.formularioDeleteGrupo}  
                        {this.state.formularioAssginStudent}

                            <div>
                            <ListBox value={this.state.curso} options={courseSelectItems} onChange={(e) => this.showSelectCourse(e.value)} />
                            <div>&nbsp;</div>
                        
                            <ListBox options={this.allGroupNames()} onChange={(e) => this.showSelectGroup(e.value)} />
                            <div>&nbsp;</div>
                            <Button icon="pi pi-plus-circle" label="Create group" className="p-button-secondary" onClick={()=>this.formCreateGrupo()} />
                            {` `}
                            <Button icon="pi pi-minus-circle" label="Delete group" className="p-button-secondary" onClick={()=>this.formDeleteGrupo()} />
                            {` `}
                            <Button icon="pi pi-fw pi-users" label="My groups" className="p-button-secondary" onClick={this.botonGrupos} />

                            </div>
                            <div>&nbsp;</div>
                            <DataTable value={this.state.alumnos} paginator
                            paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink RowsPerPageDropdown"
                            currentPageReportTemplate="Showing {first} to {last} of {totalRecords}" rows={10} rowsPerPageOptions={[5,10,20]}
                            paginatorLeft={paginatorLeft} paginatorRight={paginatorRight}>

                                <Column header="Info" body={this.botonInfo}></Column>
                                <Column field="nombreCompletoUsuario" header="Full name"></Column>
                                <Column field="nickUsuario" header="Nickname"></Column>
                                <Column field="grupos.nombreGrupo" header="Group's name"></Column>
                                <Column field="numTareasEntregadas" header="Activities done"></Column>
                                <Column header="Assign" body={this.botonAssign}></Column>
                                <Column header="Edit" body={this.boton}></Column>
                                <Column header="Delete" body={this.botonDelete}></Column>
                            </DataTable>
                        </div>
                        {this.mostrarInfo()}
                    </React.Fragment>
                )
            }
        }
    }

    function  matchDispatchToProps(dispatch) {

        return bindActionCreators({
            selectStudent : selectStudent,
            selectAssignedStudent: selectAssignedStudent}, dispatch)
    }

    export default connect(null , matchDispatchToProps)(Alumnos) 