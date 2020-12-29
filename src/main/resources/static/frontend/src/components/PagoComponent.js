import axios from 'axios';
import {Component} from 'react';

export default class PagoComponent extends Component{
    getAllPayments(){
        return axios.get("http://localhost:8081/pagos").then(res => res.data);
    }
    save(pago) {
        
    }

    getAllStudentsPaid(concepto){
        return axios.get("http://localhost:8081/pagos/"+concepto).then(res => res.data);
    }

    getAllStudentsNotPaid(concepto){
        return axios.get("http://localhost:8081/pagos/notPaid"+concepto).then(res => res.data);
    }
}