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

    async calculateType(event){
        event.preventDefault();
        await this.type.getType(this.state.username, this.state.password).then(data => this.setState({type:data}));
        console.log(this.state.type);
        if(this.state.type==="Username not exist" || this.state.type==="Incorrect password"){
            this.props.history.push("/login");
        }else{
            this.props.history.push("/");
        }
    }

    render() {
        return (
            <div>
                <div className="c">
                    <div className="login">
                        <form method="GET" onSubmit={this.calculateType}>
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
                                    <Button className="p-button-secondary" label="OK" icon="pi pi-fw pi-check"/>
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