import React from 'react';
import { EditarFormulario } from './EditarFormulario';
import { FormularioSolicitud } from './FormularioSolicitud';
import { SolicitudesProfesor } from './SolicitudesProfesor';

export class Solicitudes extends React.Component{

    tipoDeUsuario(){
        if(this.props.tipoDeUsuario==="usuario"){
            return <FormularioSolicitud></FormularioSolicitud>;
        }else if(this.props.tipoDeUsuario==="alumno"){
            return <EditarFormulario></EditarFormulario>;
        }else if(this.props.tipoDeUsuario==="profesor"){
            return <SolicitudesProfesor></SolicitudesProfesor>;
        }
    }

    render(){
        return this.tipoDeUsuario();
    }
}