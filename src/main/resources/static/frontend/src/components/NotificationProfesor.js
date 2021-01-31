import React, { Component } from 'react'

import ExtraccionSolicitudes from './ExtraccionSolicitudes';
import Notificaciones from './Notificaciones';
import axios from 'axios';
import Auth from './Auth';
export default class NotificationProfesor extends Component {
  constructor() {
    super();
    this.state = {
      numeroSolicitudes: 0,
      numeroEventos: 0,
      comprobation: false
    }
    this.solicitudesComponent = new ExtraccionSolicitudes();
  }

  componentDidMount() {
    axios.get(this.props.urlBase + "/auth", {withCredentials: true}).then(res => {
      if(res.data==="profesor"){
          this.setState({comprobation: true})
      }
      })
     this.solicitudesComponent.getSolicitudes(this.props.urlBase).then(data => this.setState({ numeroSolicitudes: data.length }));

  }




  render() {  if (!this.state.comprobation) {
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