import axios from 'axios';
import {Component} from 'react';
import AuthenticationService from '../service/AuthenticationService';

export default class PagoComponent extends Component{
    getAllPayments(){
        return axios.get("http://localhost:8081/pagos", { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } }).then(res => res.data);
    }
    
    getAllStudentsPaid(concepto){
        return axios.get("http://localhost:8081/pagos/" + concepto, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } }).then(res => res.data);
    }

    getAllStudentsNotPaid(concepto){
        return axios.get("http://localhost:8081/pagos/notPaid/" + concepto, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } }).then(res => res.data);
    }

    getNotPaidByStudent(student){
        return axios.get("http://localhost:8081/pagos/notPaidByStudent/" + student, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } }).then(res => res.data);
    }

    getPaymentsByStudent(student){
        return axios.get("http://localhost:8081/pagos/paidByStudent/" + student, { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } }).then(res => res.data);
    }

    getNameStudentByNoPago(){
        return axios.get("http://localhost:8081/pagos/studentsNotPaid", { headers: { authorization: AuthenticationService.createBasicAuthToken(sessionStorage.getItem("authenticatedUser"), 
		sessionStorage.getItem("password")) } }).then(res => res.data);
    }
    
}