import React, { Component } from 'react'
import AlumnoComponent from './AlumnoComponent';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { Redirect } from 'react-router-dom';

import GrupoComponent from './GrupoComponent';
import AssignmentComponent from './AssignmentComponent';
import axios from 'axios';
import Auth from './Auth';
import AssignTeacher from './AssginTeacher'
import { Dialog } from 'primereact/dialog';
import {DeleteAssignmentTeacher} from './DeleteAssignmentTeacher'


export default class TeacherGroups extends Component {

    constructor(props) {
        super(props);
        this.state = {
           
            redirect: false,
            nickUsuario: "",           
            listaGrupos:{
                nombreGrupo: ""
            },
            formularioAsignarProfesor:null,
            formularioDesasignarProfesor:null,
            comprobation: false,
            asigT:""
        }
        this.alumnos = new AlumnoComponent();
        this.asignaciones = new AssignmentComponent();
        this.grupos = new GrupoComponent();
        this.formAssignTeacher=this.formAssignTeacher.bind(this);
        this.mostrarTabla = this.mostrarTabla.bind(this);
        this.botonDelete = this.botonDelete.bind(this);
        this.formUnassignTeacher = this.formUnasssignTeacher.bind(this);       
    }

    componentDidMount() {
        axios.get("http://localhost:8081/auth", {withCredentials: true}).then(res => {
            if(res.data==="profesor"){
                this.setState({comprobation: true})
                }
            })
        this.mostrarTabla();
        this.grupos.getAllGroupNames().then(data => this.setState({ listaGrupos: data }));
    }

    mostrarTabla(){
        this.asignaciones.getListOfAssignment(this.props.urlBase, this.props.nickUser).then(data => this.setState({ alumnos: data }));
    }   

    formAssignTeacher() {
        this.setState({
            formularioAsignarProfesor: 
            <Dialog visible={true} style={{ width: '40vw' }} onHide={() => this.setState({formularioAsignarProfesor: null})}>
                <AssignTeacher urlBase={this.props.urlBase} nickUser={this.props.nickUser}></AssignTeacher>
            </Dialog>
        });    
    }

    formUnasssignTeacher(data) {
        this.setState({
            formularioDesasignarProfesor: 
            <Dialog visible={true} style={{ width: '40vw' }} onHide={() => this.setState({formularioDesasignarProfesor: null})}>
                <DeleteAssignmentTeacher urlBase={this.props.urlBase} data={data} nickUser={this.props.nickUser}></DeleteAssignmentTeacher>
            </Dialog>
        });    
    }

    botonDelete(rowData) {  
            return (    
                <React.Fragment>
                                      
                    <Button icon="pi pi-trash" className="p-button-rounded p-button-secondary p-mr-2"  onClick={() => this.formUnassignTeacher(rowData)}/>
                </React.Fragment>
            );    
     }

    render() {
        if (!this.state.comprobation) {
            return <Auth authority="teacher"></Auth>
        }else{        
            if (this.state.redirect) {
                if(this.state.redirect==="/assignTeacher"){
                    return <Redirect
                    to={{
                    pathname: "/assignTeacher"
                    }}
                />
                }          
            }
            return (
                <React.Fragment>
                    <div className="datatable-templating-demo">
                        <div>
                        {this.state.formularioAsignarProfesor}
                        {this.state.formularioDesasignarProfesor}                    
                        <Button icon="pi pi-plus-circle" label="Assign group" className="p-button-secondary" onClick={() => this.formAssignTeacher()} />
                        {` `}
                        </div>
                        <div>&nbsp;</div>
                        <DataTable value={this.state.alumnos}>
                            <Column field="grupo.nombreGrupo" header="Group"></Column>
                            <Column field="fecha" header="Date of assignment"></Column>
                            <Column header="Delete" body={this.botonDelete}></Column>

                        </DataTable>
                    </div>
                </React.Fragment>
            )
        }
    }
}
