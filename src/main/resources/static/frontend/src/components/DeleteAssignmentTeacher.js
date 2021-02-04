import React, { Component } from 'react'
import axios from 'axios';
import { Button } from 'primereact/button';


export class DeleteAssignmentTeacher extends Component  {
       
    constructor(props) {
        super(props);
        this.state = {
           
            redirect: false,
            comprobation: false,
        }
        this.form= this.form.bind(this);
        this.delete = this.delete.bind(this);
    }
    componentDidMount() {
        axios.get("http://localhost:8081/auth", {withCredentials: true}).then(res => {
            if(res.data==="profesor"){
                this.setState({comprobation: true})
            }
            })  
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

    delete  =  event => {
        event.preventDefault();
        axios.delete(this.props.urlBase + "/asignaciones/delete/"+this.props.nickUser+"/"+this.props.data.grupo.nombreGrupo, {withCredentials: true}).then(res => { console.log(res.status)
            })
        window.location.assign("/teacherGroups")
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

      
    
    

