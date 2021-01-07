import axios from 'axios';
import {Component} from 'react';

export default class PagoComponent extends Component{
    getAllPayments(){
        return axios.get("http://localhost:8081/pagos", {withCredentials: true}).then(res => res.data);
    }
    
    getAllStudentsPaid(concepto){
        return axios.get("http://localhost:8081/pagos/" + concepto, {withCredentials: true}
        ).then(res => res.data);
    }

    getAllStudentsNotPaid(concepto){
        return axios.get("http://localhost:8081/pagos/notPaid/" + concepto, {withCredentials: true}).then(res => res.data);
    }

    getNotPaidByStudent(student){
        return axios.get("http://localhost:8081/pagos/notPaidByStudent/" + student, {withCredentials: true}).then(res => res.data);
    }

    getPaymentsByStudent(student){
        return axios.get("http://localhost:8081/pagos/paidByStudent/" + student, {withCredentials: true}).then(res => res.data);
    }

    getNameStudentByNoPago(){
        return axios.get("http://localhost:8081/pagos/studentsNotPaid", {withCredentials: true}).then(res => res.data);
    }
    
}