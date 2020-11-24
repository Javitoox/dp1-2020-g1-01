import { Component } from 'react'
import axios from 'axios';

export default class ExtraccionAlumnosByTutor extends Component {

    getAlumnosPorTutor(){
        return axios.get("http://localhost:8081/tutor/marrambla2/allMyStudents").then(res=>res.data);
    }
 
}