import { Component } from 'react'
import axios from 'axios';
import AuthenticationService from '../service/AuthenticationService';

export default class ExtraccionSolicitudes extends Component {
        
    getSolicitudes(urlBase){
        return axios.get(urlBase+"/requests/pending", { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } }).then(res => res.data);
    }
    
    denyRequest(urlBase, nickUsuario){
        return axios.put(urlBase+"/requests/decline/"+nickUsuario, {}, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } });
    }

    acceptRequest(urlBase, nickUsuario){
        return axios.put(urlBase +"/requests/accept/"+nickUsuario, {}, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } });
    }
}

