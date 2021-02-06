import axios from 'axios';
import {Component} from 'react';
import AuthenticationService from '../service/AuthenticationService';

export default class GrupoComponent extends Component{
    getAllGroups(){
        return axios.get("http://localhost:8081/grupos/all", { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } }).then(res => res.data);
    }
    getAllGroupNames(){
        return axios.get("http://localhost:8081/grupos/allGroupNames", { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } }).then(res => res.data);
    }

    getGroupNamesByCourse(course){
        return axios.get("http://localhost:8081/grupos/nombresGrupo/" + course, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } }).then(res => res.data);
    }

    getCourseNamesByGroup(group){
        return axios.get("http://localhost:8081/grupos/nombreCurso/" + group, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } }).then(res => res.data);
    }

    getEmptyGroupNames(){
        return axios.get("http://localhost:8081/grupos/allEmptyGroups", { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } }).then(res => res.data);
    }

    getAssignmentGroupsByStudent(nick){
        return axios.get("http://localhost:8081/grupos/allAsignableGroups/"+nick, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } }).then(res => res.data);
    }
}