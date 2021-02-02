import React, { Component } from 'react'
import axios from 'axios';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import Pdf from './all-pages';
import { Dialog } from 'primereact/dialog';

 export  class MaterialStudent extends Component{

    constructor(props){
        super(props);
        this.state={
            nickUsuario: this.props.nickUser,
            urlBase: this.props.urlBase, 
            materiales: null,
            visualizarPDF: null,
        }
        this.mostrarMaterial= this.mostrarMaterial.bind(this);
        this.obtenerMaterial= this.obtenerMaterial.bind(this);
        this.botonVerMaterial = this.botonVerMaterial.bind(this);
        this.botonDescargarMaterial = this.botonDescargarMaterial.bind(this);
    }

    componentDidMount() {
        this.obtenerMaterial();
    }

    async obtenerMaterial() {
        await axios.get(this.state.urlBase+"/materiales/getMaterialByAlumno/"+this.state.nickUsuario).then(res => this.setState({materiales: res.data}))
        console.log(this.state.materiales);
    }

    mostrarMaterial(){
        return(
            <DataTable value={this.state.materiales}>
                <Column header="Material name" field="nombreMaterial"></Column>
                <Column header="Upload date" field="fechaSubida"></Column>
                <Column header="Preview" body={this.botonVerMaterial}></Column>
                <Column header="Download" body={this.botonDescargarMaterial}></Column>
                <Column header="Feedback"></Column>
                {/* Nuestra idea es que al pulsar en feedback, se llame a un método en el que aparezca un <Dialog> donde aparezcan dos
                cosas: la valoracion y el comentario. Fijaros en  mostrarFormUpload de MaterialTeacher para hacerlo igual 
                
                Pd: no usar redux*/}
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
            <a href={"/material/"+rowData.id+".pdf"} download={rowData.nombreMaterial}><i className="pi pi-download"></i></a>
        )
    }

    render(){
        return (
            <React.Fragment>
                {this.mostrarMaterial()}
                {this.state.visualizarPDF}
            </React.Fragment>
        );
    }
}