import React, { Component } from 'react'
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import { connect } from 'react-redux';
import AlumnoComponent from './AlumnoComponent';
import GrupoComponent from './GrupoComponent';
import { Dropdown } from 'primereact/dropdown';
import axios from 'axios';
import { Dialog } from 'primereact/dialog';

class AssignStudent extends Component  {
   
    nickUsuario = this.nickUsuario.bind(this);
    alumnos = new AlumnoComponent();
    grupos = new GrupoComponent();
    handleNG = this.handleNG.bind(this);
        state = {   
        nickUsuario: this.props.astudent === null ? "":this.props.astudent.nickUsuario,
        grupos: {
            nombreGrupo: "",
            cursos: {
            cursoDeIngles: ""
        }
        },
        listaGrupos:{
           nombreGrupo: ""
        },
        listaSinGrupos: this.props.cgselected === null ? "":this.props.cgselected,            
        cursoS:"",
        succes:null,
        comprobation: true,
        displayConfirmation: false,
        A1:{
            nombreGrupo: "",
        },
        A2:{
            nombreGrupo: "",
        },
        B1:{
            nombreGrupo: "",
        },
        B2:{
            nombreGrupo: "",
        }, 
        C1:{
            nombreGrupo: "",
        },
        C2:{
            nombreGrupo: "",
        },
        APRENDIZAJELIBRE:{
            nombreGrupo: "",
        }
    }
         
    componentDidMount() {
        this.alumnos.getAlumnosSinGrupo(this.props.urlBase).then(data =>  this.setState({ listaSinGrupos: data }) ).catch(error => this.setState({ comprobation: false }));
        if(!this.state.listaSinGrupos.includes(this.state.nickUsuario)){
            this.grupos.getAssignmentGroupsByStudent(this.state.nickUsuario).then(data => this.setState({ listaGrupos: data })); 
        }else{
            this.grupos.getAllGroupNames().then(data => this.setState({ listaGrupos: data }));   
        
        }
        this.grupos.getGroupNamesByCourse('A1').then(data =>this.setState({ A1: data }));
        this.grupos.getGroupNamesByCourse('A2').then(data =>this.setState({ A2: data }));
        this.grupos.getGroupNamesByCourse('B1').then(data =>this.setState({ B1: data }));
        this.grupos.getGroupNamesByCourse('B2').then(data =>this.setState({ B2: data }));
        this.grupos.getGroupNamesByCourse('C1').then(data =>this.setState({ C1: data }));
        this.grupos.getGroupNamesByCourse('C2').then(data =>this.setState({ C2: data }));
        this.grupos.getGroupNamesByCourse('APRENDIZAJELIBRE').then(data =>this.setState({ APRENDIZAJELIBRE: data }));
        
    }
    handleNG(event) {
        var c= String(event.target.value).split("(")
        var cc= c[0]
        if(cc!==""){
            this.grupos.getCourseNamesByGroup(cc).then(data => this.setState({ cursoS: data }));
            this.setState({ grupos: {
            nombreGrupo: event.target.value,            
        }});

        }
        
    }  
    nickUsuario(event) {
        this.setState({ nickUsuario: event.target.value });
    }

    allGroupNames(){
        var t=this.state.listaGrupos
        var i=0
        var groupSelectItems = [];
        while(i<t.length){  
            if(this.allGroupNamesA1().includes(String(t[i]))){
                groupSelectItems.push(         
                    String(t[i]) +"(A1)")  

            }   else if(this.allGroupNamesA2().includes(String(t[i]))){
                groupSelectItems.push(         
                    String(t[i]) +"(A2)")  

            }   else if(this.allGroupNamesB1().includes(String(t[i]))){
                groupSelectItems.push(         
                    String(t[i]) +"(B1)")  

            }   else if(this.allGroupNamesB2().includes(String(t[i]))){
                groupSelectItems.push(         
                    String(t[i]) +"(B2)")  

            }   else if(this.allGroupNamesC1().includes(String(t[i]))){
                groupSelectItems.push(         
                    String(t[i]) +"(C1)")  

            }   else if(this.allGroupNamesC2().includes(String(t[i]))){
                groupSelectItems.push(         
                    String(t[i]) +"(C2)")  

            }else{
                groupSelectItems.push(         
                    String(t[i]) +"(APRENDIZAJE LIBRE)")  

            }
              
        i+=1
        }
        return groupSelectItems
    }
    allGroupNames2(){
        this.alumnos.getAlumnosSinGrupo(this.props.urlBase).then(data =>  this.setState({ listaSinGrupos: data }) );
        var t=this.state.listaSinGrupos
        var i=0
        var groupSelectItems = [];
        while(i<t.length){        
        groupSelectItems.push(         
           String(t[i]))        
        i+=1
        }
        return groupSelectItems
    }
    allGroupNamesA1(){
        var t=this.state.A1
        var i=0
        var groupSelectItems = [];
        while(i<t.length){        
        groupSelectItems.push(         
           String(t[i]))        
        i+=1
        }
        return groupSelectItems
    }
    allGroupNamesA2(){
        var t=this.state.A2
        var i=0
        var groupSelectItems = [];
        while(i<t.length){        
        groupSelectItems.push(         
           String(t[i]))        
        i+=1
        }
        return groupSelectItems
    }
    allGroupNamesB1(){
        var t=this.state.B1
        var i=0
        var groupSelectItems = [];
        while(i<t.length){        
        groupSelectItems.push(         
           String(t[i]))        
        i+=1
        }
        return groupSelectItems
    }
    allGroupNamesB2(){
        var t=this.state.B2
        var i=0
        var groupSelectItems = [];
        while(i<t.length){        
        groupSelectItems.push(         
           String(t[i]))        
        i+=1
        }
        return groupSelectItems
    }
    allGroupNamesC1(){
        var t=this.state.C1
        var i=0
        var groupSelectItems = [];
        while(i<t.length){        
        groupSelectItems.push(         
           String(t[i]))        
        i+=1
        }
        return groupSelectItems
    }
    allGroupNamesC2(){
        var t=this.state.C2
        var i=0
        var groupSelectItems = [];
        while(i<t.length){        
        groupSelectItems.push(         
           String(t[i]))        
        i+=1
        }
        return groupSelectItems
    }
    allGroupNamesF(){
        var t=this.state.APRENDIZAJELIBRE
        var i=0
        var groupSelectItems = [];
        while(i<t.length){        
        groupSelectItems.push(         
           String(t[i]))        
        i+=1
        }
        return groupSelectItems
    }
    error(campo, mensaje){
        if(campo === "grupo.cursoDeIngles"){
            this.setState({ cursoError: <div className="alert alert-danger" role="alert">{mensaje}</div> })
        }
    }
    assign  =  event => {
        event.preventDefault();
        var c= String(this.state.grupos.nombreGrupo).split("(")
        var cc= c[0]
            
            if(cc===""){
                this.setState({displayConfirmation: true})    
    
            }else{
             axios.put(this.props.urlBase + "/alumnos/assignStudent/"+this.state.nickUsuario+"/"+cc).then(res => {
                this.respuesta(res.status, res.data)
                })
            }    
        
      }

    respuesta(status, data){
        if(status===203 ){
            data.forEach(e => this.error(e.field, e.defaultMessage))
        }else if(status === 208){
            this.setState({
                succes: <div className="alert alert-danger" role="alert">There are already 12 alumns in this group</div>
            })
        }else if(status===201){
            this.setState({
                succes: <div className="alert alert-success" role="alert">Modified Succesfully</div>
            })
        }
    } 
    renderFooter(){
        return (
            <div>
            </div>
        );
    }

    
   
    
    render() {
        return (
            <div> 
                <div className="c">
                    <div className="login request">
                        <form onSubmit={this.assign}  >
                        {this.state.succes}
                        <Dialog header="Confirmation" visible={this.state.displayConfirmation} style={{ width: '350px' }} footer={this.renderFooter('displayConfirmation')} onHide={() => this.setState({displayConfirmation: false})}>
                         <div className="confirmation-content">
                             <i className="pi pi-exclamation-triangle p-mr-3" style={{ fontSize: '2rem' }} />
                               <span>You must select a group</span>
                         </div>
                         </Dialog>
                            <div className="t"><div><h5>Assign Student</h5></div></div>
                            <div className="i">
                                <div className="p-inputgroup">
                                    <InputText placeholder="Username" name="alumno.nickUsuario" type="text" value={this.state.nickUsuario} onChange={this.nickUsuario}  />
                                 </div>
                                 </div>

                                 <div className="i">
                                <div className="p-inputgroup">
                                    <Dropdown placeholder="Select a group" name="grupo.nombreGrupo"  field="grupo.nombreGrupo" value={this.state.grupos.nombreGrupo} options={this.allGroupNames()} onChange={this.handleNG} />
                                </div>
                                </div>

                                <div className="b">
                                <div className="i">
                                    <Button className="p-button-secondary" label="Guardar" icon="pi pi-fw pi-check"/>

                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        );
    }
}

function mapStateToProps(state) {
    return {
        astudent: state.astudent,
        cgselected: state.cgselected
    }
}

export default connect(mapStateToProps)(AssignStudent); 