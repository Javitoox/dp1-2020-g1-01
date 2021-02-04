import React, { Component } from 'react';
import ReactNotification from 'react-notifications-component';
import 'react-notifications-component/dist/theme.css';
import { store } from 'react-notifications-component';
class NotificacioneStudents extends Component {
    constructor() {
        super();
        this.state = {
        }

    }
    sleep(ms) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }
    async componentDidMount() {
        await this.sleep(500)
        if (this.props.payment !== 0) {
            store.addNotification({
                title: "PAYMENTS",
                message: "There are " + this.props.payment + " unpaid months, please check the payments view to see more info",
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
           
        }else {
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
        console.log(this.props.payment)
        return (

            <div className="app-container">
                <ReactNotification />
            </div>

        );
    }
}

export default NotificacioneStudents;