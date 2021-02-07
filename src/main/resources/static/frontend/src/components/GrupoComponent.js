import axios from 'axios';
import {Component} from 'react';

export default class GrupoComponent extends Component{
    getAllGroups(){
        return axios.get("http://localhost:8081/grupos/all").then(res => res.data);
    }
    getAllGroupNames(){
        return axios.get("http://localhost:8081/grupos/allGroupNames").then(res => res.data);
    }

    getGroupNamesByCourse(course){
        return axios.get("http://localhost:8081/grupos/nombresGrupo/" + course).then(res => res.data);
    }

    getCourseNamesByGroup(group){
        return axios.get("http://localhost:8081/grupos/nombreCurso/" + group).then(res => res.data);
    }

    getEmptyGroupNames(){
        return axios.get("http://localhost:8081/grupos/allEmptyGroups").then(res => res.data);
    }

    getAssignmentGroupsByStudent(nick){
        return axios.get("http://localhost:8081/grupos/allAsignableGroups/"+nick).then(res => res.data);
    }
}