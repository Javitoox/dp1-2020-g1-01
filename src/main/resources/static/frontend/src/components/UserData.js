import { Component } from 'react';
import axios from 'axios';
import AuthenticationService from '../service/AuthenticationService';

export default class UserData extends Component{

  getAlumnoInfo(urlBase, nickUsuario) {
    return axios.get(urlBase + "/alumnos/getStudentInfo/" + nickUsuario, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } }).then(res => res.data);
}

}