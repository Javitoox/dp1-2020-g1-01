import React, { Component } from 'react'
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import eventBus from "./EventBus";




class AssignGroup extends Component {
    nickUsuario = this.nickUsuario.bind(this);
    constructor() {
        super();
        this.state = {
            nickUsuario: ""
        }
    }
    
   componentDidMount(){
       eventBus.on("guardandoAsignacionAGrupo", (data) =>
       this.setState({ nickUsuario: data.nickUsuario,
      }),

    );
    
    }  
    
    componentWillUnmount() {
        eventBus.remove("guardandoAsignacionAGrupo");
      }
    nickUsuario(event) {
        this.setState({ nickUsuario: event.target.value });
    }

    
    changeButton(event) {
        this.setState({ button: !this.state.button });
    }
    urlBase = "http://localhost:8081/alumnos/assignGroup"
    render() {

        return (
            <div>
                <div className="c">
                    <div className="login request">
                        <form method="GET" action={this.urlBase} >
                            <div className="t"><div><h5>Edit</h5></div></div>
                            <div className="i">
                                <div className="p-inputgroup">
                                    <span className="p-inputgroup-addon">
                                        <i className="pi pi-user"></i>
                                    </span>
                                    <InputText placeholder="Username" name="nickUsuario" type="text" value={this.state.nickUsuario} onChange={this.nickUsuario} />
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
        );
    }
}
export { AssignGroup }; 