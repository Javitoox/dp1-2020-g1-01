import React, { Component } from 'react';
import ReactNotification from 'react-notifications-component';
import 'react-notifications-component/dist/theme.css';
import "../css/home.css";
import { store } from 'react-notifications-component';

class HomeUsuario extends Component {
    constructor() {
        super();
        this.state = {
            events: 0,
            payment: 0
        }

    }
    componentDidMount(){
        store.addNotification({
            title: "COVID 19 INFORMATION",
            message: "Due to the Covid-19 situation, you have the option to take online classes or to book custom presential tutorships.",
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

    render() {
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

export default HomeUsuario;
