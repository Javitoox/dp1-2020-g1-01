import { Component } from 'react'
import axios from 'axios';

export default class ExtraccionSolicitudes extends Component {
        
    getSolicitudes(urlBase){
        return axios.get(urlBase+"/requests/pending").then(res => res.data);
    }
 
}

