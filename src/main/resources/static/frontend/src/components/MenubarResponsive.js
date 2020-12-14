import 'primeicons/primeicons.css';
import 'primereact/resources/themes/saga-blue/theme.css';
import 'primereact/resources/primereact.css';
import 'primeflex/primeflex.css';
import '../menu.css';
import { Component } from 'react';
import { Menubar } from 'primereact/menubar';
import { Button } from 'primereact/button';
import {storageUserType} from './storage'

export class MenubarResponsive extends Component {

    logout = this.logout.bind(this)

    state = {
        items1: [
            { label: 'Home', icon: 'pi pi-fw pi-home', command: (event) => {
                window.location = "/";
            }},
            { label: 'Enrolment´s requests', icon: 'pi pi-fw pi-file', command: (event) => {
                window.location = "/requests";
            }},
            { label: 'Wall of Fame', icon: 'pi pi-fw pi-star' },
            { label: 'About us', icon: 'pi pi-fw pi-question' }
        ],
        items2: [
            { label: 'Home', icon: 'pi pi-fw pi-home', command: (event) => {
                window.location = "/";
            }},
            { label: 'Enrolment´s requests', icon: 'pi pi-fw pi-file', command: (event) => {
                window.location = "/editStudent";
            }},
            { label: 'Students', icon: 'pi pi-fw pi-users'},
            { label: 'Payments', icon: 'pi pi-fw pi-dollar' },
            { label: 'Material', icon: 'pi pi-fw pi-pencil' },
            { label: 'School calendar', icon: 'pi pi-fw pi-calendar' },
            { label: 'Wall of Fame', icon: 'pi pi-fw pi-star' },
            { label: 'About us', icon: 'pi pi-fw pi-question' }
        ],
        items3: [
            { label: 'Home', icon: 'pi pi-fw pi-home', command: (event) => {
                window.location = "/";
            }},
            { label: 'Enrolment´s requests', icon: 'pi pi-fw pi-file', command: (event) => {
                window.location = "/pendingRequests";
            }},
            { label: 'Students', icon: 'pi pi-fw pi-users', command:(event)=>{
                window.location="/allStudents"
            }},
            { label: 'Payments', icon: 'pi pi-fw pi-dollar' },
            { label: 'Material', icon: 'pi pi-fw pi-pencil' },
            { label: 'School calendar', icon: 'pi pi-fw pi-calendar' },
            { label: 'Wall of Fame', icon: 'pi pi-fw pi-star' },
            { label: 'About us', icon: 'pi pi-fw pi-question' }
        ],
        items4: [
            { label: 'Home', icon: 'pi pi-fw pi-home', command: (event) => {
                window.location = "/";
            }},
            { label: 'My students', icon: 'pi pi-fw pi-pencil', command:(event)=>{
                window.location= "/myStudents"
            }},
            { label: 'Wall of Fame', icon: 'pi pi-fw pi-star' },
            { label: 'About us', icon: 'pi pi-fw pi-question' }
        ]
    };

    tipoDeUsuario(){
        if(this.props.tipoDeUsuario==="usuario"){
            return this.state.items1;
        }else if(this.props.tipoDeUsuario==="alumno"){
            return this.state.items2;
        }else if(this.props.tipoDeUsuario==="profesor"){
            return this.state.items3;
        }else if(this.props.tipoDeUsuario==="tutor"){
            return this.state.items4;
        }
    }

    login(){
        if(this.props.tipoDeUsuario==="usuario"){
            return false;
        }else{
            return true;
        }
    }

    logout(event){
        storageUserType("usuario")
    }

    render() {
        const start = <img alt="logo" src="https://static.wixstatic.com/media/8d86d1_1e825f1702a2414eba98748ce4fc56b3~mv2.png/v1/fill/w_121,h_106,al_c,q_85,usm_0.66_1.00_0.01/8d86d1_1e825f1702a2414eba98748ce4fc56b3~mv2.webp" height="40" className="p-mr-2"></img>;
        return (
            <div>
                <div className="card">
                    <Menubar model={this.tipoDeUsuario()} start={start} end={this.login() ? <a href="/"><Button onClick={this.logout} className="button-login p-button-secondary" 
                    label="Logout" icon="pi pi-power-off"/></a> : <a href="/login"><Button className="button-login p-button-secondary" label="Login" icon="pi pi-fw pi-users" /></a>} />
                </div>
            </div>
        );
    }
}