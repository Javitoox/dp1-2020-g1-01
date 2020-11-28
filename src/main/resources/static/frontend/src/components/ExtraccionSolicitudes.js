import { Component } from 'react'
import axios from 'axios';

export default class ExtraccionSolicitudes extends Component {
        
    getSolicitudes(urlBase){
        return axios.get(urlBase+"/requests/pending").then(res => res.data);
    }
 
    denyRequest(nickUsuario){
        return axios.get("http://localhost:8081/requests/decline/"+nickUsuario);
    }

    acceptRequest(nickUsuario){
        return axios.get("http://localhost:8081/requests/accept/"+nickUsuario);
    }
}

