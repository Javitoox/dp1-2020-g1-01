import React, { Component } from 'react'
import {DataTable} from 'primereact/datatable';
import {Column} from 'primereact/column';
import axios from 'axios';
import App from '../App';


export  class AlumnosPorTutor extends Component {
    constructor(){
        super()
        this.state={
            nickUsuario:""
        }

    }
    componentDidMount(){
        //this.getAlumnosPorTutor.then(data => this.setState({alumnos:data}));
        this.setState({nickUsuario:this.state.polla})
    }

   // getAlumnosPorTutor(){
     //  axios.get("localhost:8081/tutor/"+this.props.userName+"/allMyStudents").then(res=>res.data);
    //}

    render(){

        console.log(this.state.nickUsuario);
       return(

        <div></div>
       )
    }




}