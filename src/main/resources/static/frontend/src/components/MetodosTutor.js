import { Component } from 'react'
import axios from 'axios';

export default class MetodosTutor extends Component {

    getAlumnosPorTutor(urlBase){
        return axios.get(urlBase+"/tutor/marrambla2/allMyStudents").then(res=>res.data);
    }
 
}