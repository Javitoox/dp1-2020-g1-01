import 'primeicons/primeicons.css';
import 'primereact/resources/themes/saga-blue/theme.css';
import 'primereact/resources/primereact.css';
import 'primeflex/primeflex.css';
import '../menu.css';
import { Component } from 'react';
import { Menubar } from 'primereact/menubar';
import { Button } from 'primereact/button';
import { withRouter } from "react-router-dom"
import { storageLogout } from './storage';
import axios from 'axios';

class MenubarResponsive extends Component {

    logout = this.logout.bind(this)
    goToLogin = this.goToLogin.bind(this)

    state = {
        items1: [
            { label: 'Home', icon: 'pi pi-fw pi-home', command: (event) => {
                this.props.history.push("/");
            }},
            { label: 'Enrolment´s requests', icon: 'pi pi-fw pi-file', command: (event) => {
                this.props.history.push("/requests");
            }},
            { label: 'Wall of Fame', icon: 'pi pi-fw pi-star' },
            { label: 'About us', icon: 'pi pi-fw pi-question' }
        ],
        items2: [
            { label: 'Home', icon: 'pi pi-fw pi-home', command: (event) => {
                this.props.history.push("/");
            }},
            { label: 'Enrolment´s requests', icon: 'pi pi-fw pi-file'},
            { label: 'Students', icon: 'pi pi-fw pi-users'},
            { label: 'Payments', icon: 'pi pi-fw pi-dollar', command: (event) => {
                this.props.history.push("/createPayment");
            }},
            { label: 'Material', icon: 'pi pi-fw pi-pencil' },
            { label: 'School calendar', icon: 'pi pi-fw pi-calendar' },
            { label: 'Wall of Fame', icon: 'pi pi-fw pi-star', command: (event) => {
                this.props.history.push("/students/walloffame");
            }},
            { label: 'About us', icon: 'pi pi-fw pi-question' }
        ],
        items3: [
            { label: 'Home', icon: 'pi pi-fw pi-home', command: (event) => {
                this.props.history.push("/");
            }},
            { label: 'Enrolment´s requests', icon: 'pi pi-fw pi-file', command: (event) => {
                this.props.history.push("/pendingRequests");
            }},
            { label: 'Students', icon: 'pi pi-fw pi-users', command:(event)=>{
                this.props.history.push("/allStudents");
            }},
            { label: 'Payments', icon: 'pi pi-fw pi-dollar', command: (event) => {
                this.props.history.push("/payments");
            }},
            { label: 'Material', icon: 'pi pi-fw pi-pencil' },
            { label: 'School calendar', icon: 'pi pi-fw pi-calendar' },
            { label: 'Wall of Fame', icon: 'pi pi-fw pi-star' },
            { label: 'About us', icon: 'pi pi-fw pi-question' }
        ],
        items4: [
            { label: 'Home', icon: 'pi pi-fw pi-home', command: (event) => {
                this.props.history.push("/");
            }},
            { label: 'My students', icon: 'pi pi-fw pi-pencil', command:(event)=>{
                this.props.history.push("/myStudents");
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

    async logout(){
        const result = await axios.delete(this.props.urlBase+"/logout", {withCredentials: true})
        console.log(result.data)
        storageLogout()
        this.props.onChange("usuario")
        this.props.history.push("/")
    }

    goToLogin(){
        this.props.history.push("/login")
    }

    render() {
        const start = <img alt="logo" src="https://static.wixstatic.com/media/8d86d1_1e825f1702a2414eba98748ce4fc56b3~mv2.png/v1/fill/w_121,h_106,al_c,q_85,usm_0.66_1.00_0.01/8d86d1_1e825f1702a2414eba98748ce4fc56b3~mv2.webp" height="40" className="p-mr-2"></img>;
        return (
            <div>
                <div className="card">
                    <Menubar model={this.tipoDeUsuario()} start={start} end={this.props.tipoDeUsuario !== "usuario"? <Button onClick={this.logout} className="button-login 
                    p-button-secondary" label="Logout" icon="pi pi-power-off"/> : <Button onClick={this.goToLogin} className="button-login p-button-secondary" label="Login" 
                    icon="pi pi-fw pi-users" />} />
                </div>
            </div>
        );
    }
}

export default withRouter(MenubarResponsive)