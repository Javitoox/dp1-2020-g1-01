import React, { Component } from 'react'
import axios from 'axios';

export default class ExtraccionSolicitudes extends Component {
    
    
    
    baseUrl= "http://localhost:8081/solicitudes/all"



    getSolicitudes(){
        return axios.get(this.baseUrl).then(res => res.data);
    }
 
    }

