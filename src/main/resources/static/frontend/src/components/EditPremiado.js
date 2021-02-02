import React, { Component } from 'react'
import AlumnoComponent from './AlumnoComponent';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import "primeicons/primeicons.css";
import "primereact/resources/themes/saga-blue/theme.css";
import "primereact/resources/primereact.css";
import "primeflex/primeflex.css";
import "../css/wallOfFame.css";
import { Toast } from "primereact/toast";
import { FileUpload } from "primereact/fileupload";

export class EditPremiado extends Component{
    constructor(props){
        super(props)
        this.state={
            photoError: null,
            nickusuario : "",
            description: "",
            photo: null,
            premiado:this.props.premiado
            
        }
        this.handleSubmitEdit= this.handleSubmitEdit.bind(this);  
        this.premiados = new AlumnoComponent();

    }
    nickusuario= this.nickusuario.bind(this);
    description= this.description.bind(this);
    photo= this.photo.bind(this);
    

    nickusuario(event){
        this.setState({nickusuario : event.target.value})
    }

    description(event){
        this.setState({description : event.target.value})
    }

    photo(event){
        if(event.files[0].size > 1048576){
            this.setState({ photoError: <div className="alert alert-danger" role="alert">The photo exceeds its maximum permitted size</div> })
        }else{
            this.setState({photo : event.files[0]})
            this.toast.show({
                severity: "info",
                summary: "Success",
                detail: "File Uploaded"
            });
        }
    }

    async handleSubmitEdit(event){
        event.preventDefault()
        const formData = new FormData();
        formData.append('id', this.state.premiado.id) ;
        formData.append('photo', this.state.photo) ;
        formData.append('description', this.state.description) ;
        formData.append('nickUsuario', this.state.premiado.alumnos.nickUsuario) ;
        await this.premiados.editPremiado(this.props.urlBase, formData).then(() => this.setState({editForm: false}))
        this.setState({
            nickusuario : "",
            description: "",
            photo: null,
        })
        window.location.assign("/walloffame")
    }

    render(){
        return(
            <div className="c">
                <form onSubmit={this.handleSubmitEdit}>
                    <div className="t">
                        <div><h5>You're editing: {this.state.premiado.alumnos.nombreCompletoUsuario} </h5></div>
                    </div>

                    <div className="i">
                        <div className="p-inputgroup">
                        <span className="p-inputgroup-addon">
                            <i className="pi pi-align-center"></i>  
                        </span>
                            <InputText type= "textarea" placeholder={this.state.premiado.descripcion} name="description" onChange={this.description} />
                        </div>
                    </div>

                    {this.state.photoError}

                    <div className="i">
                    <Toast
                    ref={(el) => {
                        this.toast = el;
                    }}
                    ></Toast>

                    <div className="p-inputgroup">
                    <FileUpload
                        name="demo[]"
                        customUpload 
                        uploadHandler={this.photo}
                        accept="image/*"
                        emptyTemplate={
                        <p className="p-m-0">Drag and drop files to here to upload.</p>
                    }
                    />
                    </div>
                    </div>

                    <div className="b">
                        <div className="i">
                            <Button className="p-button-secondary" label="Edit the awarded student" icon="pi pi-fw pi-upload"/>
                        </div>
                    </div>
                </form>    
            </div>
        );
    }
    

}