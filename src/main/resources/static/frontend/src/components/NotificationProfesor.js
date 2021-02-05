import React, { Component } from 'react'

import ExtraccionSolicitudes from './ExtraccionSolicitudes';
import Notificaciones from './Notificaciones';
import Auth from './Auth';
export default class NotificationProfesor extends Component {
  constructor() {
    super();
    this.state = {
      numeroSolicitudes: 0,
      numeroEventos: 0,
      comprobation: true
    }
    this.solicitudesComponent = new ExtraccionSolicitudes();
  }

   componentDidMount() {
    this.solicitudesComponent.getSolicitudes(this.props.urlBase).then(data => this.setState({numeroSolicitudes: data.length}))
    .catch(error => this.setState({comprobation: false}));
  }

  render() {  
    if (!this.state.comprobation) {
    return <Auth authority="teacher"></Auth>
  } else {

    console.log("hay en total : " + this.state.numeroSolicitudes)
    return (
      <div>
        <Notificaciones solicitudes={this.state.numeroSolicitudes}></Notificaciones>
      </div>

    )
  }
}
}