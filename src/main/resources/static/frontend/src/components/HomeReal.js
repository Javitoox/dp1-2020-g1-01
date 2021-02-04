import React, { Component } from 'react';
import ReactNotification from 'react-notifications-component';
import 'react-notifications-component/dist/theme.css';
import { store } from 'react-notifications-component';
import "../css/home.css";

class HomeReal extends Component {
    constructor() {
        super();
        this.state = {
            events: 0,
            payment: 0
        }

    }


    sleep(ms) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }
    async componentDidMount() {
        await this.sleep(500)
        if(this.props.comprobation===true){
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
            }
        }
    }

    render() {
        
        console.log(this.props.solicitudes)
        return (

            <div className="background"> 
                <ReactNotification />
                <div>
                    <h1 className="w3-black">
                       Welcome to TEA, The English Academy  
                    </h1>
                </div>
             </div>


        );
    }
}

export default HomeReal;
