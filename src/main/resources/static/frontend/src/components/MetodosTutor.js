import { Component } from 'react'
import axios from 'axios';

export default class MetodosTutor extends Component {

    getAlumnosPorTutor(urlBase, nickUsuario){
        return axios.get(urlBase+"/tutor/"+nickUsuario+"/allMyStudents").then(res=>res.data);
    }
}