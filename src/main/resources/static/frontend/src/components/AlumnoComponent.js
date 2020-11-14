import axios from 'axios';
import {Component} from 'react';

export class AlumnoComponent extends Component{
	baseUrl = "http://localhost:8081/alumnos";
	
	getAllAlumnos(){
		return axios.get(this.baseUrl + "/all").then(res => res.data);
	}
}

