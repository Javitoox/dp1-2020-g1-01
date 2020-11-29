import { Component } from 'react';
import ExtraccionMensajes from './ExtraccionMensajes';
import { Redirect } from "react-router-dom";

export class Home extends Component {

    extraccion = new ExtraccionMensajes();

    state = {
        type: "usuario"
    }

    componentDidMount() {
        var m = this.extraccion.getParameterByName("type");
        if (m !== "") {
            this.setState({ type: m });
        }
    }

    render() {
        return <Redirect to="/"/>
    }
}