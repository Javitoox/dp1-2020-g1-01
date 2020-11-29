import axios from 'axios';
import {Component} from 'react';

export default class AlumnoComponent extends Component{
		
	getAllStudents(baseUrl){
		return axios.get(baseUrl + "/alumnos/all").then(res => res.data);
	}

	getStudentsByCourse(baseUrl, course){
		return axios.get(baseUrl + "/alumnos/getByCourse/"+course).then(res => res.data);
	}
        
	getStudentsByNameOfGroup(baseUrl, group){
	return axios.get(baseUrl + "/alumnos/getByNameOfGroup/"+group).then(res => res.data);
	}
}

