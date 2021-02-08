import React, { Component } from 'react'
import AlumnoComponent from './AlumnoComponent';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import { Dropdown } from 'primereact/dropdown';
import axios from 'axios';
import moment from 'moment';
import GrupoComponent from './GrupoComponent';
import AssignmentComponent from './AssignmentComponent';
import Auth from './Auth';
import { Dialog } from 'primereact/dialog';


export default class TeacherGroups extends Component {

    constructor(props) {
        super(props);
        this.state = {
           
            nickUsuario: "",           
            listaGrupos:{
                nombreGrupo: ""
            },
            formularioAsignarProfesor:null,
            formularioDesasignarProfesor:null,
            comprobation: true,
            nombreGrupo:"",
            nombreGrupoError:null,
            succes:null,
            exist:null,
            asigT:"",
            opcion:"",
            grupoS:"",
            displayConfirmation:false
        }
        this.alumnos = new AlumnoComponent();
        this.asignaciones = new AssignmentComponent();
        this.grupos = new GrupoComponent();
        this.mostrarTabla = this.mostrarTabla.bind(this);
        this.botonDelete = this.botonDelete.bind(this);
        this.handleNG = this.handleNG.bind(this); 

    }

    componentDidMount() {
            this.mostrarTabla()
            this.grupos.getAllGroupNames().then(data => this.setState({ listaGrupos: data }));
            
    }

    mostrarTabla(){
        this.asignaciones.getListOfAssignment(this.props.urlBase, this.props.nickUser).then(data => this.setState({ alumnos: data })).catch(error => this.setState({comprobation: false}));
        this.asignaciones.getListOfAssignment(this.props.urlBase, this.props.nickUser).then(data => this.setState({ alumnos: data }));

    }   

   

    assignOption(data, rowData) {
       if(data==="op2"){
        this.setState({ 
            opcion: data,
            grupoS:rowData.grupo.nombreGrupo,
            succes:"",
            nombreGrupoError:"",
            exist:""
         });

       }else{
           
        this.setState({ 
            opcion: data,
            succes:"",
            nombreGrupoError:"",
            exist:""
        });

       }
        
    }

    form(){
        var l = this.state.listaGrupos
        if(String(l)===""){
            return <div className="t"><div><h5>There are no groups to assign</h5></div></div>
        }else{
            return <div>
                <div className="t"><div><h5>Assign Teacher</h5></div></div>
                                <div className="i">
                                {this.state.usernameError}
                                <div className="p-inputgroup">
                                    <InputText field="profesor.nickUsuario" readOnly={true} placeholder="Username" name="profesor.nickUsuario" type="text" value={this.props.nickUser}/>
                                 </div>
                                 </div>

                                <div className="i">
                                {this.state.nombreGrupoError}
                                <div className="p-inputgroup">
                                    <Dropdown field="grupo.nombreGrupo" placeholder="Select a group" name="grupo.nombreGrupo" value={this.state.nombreGrupo} options={this.allGroupNames()} onChange={this.handleNG} />
                                </div>
                                </div>

                                <div className="b">
                                <div className="i">
                                    <Button className="p-button-secondary" label="Guardar" icon="pi pi-fw pi-check"/>
                                </div>
                            </div>
                            </div>
        }
    }
    save = event => {
        event.preventDefault();
        this.setState({
            usernameError: null,
            nombreGrupoError: null
        });
        const grupo = {
            id: {
                nickProfesor: this.props.nickUser,
                nombreGrupo: this.state.nombreGrupo

            },
            profesor: {
                nickUsuario: this.props.nickUser
            },
            grupo: {
                nombreGrupo: this.state.nombreGrupo
            },
            fecha:moment().format('YYYY-MM-DD')
           }
        if(this.state.nombreGrupo===""){
            this.setState({displayConfirmation: true})        
        }else{
            axios.post("http://localhost:8081/asignaciones/new", grupo).then(res => {
                this.respuesta(res.status, res.data);
            })
            this.assignOption("")
            this.mostrarTabla()
        }

          
    }

    delete = event => {

        event.preventDefault();
           this.asignaciones.deleteAsignacion(this.props.urlBase,this.props.nickUser,this.state.grupoS).then(res => {
            this.respuesta(res.status, res.data);
        })

            this.assignOption("")
            this.mostrarTabla()

        }

    formDelete(){
        return <React.Fragment>
                    <div className="b">
                    <div className="i">
                        <Button className="p-button-secondary" label="OK" icon="pi pi-fw pi-check"/>
                    </div>
                    </div>
                </React.Fragment>
    }

    botonDelete(rowData) {  
            return (    
                <React.Fragment>
                                      
                    <Button icon="pi pi-trash" className="p-button-rounded p-button-secondary p-mr-2"  onClick={() => this.assignOption("op2", rowData)}/>
                </React.Fragment>
            ); 
     }
    
    handleNG(event) {
        this.setState({ nombreGrupo: event.target.value });
    }

    allGroupNames(){
        var t=this.state.listaGrupos
        var i=0
        var groupSelectItems = [];
        while(i<t.length){        
        groupSelectItems.push(         
            { label: String(t[i]) , value: String(t[i]) })        
        i+=1
        }
        return groupSelectItems
    }
    respuesta(status, data) {
        if(status===203){
           this.error(data.field, data.defaultMessage)
        }else if(status===201){
            this.setState({
                nombreGrupo:"",
                succes: <div className="alert alert-success" role="alert">Successful assignment</div>
            })
            
            this.mostrarTabla()
        }else{
            this.setState({exist: <div className="alert alert-danger" role="alert">{data}</div>})
        }
    }
    error(campo, mensaje) {
        if (campo === "profesor.nickUsuario") {
            this.setState({ usernameError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        } else if (campo === "grupo.nombreGrupo") {
            this.setState({ nombreGrupoError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        }
    }
    renderFooter(){
        
            return (
                <div>
                   
                </div>
            );
      
    }

    render() {
        if (!this.state.comprobation) {
            return <Auth authority="teacher"></Auth>
        }else{        
            if(this.state.opcion===""){
            return (
                <React.Fragment>
                    <div className="datatable-templating-demo">
                        <div>
                        {this.state.formularioAsignarProfesor}
                        {this.state.formularioDesasignarProfesor}                    
                        <Button icon="pi pi-plus-circle" label="Assign group" className="p-button-secondary" onClick={() => this.assignOption("op1")} />
                        {` `}
                        </div>
                        <div>&nbsp;</div>
                        <DataTable value={this.state.alumnos}>
                            <Column field="grupo.nombreGrupo" header="Group"></Column>
                            <Column field="grupo.cursos.cursoDeIngles" header="Course"></Column>
                            <Column field="fecha" header="Date of assignment"></Column>
                            <Column header="Delete" body={this.botonDelete}></Column>

                        </DataTable>
                    </div>
                </React.Fragment>
            )
            }else if(this.state.opcion==="op1"){
                return (
                    <React.Fragment>
                    <div className="datatable-templating-demo">
                        <div>
                        <div>
                        <Dialog visible={true} style={{ width: '40vw'}} onHide={() => this.setState({opcion: ""})}>
                        <div className="c">
                            <div className="login2 request2">
                                <form onSubmit={this.save}>
                                    {this.state.succes}
                                    {this.state.exist}
                                    {this.form()}
    
                                </form>
                            </div>
                        </div>
                        </Dialog>
                    </div>
                    <Dialog header="Confirmation" visible={this.state.displayConfirmation} style={{ width: '350px' }} footer={this.renderFooter('displayConfirmation')} onHide={() => this.setState({displayConfirmation: false})}>
                         <div className="confirmation-content">
                             <i className="pi pi-exclamation-triangle p-mr-3" style={{ fontSize: '2rem' }} />
                               <span>You muest select a group</span>
                         </div>
                         </Dialog>
                
                        {this.state.formularioDesasignarProfesor}                    
                        <Button icon="pi pi-plus-circle" label="Assign group" className="p-button-secondary" onClick={() => this.formAssignTeacher()} />
                        {` `}
                        </div>
                        <div>&nbsp;</div>
                        <DataTable value={this.state.alumnos}>
                            <Column field="grupo.nombreGrupo" header="Group"></Column>
                            <Column field="grupo.cursos.cursoDeIngles" header="Course"></Column>
                            <Column field="fecha" header="Date of assignment"></Column>
                            <Column header="Delete" body={this.botonDelete}></Column>

                        </DataTable>
                    </div>
                </React.Fragment>
                    
                );
            }else if(this.state.opcion==="op2"){
                return(<React.Fragment>
                    <Dialog visible={true} style={{ width: '40vw'}} onHide={() => this.setState({opcion: ""})}>
            <div className="t"><div><h5>Do you want to stop being the teacher of "{this.state.grupoS}"?</h5></div></div>
            <form onSubmit={this.delete}>
            {this.formDelete()}
            </form>     
            </Dialog>
            <Button icon="pi pi-plus-circle" label="Assign group" className="p-button-secondary" onClick={() => this.formAssignTeacher()} />
                        {` `}
                        <div>&nbsp;</div>
                        <DataTable value={this.state.alumnos}>
                            <Column field="grupo.nombreGrupo" header="Group"></Column>
                            <Column field="grupo.cursos.cursoDeIngles" header="Course"></Column>
                            <Column field="fecha" header="Date of assignment"></Column>
                            <Column header="Delete" body={this.botonDelete}></Column>

                        </DataTable>   
            </React.Fragment>
                );
            }
        }
    }
}








