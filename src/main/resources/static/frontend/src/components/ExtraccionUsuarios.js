import { Component } from 'react';
import axios from 'axios';

export class ExtraccionUsuarios extends Component {

    constructor(urlBase){
        super();
        this.urlBase = urlBase;
    }

    getType(username, password) {
        return axios.get(this.urlBase+"/login?username=" + username + "&password=" + password, {withCredentials: true}).then(res => res.data);
    }

}