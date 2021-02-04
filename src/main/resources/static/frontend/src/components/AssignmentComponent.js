import axios from 'axios';
import {Component} from 'react';

export default class AssignmentComponent extends Component{
		
	
	getListOfAssignment(baseUrl, user){
		return axios.get(baseUrl + "/asignaciones/" + user).then(res => res.data);
    }
    
    getListOfEmptyAssignmentGroup(baseUrl){
        return axios.get(baseUrl + "/asignaciones/freeAssignments").then(res => res.data);
    }

}
