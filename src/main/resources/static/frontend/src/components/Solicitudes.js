import { Component } from 'react';
import { AlumnoRequest } from './AlumnoRequest';

export class Solicitudes extends Component {

    render() {
        return <AlumnoRequest urlBase={this.props.urlBase}></AlumnoRequest>;
    }

}