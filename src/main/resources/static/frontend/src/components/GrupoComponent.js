import axios from 'axios';
import {Component} from 'react';

export default class GrupoComponent extends Component{
    getAllGroups(){
        return axios.get("http://localhost:8081/grupos/all").then(res => res.data);
    }
    save(grupo) {
        
    }

    getAllGroupNames(){
        return axios.get("http://localhost:8081/grupos/allGroupNames").then(res => res.data);
    }

    getEmptyGroupNames(){
        return axios.get("http://localhost:8081/grupos/allEmptyGroups").then(res => res.data);
    }
    
    delete(grupo) {
        
    }
}