import React, { Component } from 'react'
import { Toast } from "primereact/toast";
import { FileUpload } from "primereact/fileupload";
import { Button } from 'primereact/button';
import AlumnoComponent from './AlumnoComponent';
import { PickList } from 'primereact/picklist';
import MaterialComponent from './MaterialComponent';
import { Dropdown } from 'primereact/dropdown';


export class UploadMaterial extends Component{

    constructor(props){
        super(props);
        this.state={
            file : null,
            source: [],
            target: [],
            material: null,
            fileError: null,
            succes: null,
            typeMaterialError: null
        }
        this.handleSubmit= this.handleSubmit.bind(this);
        this.alumnos = new AlumnoComponent(); 
        this.itemTemplate = this.itemTemplate.bind(this);
        this.onChange = this.onChange.bind(this);
        this.materiales = new MaterialComponent();
        this.respuesta= this.respuesta.bind(this);
        this.typeMaterial = this.typeMaterial.bind(this);
        this.error = this.error.bind(this);
    }

    file= this.file.bind(this);

    componentDidMount() {
        this.alumnos.getAllStudents(this.props.urlBase).then(data => this.setState({ source: data }));
    }

    file(event){
        if(event.files[0].size > 1048576){
            this.setState({ fileError: <div className="alert alert-danger" role="alert">The file exceeds its maximum permitted size</div> })
        }else{
            this.setState({file : event.files[0]})
            this.toast.show({
                severity: "info",
                summary: "Success",
                detail: "File Uploaded"
            });
        }
    }

    async handleSubmit(event){
        event.preventDefault();
        this.setState({
            fileError:null
        })
        const formData = new FormData();
        formData.append('pdf', this.state.file);
        formData.append('tipoMaterial', this.state.typeMaterial);
        await this.materiales.crearMaterial(this.props.urlBase,this.props.nickUsuario,formData).then(res => this.respuesta(res.status, res.data));
        this.state.target.forEach(e => this.materiales.asignarAlumnoMaterial(this.props.urlBase,this.state.material.id,e));
    }

    respuesta(status, data){
        if(status === 203){
            data.forEach(e => this.error(e.field, e.defaultMessage))
            console.log(data)
        }else{
            this.setState({
                material: data,
                fileError: null,
                typeMaterialError: null,
                succes: <div className="alert alert-success" role="alert">Successful upload</div>
            })
        }
    }

    error(field,message){
        if(field === "pdf"){
            this.setState({ fileError: <div className="alert alert-danger" role="alert">{message}</div> })
        }else if(field === "tipoMaterial"){
            console.log("hola")
            this.setState({ typeMaterialError: <div className="alert alert-danger" role="alert">{message}</div> })
        }
    }

    itemTemplate(item) {
        return (
            <div className="product-item">
                <div className="product-list-detail">
                    <p className="p-mb-2" style={{fontsize:'100px'}}>{item.nombreCompletoUsuario+" ("+item.grupos.cursos.cursoDeIngles+")"}</p>
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

    typeMaterial(event){
        this.setState({ typeMaterial: event.target.value });
    }

    render(){
        return(
        <React.Fragment>    
            <div className="c" >
                <form onSubmit={this.handleSubmit} style={{width:'700px'}}  >
                {this.state.succes}
                <div className="t">
                    <div><h5>Upload a new material</h5></div>
                </div>

                <div className="i">
                    {this.state.typeMaterialError}
                    <div className="p-inputgroup">
                        <span className="p-inputgroup-addon">
                            <i className="pi pi-sort-down"></i>
                        </span>
                        <Dropdown value={this.state.typeMaterial} options={["Homework", "Exam"]} 
                        onChange={this.typeMaterial} placeholder="Type" name="tipo"/>
                    </div>
                </div>

                {this.state.fileError}
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
                        <Button className="p-button-secondary" label="Upload homework" icon="pi pi-fw pi-upload" />
                    </div>
                </div>
                </form>
            </div>
        </React.Fragment>


        );
    }
}