import axios from 'axios';
import {Component} from 'react';

export default class AlumnoComponent extends Component{
		
	getAllStudents(baseUrl){
		return axios.get(baseUrl + "/alumnos/all", {withCredentials: true}).then(res => res.data);
	}

	getStudentsByCourse(baseUrl, course){
		return axios.get(baseUrl + "/alumnos/getByCourse/"+course,{withCredentials: true}).then(res => res.data);
	}
        
	getStudentsByNameOfGroup(baseUrl, group){
		return axios.get(baseUrl + "/alumnos/"+group, {withCredentials: true}).then(res => res.data);
	}

	getWallOfFameForStudents(baseUrl, fecha){
		return axios.get(baseUrl+"/premiados/"+fecha, {withCredentials: true}).then(res=>res.data);
	}

	getTheLastWeek(baseUrl){
		return axios.get(baseUrl+"/premiados/ultimaSemana", {withCredentials: true}).then(res=>res.data);
	}

	assign(persona) {
        return axios.put("http://localhost:8081/alumnos/assignStudent", persona, {withCredentials: true}).then(res => res.data);
    }

	postNewPremiado(baseUrl, fecha ,formData){
        return axios.post(baseUrl+"/premiados/anadirPremiado/"+fecha ,formData,{withCredentials: true});
	}
	
	editPremiado(baseUrl, formData){
		return axios.put(baseUrl+"/premiados/editarPremiado", formData,{withCredentials: true});
	}
	
	deletePremiado(baseUrl, id){
		return axios.delete(baseUrl+"/premiados/borrarPremiado/"+id,{withCredentials: true});
	}

	getAlumnosPorTutor(urlBase, nickUsuario){
        return axios.get(urlBase+"/alumnos/"+nickUsuario+"/allMyStudents", {withCredentials: true}).then(res=>res.data);
	}
	getAlumnosEliminiables(urlBase){
        return axios.get(urlBase+"/alumnos/ableToDelete", {withCredentials: true}).then(res=>res.data);
    }
}

