import axios from 'axios';
import {Component} from 'react';

export default class AssignmentComponent extends Component{
		
	
	getListOfAssignment(baseUrl, user){
		return axios.get(baseUrl + "/asignaciones/get/" + user, {withCredentials: true}).then(res => res.data);
    }
    
    getListOfEmptyAssignmentGroup(baseUrl){
        return axios.get(baseUrl + "/asignaciones/freeAssignments", {withCredentials: true}).then(res => res.data);
    }

    deleteAsignacion(baseUrl, nickUser,nombreGrupo){
        return axios.delete(baseUrl + "/asignaciones/delete/"+nickUser+"/"+nombreGrupo, {withCredentials: true}).then(res => res.data);
    }

}
