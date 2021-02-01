import React, { Component } from 'react'
import axios from 'axios';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import Pdf from './all-pages';
import { Dialog } from 'primereact/dialog';
import {UploadMaterial} from './UploadMaterial'

 export  class MaterialTeacher extends Component{

    constructor(props){
        super(props);
        this.state={
            nickUsuario: this.props.nickUser,
            urlBase: this.props.urlBase, 
            materiales: null,
            visualizarPDF: null,
            formularioUpload: null
        }
        this.mostrarMaterial= this.mostrarMaterial.bind(this);
        this.obtenerMaterial= this.obtenerMaterial.bind(this);
        this.botonVerMaterial = this.botonVerMaterial.bind(this);
        this.botonDescargarMaterial = this.botonDescargarMaterial.bind(this);
        this.mostrarBotonUpload = this.mostrarBotonUpload.bind(this);
        this.mostrarFormUpload = this.mostrarFormUpload.bind(this);
    }

    componentDidMount() {
        this.obtenerMaterial();
    }

    async obtenerMaterial() {
        await axios.get(this.state.urlBase+"/materiales/getMaterialByProfesor/"+this.state.nickUsuario).then(res => this.setState({materiales: res.data}))
        console.log(this.state.materiales);
    }

    mostrarBotonUpload(){
        return(
        <div className="mt-3 mb-3">  
            <Button className="p-button-secondary" label="Upload new content" icon="pi pi-plus" onClick={()=>this.mostrarFormUpload()}/>
        </div>
        );
    }

    mostrarFormUpload(){
        this.setState({
            formularioUpload: 
                <Dialog visible={true} style={{ width: '40vw' }} onHide={() => this.setState({formularioUpload: null})}>
                    <UploadMaterial urlBase={this.state.urlBase} nickUsuario={this.state.nickUsuario}></UploadMaterial>
                </Dialog>
        })

    }

    mostrarMaterial(){
        return(
            <DataTable value={this.state.materiales}>
                <Column header="Material name" field="nombreMaterial"></Column>
                <Column header="Upload date" field="fechaSubida"></Column>
                <Column header="Preview" body={this.botonVerMaterial}></Column>
                <Column header="Download" body={this.botonDescargarMaterial}></Column>
            </DataTable>
        );

    }

    botonVerMaterial(rowData){
        return(
            <React.Fragment>
                <Button icon="pi pi-eye" className="p-button-rounded p-button-secondary p-mr-2" onClick={() => 
                    this.setState({visualizarPDF:
                        <Dialog visible={true} style={{ width: '40vw' }} onHide={() => this.setState({visualizarPDF: null})}> 
                            <center><Pdf id = {rowData.id}/></center>
                        </Dialog>
                    })
                }
                />      
            </React.Fragment>
        );
    }

    botonDescargarMaterial(rowData){
        return(
            <a href={"/material/"+rowData.id+".pdf"} download><i className="pi pi-download"></i></a>
        )
    }

    render(){
        return (
            <React.Fragment>
                {this.mostrarBotonUpload()}
                {this.mostrarMaterial()}
                {this.state.visualizarPDF}
                {this.state.formularioUpload}
            </React.Fragment>
        );
    }
}