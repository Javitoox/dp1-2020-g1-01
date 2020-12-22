import React, { Component } from 'react'
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import AlumnoComponent from './AlumnoComponent';


export class FormWallofFame extends Component{
    
    constructor(){
        super();
        this.nickusuario = this.nickusuario.bind(this);
        this.photo = this.photo.bind(this);
        
        this.state = {
            nickusuario : "",
            photo: null
        }

        this.premiados = new AlumnoComponent()
    }
    
    nickusuario(event){
        this.setState({nickusuario : event})
    }

    photo(event){
        this.setState({photo : event})
    }

    handleSubmit(){
        this.premiados.postNewPremiado(this.props.urlBase, this.state.nickusuario, this.state.photo)
    }
    
    render(){

        return(
            <form onSubmit={this.handleSubmit}>
                <div className="i">
                    <div className="p-inputgroup">
                    <span className="p-inputgroup-addon">
                        <i className="pi pi-user"></i>
                    </span>
                        <InputText type= "text" placeholder="Username" name="nickusuario" onChange={this.nickusuario} />
                    </div>

                    <div>
                        <InputText type= "file" placeholder="Upload a new photo" name="photo" onChange={this.photo}/>
                    </div>
                    
                    <div className="i">
                        <Button className="p-button-secondary" label="OK" icon="pi pi-fw pi-check" />
                    </div>
                           
                </div>

                
            </form>
        );
    }
}