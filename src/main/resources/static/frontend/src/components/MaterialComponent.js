import axios from 'axios';
import {Component} from 'react';

export default class MaterialComponent extends Component{


    crearMaterial(urlBase, nickUsuario,formData ){
        return axios.post(urlBase+"/materiales/añadirMaterial/"+nickUsuario, formData);
    }

    asignarAlumnoMaterial(urlBase,id,alumno){
        return axios.put(urlBase+"/feedback/"+id+"/añadirAlumno/",alumno);
    }

    obtenerMaterialTeacher(urlBase,nickUsuario){
        return axios.get(urlBase+"/materiales/getMaterialByProfesor/"+nickUsuario);
    }

    deleteMaterial(urlBase, id){
        return axios.delete(urlBase+"/feedback/deleteMaterial/"+id);
    }

    obtenerMaterialStudent(urlBase, nickUsuario){
        return axios.get(urlBase+"/materiales/getMaterialByAlumno/"+nickUsuario);
    }

    obtenerFeedbackMaterial(urlBase, idMaterial){
        return axios.get(urlBase+"/feedback/obtenerFeedback/"+idMaterial);
    }

    modificarDone(urlBase, id){
        return axios.put(urlBase+"/feedback/cambiarDone/"+id);

    }




}