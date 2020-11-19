import { Component } from 'react';
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import axios from 'axios';
import '../login.css';

export class Login extends Component {

    state = {
        redirect :"/"
    }

    resultForm(){
       var type = axios.get(this.props.urlBase + "/login").then(res => res.data);
       if(type==="Username not exist" || type==="Incorrect password") this.setState({redirect:"/login"});
    }

    render() {
        return (
            <div>
                <div className="c">
                    <div className="login">
                        <form action={this.state.redirect} method="GET">
                            <div className="t"><div><h5>Login</h5></div></div>
                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-user"></i>
                                    </span>
                                    <InputText placeholder="Username" name="username" type="text"/>
                                </div>
                            </div>

                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-lock"></i>
                                    </span>
                                    <InputText placeholder="Password" name="password" type="text"/>
                                </div>
                            </div>
                            <div className="b">
                                <div className="i">
                                    <Button className="p-button-secondary" label="OK" icon="pi pi-fw pi-check" onClick={this.resultForm()}/>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        );
    }
}