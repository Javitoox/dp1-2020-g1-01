import React, { Component } from 'react'
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import { Dropdown } from 'primereact/dropdown';
import axios from 'axios';
import moment from 'moment';

import AlumnoComponent from './AlumnoComponent';

import GrupoComponent from './GrupoComponent';

import AssignmentComponent from './AssignmentComponent';
import Auth from './Auth';

export default class AssignTeacher extends Component {

    constructor(props) {
        super(props);
        this.state = {

        nickUsuario:this.props.nickUser,
        nombreGrupo:"",
        fecha:moment().format('YYYY-MM-DD'),
        listaGrupos:{
           nombreGrupo: ""
        },
        usernameError:null,
        nombreGrupoError:null,
        succes:null,
        exist:null,
        comprobation:true,
    } 

    this.handleNG = this.handleNG.bind(this);
    this.save = this.save.bind(this);
    this.alumnos = new AlumnoComponent();
    this.grupos = new GrupoComponent();
    this.asignaciones = new AssignmentComponent();
    this.form=this.form.bind(this);
    this.mostrarTabla = this.mostrarTabla.bind(this);


    }

    
    
    
      

    

    componentDidMount() {
        this.mostrarTabla()
        this.grupos.getAllGroupNames().then(data => this.setState({ listaGrupos: data }));
    }
    mostrarTabla(){
        this.asignaciones.getListOfAssignment(this.props.urlBase, this.props.nickUser).then(data => this.setState({ alumnos: data })).catch(error => this.setState({comprobation: false}));
    }   
    render() {
        if (!this.state.comprobation) {
            return <Auth authority="teacher"></Auth>
        } else {
            return (
                <div>
                    <div className="c">
                        <div className="login2 request2">
                            <form onSubmit={this.save}>
                                {this.state.succes}
                                {this.state.exist}
                                {this.form()}

                            </form>
                        </div>
                    </div>
                </div>
            );
        }
    }
}