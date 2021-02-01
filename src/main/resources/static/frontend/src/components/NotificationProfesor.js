import React, { Component } from 'react'
import ReactNotification from 'react-notifications-component';
import 'react-notifications-component/dist/theme.css';
import { store } from 'react-notifications-component';
import ExtraccionSolicitudes from './ExtraccionSolicitudes';

export default class NotificationProfesor extends Component {

  constructor() {
    super();
    this.state = {
      requests:  5,
      events: 3
    }
    this.solicitudesComponent = new ExtraccionSolicitudes();
  }
  async componentDidMount() {
    this.notificaciones();
  }
notificaciones(){
  store.addNotification({
    title: "EVENTS",
    message: "There are " + this.state.events + " new inscriptions to events that you posted",
    type: "info",
    insert: "top",
    container: "top-right",
    animationIn: ["animate__animated", "animate__fadeIn"],
    animationOut: ["animate__animated", "animate__fadeOut"],
    dismiss: {
      duration: 50000,
      onScreen: true
    }
  })
  store.addNotification({
    title: "REQUESTS",
    message: "There are "+  this.state.requests +  " new pending requests",
    type: "info",
    insert: "top",
    container: "top-right",
    animationIn: ["animate__animated", "animate__fadeIn"],
    animationOut: ["animate__animated", "animate__fadeOut"],
    dismiss: {
      duration: 50000,
      onScreen: true
    }
  })

}
  render() {
    return (
      
      <div className="app-container">
        <ReactNotification />
      </div>
    )
  }
}