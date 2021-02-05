import axios from 'axios';
import {Component} from 'react';
import AuthenticationService from '../service/AuthenticationService';

export default class AlumnoComponent extends Component{
		
	getAllStudents(baseUrl){
		return axios.get(baseUrl + "/alumnos/all", { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } }).then(res => res.data);
	}

	getStudentsByCourse(baseUrl, course){
		return axios.get(baseUrl + "/alumnos/getByCourse/"+course, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } }).then(res => res.data);
	}
        
	getStudentsByNameOfGroup(baseUrl, group){
		return axios.get(baseUrl + "/alumnos/"+group, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } }).then(res => res.data);
	}

	getWallOfFameForStudents(baseUrl, fecha){
		return axios.get(baseUrl+"/premiados/"+fecha, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } }).then(res=>res.data);
	}

	getTheLastWeek(baseUrl){
		return axios.get(baseUrl+"/premiados/ultimaSemana", { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } }).then(res=>res.data);
	}

	assign(persona) {
        return axios.put("http://localhost:8081/alumnos/assignStudent", persona, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } }).then(res => res.data);
    }

	postNewPremiado(baseUrl, fecha ,formData){
        return axios.post(baseUrl+"/premiados/anadirPremiado/"+fecha ,formData, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } });
	}
	
	editPremiado(baseUrl, formData){
		return axios.put(baseUrl+"/premiados/editarPremiado", formData, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } });
	}
	
	deletePremiado(baseUrl, id){
		return axios.delete(baseUrl+"/premiados/borrarPremiado/"+id, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } });
	}

	getAlumnosPorTutor(urlBase, nickUsuario){
        return axios.get(urlBase+"/alumnos/"+nickUsuario+"/allMyStudents", { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } }).then(res=>res.data);
    }
}

