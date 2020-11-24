import { Component } from 'react';
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import { withRouter } from "react-router-dom";
import '../login.css';
import { ExtraccionUsuarios } from './ExtraccionUsuarios';

 class Login extends Component {

    username = this.username.bind(this);
    password = this.password.bind(this);   
    type = new ExtraccionUsuarios(this.props.urlBase); 
    calculateRedirect = this.calculateRedirect.bind(this);
    calculateType = this.calculateType.bind(this);

    state = {
        username: "",
        password: "",
        type:""
    }

    username(event) {
        this.setState({ username: event.target.value });
    }

    password(event) {
        this.setState({ password: event.target.value });
    }

    calculateRedirect(event){
        if(this.state.type!==""){
            this.props.history.push("/");
        }
    }

    calculateType(event){
        console.log("Entrada");
        this.type.getType(this.state.username, this.state.password).then(data => this.setState({type:data}));
    }

    render() {
        return (
            <div>
                <div className="c">
                    <div className="login">
                        <form method="GET">
                            <div className="t"><div><h5>Login</h5></div></div>
                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-user"></i>
                                    </span>
                                    <InputText placeholder="Username" name="username" type="text" value={this.state.username} onChange={this.username}/>
                                </div>
                            </div>

                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-lock"></i>
                                    </span>
                                    <InputText placeholder="Password" name="password" type="text" value={this.state.password} onChange={this.password}/>
                                </div>
                            </div>
                            <div className="b">
                                <div className="i">
                                    <Button className="p-button-secondary" label="OK" icon="pi pi-fw pi-check" onClick={this.calculateRedirect} onMouseOver={this.calculateType}/>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        );
    }
}

export default withRouter(Login);