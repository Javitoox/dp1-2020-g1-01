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

export default class TeacherGroups extends Component {

    constructor(props) {
        super(props);
        this.state = {
           
            redirect: false,
            nickUsuario: "",
           
            listaGrupos:{
                nombreGrupo: ""
            },
            comprobation: true,
        }
        this.alumnos = new AlumnoComponent();
        this.asignaciones = new AssignmentComponent();

        this.grupos = new GrupoComponent();
        this.botonGrupos=this.botonGrupos.bind(this);
       
    }

    componentDidMount() {
        /* axios.get("http://localhost:8081/auth").then(res => {
            if(res.data==="profesor"){
                this.setState({comprobation: true})
                }
            }) */
        this.asignaciones.getListOfAssignment(this.props.urlBase, this.props.nickUser).then(data => this.setState({ alumnos: data })).catch(error => this.setState({comprobation: false}));
        this.grupos.getAllGroupNames().then(data => this.setState({ listaGrupos: data }));

        //this.grupos.getAllGroups().then(data => this.setState({ groupSelectItems: data }));
        //this.cursos.getCourses().then(data => this.setState({ nodes: data }));
    }

    

    botonGrupos() {
        this.setState({ 
            redirect: "/assignTeacher",
        
    });
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
                    
                    
                        <Button icon="pi pi-plus-circle" label="Assign group" className="p-button-secondary" onClick={this.botonGrupos} />

                        </div>
                        <div>&nbsp;</div>

                        <DataTable value={this.state.alumnos}>
                            <Column field="grupo.nombreGrupo" header="Group"></Column>
                            <Column field="fecha" header="Date of assignment"></Column>
                        </DataTable>
                    </div>
                </React.Fragment>
            )
        }
    }
}
