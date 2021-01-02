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


export class CreatePremiado extends Component{

    constructor(props){
        super(props)
        this.state={
            nickNameError: null,
            descriptionError: null,
            photoError: null,
            nickusuario : "",
            description: "",
            photo: null,
            
            
        }
        this.handleSubmit= this.handleSubmit.bind(this);  
        this.respuesta= this.respuesta.bind(this);  
        this.error= this.error.bind(this); 
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

    async handleSubmit(event){
        event.preventDefault()
        this.setState({
            nickNameError: null,
            descriptionError: null,
            photoError: null,
        })
        const formData = new FormData();
        formData.append('photo', this.state.photo) ;
        formData.append('nickUsuario', this.state.nickusuario) ;
        formData.append('description', this.state.description) ;
        await this.premiados.postNewPremiado(this.props.urlBase, this.props.fecha, formData).then(res => this.respuesta(res.status, res.data));
    }

    respuesta(status,data){
        console.log(status)
        if(status===203){
            data.forEach(e => this.error(e.field, e.defaultMessage))
        }else if(status===201){
            this.setState({
             nickusuario : "",
             description: "",
             photo: null,
            })
        }else if(status===204){
            this.setState({nickNameError: <div className="alert alert-danger" role="alert">The username doesn't exists</div>})
        }else if(status===208){
            this.setState({nickNameError: <div className="alert alert-danger" role="alert">This student is already added in this week</div>})
        }
    }

    error(campo, mensaje){
        if(campo === "nickUsuario"){
            this.setState({ nickNameError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        }else if(campo === "description"){
            this.setState({ descriptionError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        }else if(campo === "photo"){
            this.setState({ photoError: <div className="alert alert-danger" role="alert">Required field</div> })
        }
    }

    render(){
        return(
            <div className="c">
                <form onSubmit={this.handleSubmit}>
                <div className="t">
                    <div><h5>Add a new awarded student</h5></div>
                </div>
                <div className="i">
                {this.state.nickNameError}

                    <div className="p-inputgroup">
                    <span className="p-inputgroup-addon">
                        <i className="pi pi-user"></i>
                    </span>
                        <InputText type= "text" placeholder="Username" name="nickusuario" onChange={this.nickusuario} />
                    </div>
                </div>


                <div className="i">
                {this.state.descriptionError}

                    <div className="p-inputgroup">
                    <span className="p-inputgroup-addon">
                        <i className="pi pi-align-center"></i>  
                    </span>
                        <InputText type= "textarea" placeholder="description" name="description" onChange={this.description} />
                    </div>
                </div>


                <div className="i">
                    {this.state.photoError}
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
                        <Button className="p-button-secondary" label="Add the student" icon="pi pi-fw pi-upload" />
                    </div>
                </div>
                </form>
            </div>
        )

    }
}
