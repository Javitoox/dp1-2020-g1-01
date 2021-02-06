import { Button } from 'primereact/button'
import { InputText } from 'primereact/inputtext'
import { withRouter } from "react-router-dom"
import '../login.css'
import { ExtraccionUsuarios } from './ExtraccionUsuarios'
import { Component } from 'react'
import { bindActionCreators } from 'redux'
import { connect } from 'react-redux'
import { selectUserLogin } from '../actions/index'
import AuthenticationService from '../service/AuthenticationService'

class Login extends Component {

    username = this.username.bind(this)
    password = this.password.bind(this)
    type = new ExtraccionUsuarios(this.props.urlBase)
    calculateType = this.calculateType.bind(this)

    state = {
        username: "",
        password: "",
        type: "",
        error: null
    }

    username(event) {
        this.setState({ username: event.target.value })
    }

    password(event) {
        this.setState({ password: event.target.value })
    }

    async calculateType(event) {
        event.preventDefault()
        await AuthenticationService.executeBasicAuthenticationService(this.state.username, this.state.password).then(res => {
            AuthenticationService.registerSuccessfulLogin(this.state.username, this.state.password, res.data)
            this.props.onChange(AuthenticationService.getAuth())
            if(sessionStorage.getItem("auth")=== "profesor"){
            this.props.history.push("/homeTeacher")
            }else{
            this.props.history.push("/")
            }
        }).catch((error) => {
            this.setState({ error: <div className="alert alert-danger" role="alert">Username or password does not exist</div> })
            this.props.history.push("/login")
        })
    }

    render() {
        return (
            <div>
                <div className="c">
                    <div className="login">
                        <form method="GET" onSubmit={this.calculateType}>
                            <div className="t"><div><h5>Login</h5></div></div>
                            {this.state.error}
                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-user"></i>
                                    </span>
                                    <InputText placeholder="Username" name="username" type="text" value={this.state.username} onChange={this.username} />
                                </div>
                            </div>

                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-lock"></i>
                                    </span>
                                    <InputText placeholder="Password" name="password" type="password" value={this.state.password} onChange={this.password} />
                                </div>
                            </div>
                            <div className="b">
                                <div className="i">
                                    <Button className="p-button-secondary" label="OK" icon="pi pi-fw pi-check" />
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        )
    }
}
function matchDispatchToProps(dispatch) {
    return bindActionCreators({
        selectUserLogin: selectUserLogin
    }, dispatch) //se mapea el action llamado selectStudent y se transforma en funcion con este metodo, sirve para pasarle la info que queramos al action, este se la pasa al reducer y de alli al store 
}

export default connect(null, matchDispatchToProps)(withRouter(Login))