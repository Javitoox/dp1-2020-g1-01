import React from 'react';
import axios from 'axios';


export  class SolicitudesProfesor extends React.Component {
    baseUrl = "http://localhost:8081/solicitudes";
	
	getAllSolicitudes(){
		return axios.get(this.baseUrl + "/all").then(res => res.data);
	}
}

