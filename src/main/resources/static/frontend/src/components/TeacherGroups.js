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
import AssignmentComponent from './AssignmentComponent';

export default class TeacherGroups extends Component {

    constructor(props) {
        super(props);
        this.state = {
           
            redirect: false,
            nickUsuario: "",
           
            listaGrupos:{
                nombreGrupo: ""
            }

            //nodes: null,
            //selectedKey: null,
        }
        this.alumnos = new AlumnoComponent();
        this.asignaciones = new AssignmentComponent();

        this.grupos = new GrupoComponent();
        this.botonGrupos=this.botonGrupos.bind(this);
       
    }

    componentDidMount() {
        this.asignaciones.getListOfAssignment(this.props.urlBase, this.props.nickUser).then(data => this.setState({ alumnos: data }));
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
        console.log(this.state.alumnos);
    if (this.state.redirect) {
        if(this.state.redirect=="/assignTeacher"){
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

                    <DataTable value={this.state.alumnos}>
                        <Column field="grupo.nombreGrupo" header="Group"></Column>
                        <Column field="fecha" header="Date of assignment"></Column>
                    </DataTable>
                </div>
            </React.Fragment>
        )
    }
}
