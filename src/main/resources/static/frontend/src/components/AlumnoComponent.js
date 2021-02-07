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
		return axios.get(baseUrl + "/alumnos/"+group).then(res => res.data);
	}

	getWallOfFameForStudents(baseUrl, fecha){
		return axios.get(baseUrl+"/premiados/"+fecha).then(res=>res.data);
	}

	getTheLastWeek(baseUrl){
		return axios.get(baseUrl+"/premiados/ultimaSemana").then(res=>res.data);
	}

	assign(persona) {
        return axios.put("http://localhost:8081/alumnos/assignStudent", persona).then(res => res.data);
    }

	postNewPremiado(baseUrl, fecha ,formData){
        return axios.post(baseUrl+"/premiados/anadirPremiado/"+fecha ,formData);
	}
	
	editPremiado(baseUrl, formData){
		return axios.put(baseUrl+"/premiados/editarPremiado", formData);
	}
	
	deletePremiado(baseUrl, id){
		return axios.delete(baseUrl+"/premiados/borrarPremiado/"+id);
	}

	getAlumnosPorTutor(urlBase, nickUsuario){
        return axios.get(urlBase+"/alumnos/"+nickUsuario+"/allMyStudents").then(res=>res.data);
    }

	getAlumnosEliminiables(urlBase){
        return axios.get(urlBase+"/alumnos/ableToDelete").then(res=>res.data);
	}

	getAlumnosSinGrupo(urlBase){
        return axios.get(urlBase+"/alumnos/studentsWithNoGroups").then(res=>res.data);
	}

	getAlumnosSinTutores(urlBase){
        return axios.get(urlBase+"/alumnos/studentsWithNoTutors").then(res=>res.data);
	}
	getAlumnoInfo(urlBase, nick){
        return axios.get(urlBase+"/alumnos/getStudentInfo/"+nick, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } }).then(res=>res.data);
	}
	
	deleteAlumno(urlBase, nickUsuario){
		return axios.delete(urlBase+"/alumnos/delete/"+nickUsuario)
	}

	getTeacherByGroup(urlBase, nombreGrupo){
		return axios.get(urlBase+"/tutores/teacherByGroup/"+nombreGrupo);
	}
}

