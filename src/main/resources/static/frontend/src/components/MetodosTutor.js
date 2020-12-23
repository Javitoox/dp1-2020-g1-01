import { Component } from 'react'
import axios from 'axios';

export default class MetodosTutor extends Component {

    getAlumnosPorTutor(urlBase, nickUsuario){
        return axios.get(urlBase+"/tutor/"+nickUsuario+"/allMyStudents", {withCredentials: true}).then(res=>res.data);
    }
}