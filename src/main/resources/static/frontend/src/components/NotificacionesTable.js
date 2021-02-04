import React, { Component } from 'react';
import 'react-notifications-component/dist/theme.css';
import { store } from 'react-notifications-component';
import ReactNotification from 'react-notifications-component';
class NotificacionesTable extends Component {
    sleep(ms) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }
  async componentDidMount(){
     await this.sleep(500)
        if(this.props.solicitudes!==0){
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
            store.addNotification({
                title: "COVID 19 INFORMATION",
                message: "Due to the Covid-19 situation, you have the option to take online classes or to book custom presential tutorships. Get more info at https://www.mscbs.gob.es/",
                type: "danger",
                insert: "top",
                container: "top-right",
                animationIn: ["animate__animated", "animate__fadeIn"],
                animationOut: ["animate__animated", "animate__fadeOut"],
                dismiss: {
                    duration: 50000,
                    onScreen: true
            }}
            )}
            else{
                store.addNotification({
                    title: "COVID 19 INFORMATION",
                    message: "Due to the Covid-19 situation, you have the option to take online classes or to book custom presential tutorships. Get more info at https://www.mscbs.gob.es/",
                    type: "danger",
                    insert: "top",
                    container: "top-right",
                    animationIn: ["animate__animated", "animate__fadeIn"],
                    animationOut: ["animate__animated", "animate__fadeOut"],
                    dismiss: {
                        duration: 50000,
                        onScreen: true
                }}
                ) 
                store.addNotification({
                    title: "NOTIFICATIONS",
                    message: "Your notifications list is empty!",
                    type: "info",
                    insert: "top",
                    container: "top-right",
                    animationIn: ["animate__animated", "animate__fadeIn"],
                    animationOut: ["animate__animated", "animate__fadeOut"],
                    dismiss: {
                        duration: 50000,
                        onScreen: true
                }}
                )
            }
        }

    render() {
        return (
            <div>
                <div>
                <ReactNotification />
                </div>
            </div>
        );
    }
}


export default NotificacionesTable;