import React, { Component } from 'react';
import ReactNotification from 'react-notifications-component';
import 'react-notifications-component/dist/theme.css';
import { store } from 'react-notifications-component';
class Notificaciones extends Component {
    constructor() {
        super();
        this.state = {
            events: 0
        }

    }
    sleep(ms) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }
    async componentDidMount() {
        await this.sleep(500)
        if (this.state.events === 0 && this.props.solicitudes !== 0) {
            store.addNotification({
                title: "REQUESTS",
                message: "There are " + this.props.solicitudes + " new pending requests",
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
        } else if (this.state.events !== 0 && this.props.solicitudes !== 0) {
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
                message: "There are " + this.props.solicitudes + " new pending requests",
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
        } else if (this.state.events !== 0 && this.props.solicitudes === 0) {
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

        }
         else if (this.state.events === 0 && this.props.solicitudes !== 0) {
            store.addNotification({
                title: "REQUESTS",
                message: "There are " + this.props.solicitudes + " new pending requests",
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
        

            else if (this.state.events !== 0 && this.props.solicitudes === 0) {
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
        } 
        else if (this.state.events !== 0 && this.props.solicitudes !== 0) {
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
                message: "There are " + this.props.solicitudes + " new pending requests",
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
        
        else {
            store.addNotification({
                title: "NO NOTIFICATIONS",
                message: "You have no notifications",
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
    }
    render() {
        console.log(this.props.solicitudes)
        return (

            <div className="app-container">
                <ReactNotification />
            </div>

        );
    }
}

export default Notificaciones;