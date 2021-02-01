import React, { Component } from 'react'
import axios from 'axios';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { Document } from 'react-pdf'

 export  class MaterialTeacher extends Component{

    constructor(props){
        super(props);
        this.state={
            nickUsuario: this.props.nickUser,
            urlBase: this.props.urlBase, 
            materiales: null,
            visualizarPDF: null
        }
        this.mostrarMaterial= this.mostrarMaterial.bind(this);
        this.obtenerMaterial= this.obtenerMaterial.bind(this);
        this.botonVerMaterial = this.botonVerMaterial.bind(this);
    }

    componentDidMount() {
        this.obtenerMaterial();
    }

    async obtenerMaterial() {
        await axios.get(this.state.urlBase+"/materiales/getMaterialByProfesor/"+this.state.nickUsuario).then(res => this.setState({materiales: res.data}))
        console.log(this.state.materiales);
    }

    mostrarMaterial(){
        return(
            <DataTable value={this.state.materiales}>
                <Column header="Material name" field="nombreMaterial"></Column>
                <Column header="Upload date" field="fechaSubida"></Column>
                <Column header="Preview" body={this.botonVerMaterial}></Column>
            </DataTable>
        );

    }

    botonVerMaterial(rowData){
        
        return(
            <React.Fragment>
                <Button icon="pi pi-eye" className="p-button-rounded p-button-secondary p-mr-2" onClick={() => 
                    this.setState({visualizarPDF:
                        <Document
                            file="/material/1.pdf"
                        ></Document>
                    })
                
                }
                />
                        
            </React.Fragment>
        );
       
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