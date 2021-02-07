import React, { Component } from 'react'
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import GrupoComponent from './GrupoComponent';
import { Dropdown } from 'primereact/dropdown';
import axios from 'axios';
import Auth from './Auth';
import { Dialog } from 'primereact/dialog';
import Alumnos from './Alumnos';
import {selectCreatedGroup}  from '../actions/index';
import { bindActionCreators } from 'redux';
import { connect } from 'react-redux';

class CreateGroup extends Component {
    constructor(props) {
        super(props);
        this.state = { 
            grupoS:{
                nombreGrupo: "",
                cursos:{
                    cursoDeIngles:""
                }
            },
            listaGrupos:{
                nombreGrupo: ""
            },
            nombreGrupoError:null,
            cursoError:null,
            succes:null,
            exist:null,
            comprobation: true,
            displayConfirmation: false
        }
        this.grupos = new GrupoComponent();
        this.save = this.save.bind(this);
        this.handleCI = this.handleCI.bind(this);
        this.handleNG = this.handleNG.bind(this);
    }
    save = event => {
        event.preventDefault();
        this.setState({
            nombreGrupoError:null,
            cursoError:null
        });
        const grupo = {
            nombreGrupo:this.state.grupoS.nombreGrupo,
            cursos:{
                cursoDeIngles:this.state.grupoS.cursos.cursoDeIngles
            } 
        }
        if(this.state.grupoS.cursos.cursoDeIngles===""){
            this.setState({displayConfirmation: true})    
        }else{
            axios.post("http://localhost:8081/grupos/new", grupo).then(res => {
                this.respuesta(res.status, res.data);
            })
            this.props.selectCreatedGroup(grupo)
        }
    }
    handleCI(event) {
        this.setState({
            grupoS:{
                nombreGrupo:this.state.grupoS.nombreGrupo,
                cursos:{
                    cursoDeIngles:event.target.value
                } 
            }
        });
    }
    handleNG(event) {
        this.setState({grupoS:{
            
           nombreGrupo:event.target.value,
           cursos:{
            cursoDeIngles:this.state.grupoS.cursos.cursoDeIngles
        }
        }});
    }
    respuesta(status, data){
        console.log(status);
        this.setState({
            exist: "",
            succes: ""})
        if(status===203){
            console.log(data)
            this.error(data.field, data.defaultMessage)
        }else if(status===201){
            this.setState({
                grupoS:{
                    nombreGrupo:"",
                    cursos:{
                        cursoDeIngles:""
                    }
                },
                succes: <div className="alert alert-success" role="alert">Successful creation</div>
            })
        }else if(status===226){
            
            this.setState({
                exist: <div className="alert alert-danger" role="alert">The group already exists</div>})
        }else if(status===204){
            this.setState({exist: <div className="alert alert-danger" role="alert">You must choose a course</div>})
        }else{
            this.setState({exist: <div className="alert alert-danger" role="alert">{data}</div>})
        }
    }
    error(campo, mensaje){
        console.log("p")
        if(String(campo) === "nombreGrupo"){
            console.log("g")
            this.setState({ nombreGrupoError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        }else if(campo === "grupo.cursoDeIngles"){
            console.log("d")
            this.setState({ cursoError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        }
    }
    componentDidMount() {
        this.grupos.getAllGroupNames().then(data => this.setState({ listaGrupos: data })).catch(error => this.setState({comprobation: false}));
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
        const courseSelectItems = [
            { label: 'A1', value: 'A1' },
            { label: 'A2', value: 'A2' },
            { label: 'B1', value: 'B1' },
            { label: 'B2', value: 'B2' },
            { label: 'C1', value: 'C1' },
            { label: 'C2', value: 'C2' },
            { label: 'Free learning', value: 'APRENDIZAJELIBRE' }
        ];
        <React.Fragment>
                <Alumnos grupo={'pepe'}></Alumnos>

        </React.Fragment>

        return (
                <div>
                    <div className="c">
                        <div className="login2 request2">
                            <form onSubmit={this.save}>
                            {this.state.succes}
                            {this.state.exist}

                        <Dialog header="Confirmation" visible={this.state.displayConfirmation} style={{ width: '350px' }} footer={this.renderFooter('displayConfirmation')} onHide={() => this.setState({displayConfirmation: false})}>
                         <div className="confirmation-content">
                             <i className="pi pi-exclamation-triangle p-mr-3" style={{ fontSize: '2rem' }} />
                               <span>You must select a course</span>
                        </div>
                        </Dialog>

                                <div className="t"><div><h5>Create Group</h5></div></div>

                                    <div className="i">
                                    <div className="p-inputgroup">
                                    <Dropdown name="grupo.cursoDeIngles" value={this.state.grupoS.cursos.cursoDeIngles} placeholder="Select a course" options={courseSelectItems} onChange={this.handleCI} />

                                    </div>
                                    </div>

                                    <div className="i">
                                    {this.state.nombreGrupoError}
                                    <div className="p-inputgroup">
                                    <InputText field="nombreGrupo" value={this.state.grupoS.nombreGrupo} placeholder="Group's name" name="nombreGrupo" onChange={this.handleNG}/>

                                    </div>
                                    </div>

                                    <div className="b">
                                    <div className="i">
                                    <Button className="p-button-secondary" label="Save" icon="pi pi-fw pi-check"/>

                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            )
        }
    }
}
function  matchDispatchToProps(dispatch) {

    return bindActionCreators({
        selectCreatedGroup: selectCreatedGroup}, dispatch)
}

export default connect(null , matchDispatchToProps)(CreateGroup) 