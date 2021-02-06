import axios from 'axios';
import {Component} from 'react';
import AuthenticationService from '../service/AuthenticationService';

export default class MaterialComponent extends Component{


    crearMaterial(urlBase, nickUsuario,formData ){
        return axios.post(urlBase+"/materiales/añadirMaterial/"+nickUsuario, formData, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } });
    }

    asignarAlumnoMaterial(urlBase,id,alumno){
        return axios.put(urlBase+"/feedback/"+id+"/añadirAlumno/",alumno, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } });
    }

    obtenerMaterialTeacher(urlBase,nickUsuario){
        return axios.get(urlBase+"/materiales/getMaterialByProfesor/"+nickUsuario, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } });
    }

    deleteMaterial(urlBase, id){
        return axios.delete(urlBase+"/feedback/deleteMaterial/"+id, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } });
    }

    obtenerMaterialStudent(urlBase, nickUsuario){
        return axios.get(urlBase+"/materiales/getMaterialByAlumno/"+nickUsuario, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } });
    }

    obtenerFeedbackMaterial(urlBase, idMaterial){
        return axios.get(urlBase+"/feedback/obtenerFeedback/"+idMaterial, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } });
    }

    modificarDone(urlBase, id){
        return axios.put(urlBase+"/feedback/cambiarDone/"+id, {}, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } });
    }

    getFeedback(urlBase,nickUser,id){
        return axios.get(urlBase+"/feedback/"+nickUser+"/"+id, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } });
    }

    updateFeedback(urlBase,formData){
        return axios.put(urlBase+"/feedback/update", formData, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } });
    }

    obtenerMediaRate(urlBase, id){
        return axios.get(urlBase+"/feedback/getRateAverage/"+id);
    }
}