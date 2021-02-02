import React, { Component } from 'react'
import { Toast } from "primereact/toast";
import { FileUpload } from "primereact/fileupload";
import { Button } from 'primereact/button';
import axios from 'axios';
import AlumnoComponent from './AlumnoComponent';
import { PickList } from 'primereact/picklist';

export class UploadMaterial extends Component{

    constructor(props){
        super(props);
        this.state={
            file : null,
            source: [],
            target: [],
            material: null
        }
        this.handleSubmit= this.handleSubmit.bind(this);
        this.alumnos = new AlumnoComponent(); 
        this.itemTemplate = this.itemTemplate.bind(this);
        this.onChange = this.onChange.bind(this);
    }


    file= this.file.bind(this);

    componentDidMount() {
        this.alumnos.getAllStudents(this.props.urlBase).then(data => this.setState({ source: data }));
    }

    file(event){
        this.setState({file : event.files[0]})
        this.toast.show({
            severity: "info",
            summary: "Success",
            detail: "File Uploaded"
        });
    }

    async handleSubmit(){
        const formData = new FormData();
        formData.append('pdf', this.state.file);

        await axios.post(this.props.urlBase+"/materiales/añadirMaterial/"+this.props.nickUsuario, formData).then(res => this.setState({material: res.data}));
        this.state.target.forEach(e => axios.put(this.props.urlBase+"/feedback/"+this.state.material.id+"/añadirAlumno/",e));
    }

    itemTemplate(item) {
        return (
            <div className="product-item">
                <div className="product-list-detail">
                    <p className="p-mb-2" style={{fontsize:'100px'}}>{item.nombreCompletoUsuario}</p>
                </div>
            </div>
        );
    }

    onChange(event) {
        this.setState({
            source: event.source,
            target: event.target
        });
    }

    render(){
        return(
            <div className="c" >
                <form onSubmit={this.handleSubmit} style={{width:'700px'}}  >
                <div className="t">
                    <div><h5>Upload a new material</h5></div>
                </div>

                <div className="i c">
                    <Toast
                    ref={(el) => {
                        this.toast = el;
                    }}
                    ></Toast>

                    <div className="p-inputgroup c">
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

                <div className="picklist-demo">
                    <div className="card">
                        <PickList source={this.state.source} target={this.state.target} itemTemplate={this.itemTemplate}
                            sourceHeader="Available Students" targetHeader="Selected Students"
                            sourceStyle={{ height: '700px' }} targetStyle={{ height: '700px' }}
                            onChange={this.onChange}></PickList>
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