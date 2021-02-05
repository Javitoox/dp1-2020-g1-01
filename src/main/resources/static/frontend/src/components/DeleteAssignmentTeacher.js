import React, { Component } from 'react'
import axios from 'axios';
import { Button } from 'primereact/button';
import AssignmentComponent from './AssignmentComponent';


export class DeleteAssignmentTeacher extends Component  {
       
    constructor(props) {
        super(props);
        this.state = {
           
            redirect: false,
            comprobation: false,
        }
        this.asig= new AssignmentComponent();
        this.form= this.form.bind(this);
        this.delete = this.delete.bind(this);
        this.mostrarTabla = this.mostrarTabla.bind(this);

    }
    componentDidMount() {
        axios.get("http://localhost:8081/auth", {withCredentials: true}).then(res => {
            if(res.data==="profesor"){
                this.setState({comprobation: true})
            }
            })  
            this.mostrarTabla()

    }
    form(){
        return <React.Fragment>
                    <div className="b">
                    <div className="i">
                        <Button className="p-button-secondary" label="OK" icon="pi pi-fw pi-check"/>
                    </div>
                    </div>
                </React.Fragment>
    }
    mostrarTabla(){
        this.asig.getListOfAssignment(this.props.urlBase, this.props.nickUser).then(data => this.setState({ alumnos: data }));
    }

    delete(){
         this.asig.deleteAsignacion(this.props.urlBase,this.props.nickUser,this.props.data.grupo.nombreGrupo)
      }
    render(){
        return(
            <React.Fragment>
            <div className="t"><div><h5>Do you want to stop being the teacher of "{this.props.data.grupo.nombreGrupo}"?</h5></div></div>
            <form onSubmit={this.delete}>
            {this.form()}
            </form>         
            </React.Fragment>

        );
    }
}

      
    
    

