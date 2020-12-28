import { Component } from 'react'
import axios from 'axios';

export default class MetodosAlumno extends Component {

    getUserInfo(urlBase, nickUsuario){
        return axios.get(urlBase+"/alumno/editStudentInfo", {withCredentials: true}, nickUsuario).then(res=>res.data);
    }
}