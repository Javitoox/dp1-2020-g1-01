import React, { Component } from 'react'
import { Toast } from "primereact/toast";
import { FileUpload } from "primereact/fileupload";
import { Button } from 'primereact/button';
import axios from 'axios';

export class UploadMaterial extends Component{
    constructor(props){
        super(props);
        this.state={
            file : null


        }
        this.handleSubmit= this.handleSubmit.bind(this);
    }
    file= this.file.bind(this);

    file(event){
        this.setState({file : event.files[0]})
        this.toast.show({
            severity: "info",
            summary: "Success",
            detail: "File Uploaded"
        });
    }

    async handleSubmit(event){
        event.preventDefault()
        console.log(this.state.file);
        const formData = new FormData();
        formData.append('pdf', this.state.file);

        await axios.post(this.props.urlBase+"/materiales/añadirMaterial/"+this.props.nickUsuario, formData).then(()=>console.log("hola maribel"));
    }

    render(){
        return(
            <div className="c">
            <form onSubmit={this.handleSubmit}>
            <div className="t">
                <div><h5>Upload a new material</h5></div>
            </div>

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
                    uploadHandler={this.file}
                    accept="*"
                    emptyTemplate={
                    <p className="p-m-0">Drag and drop file to here to upload.</p>
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




        );
    }
}