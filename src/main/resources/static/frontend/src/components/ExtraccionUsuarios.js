import { Component } from 'react';
import axios from 'axios';
import AuthenticationService from '../service/AuthenticationService';

export class ExtraccionUsuarios extends Component {

    constructor(urlBase){
        super();
        this.urlBase = urlBase;
    }

    getType(username, password) {
        return axios.get(this.urlBase+"/login?username=" + username + "&password=" + password, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } }).then(res => res.data);
    }

}