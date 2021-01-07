import { Component } from 'react';
import axios from 'axios';
export default class UserData extends Component{

  getAlumnoInfo(urlBase, nickUsuario) {
    return axios.get(urlBase + "/alumnos/getStudentInfo/" + nickUsuario, {withCredentials: true}).then(res => res.data);
}

}