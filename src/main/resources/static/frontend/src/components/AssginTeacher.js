import React, { Component } from 'react'
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import AlumnoComponent from './AlumnoComponent';
import GrupoComponent from './GrupoComponent';
import { Dropdown } from 'primereact/dropdown';
import AssignmentComponent from './AssignmentComponent';
import axios from 'axios';
import moment from 'moment';





export default class AssignTeacher extends Component  {
   
    

    constructor(props) {
        super(props);
        this.state = {
   
        nickUsuario:this.props.nickUser,
        nombreGrupo:"",
        fecha:moment().format('YYYY-MM-DD'),
        listaGrupos:{
           nombreGrupo: ""
        },
        succes:null,
        exist:null 
    } 
        
    

    this.handleNG = this.handleNG.bind(this);
    this.save = this.save.bind(this);
    this.alumnos = new AlumnoComponent();
    this.grupos = new GrupoComponent();
    this.asignaciones = new AssignmentComponent();
    this.form=this.form.bind(this);


    }
    
    handleNG(event) {
        this.setState({ nombreGrupo: event.target.value });
    }  

    allGroupNames(){

        var t=this.state.listaGrupos
        var i=0
        var groupSelectItems = [
            
        ];
        while(i<t.length){        
        groupSelectItems.push(         
            { label: String(t[i]) , value: String(t[i]) })        
        i+=1
        }
        return groupSelectItems
    }
    form(){
        var l = this.state.listaGrupos
        if(l==""){

            return <div className="t"><div><h5>There is no group to assign</h5></div></div>


        }else{
            

            return <div>
                <div className="t"><div><h5>Assign Teacher</h5></div></div>
                            <div className="i">
                                <div className="p-inputgroup">
                                    <InputText placeholder="Username" name="profesor.nickUsuario" type="text" value={this.props.nickUser}/>
                                 </div>
                                 </div>

                                 <div className="i">
                                <div className="p-inputgroup">
                                    <Dropdown placeholder="Select a group" name="grupo.nombreGrupo" value={this.state.nombreGrupo} options={this.allGroupNames()} onChange={this.handleNG} />
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

        const grupo = {
            id:{
                nickProfesor:this.props.nickUser,
                nombreGrupo: this.state.nombreGrupo

            },
            profesor:{
                nickUsuario:this.props.nickUser
            },
            grupo:{
                nombreGrupo: this.state.nombreGrupo
            },
            fecha:moment().format('YYYY-MM-DD')
           
        }

        axios.post("http://localhost:8081/asignaciones/new", grupo).then(res => {
            this.respuesta(res.status, res.data);
        })
        
    }
   

    respuesta(status, data){
        console.log(status);
        if(status===203){
            data.forEach(e => this.error(e.field, e.defaultMessage))
        }else if(status===201){
            this.setState({               

                nombreGrupo:"",
                succes: <div className="alert alert-success" role="alert">Successful assignment</div>
            })
        }else{
            this.setState({exist: <div className="alert alert-danger" role="alert">{data}</div>})
        }
    }

    
    componentDidMount() {
        this.asignaciones.getListOfEmptyAssignmentGroup(this.props.urlBase).then(data => this.setState({ listaGrupos: data }));
        
    }
    
    

    
    
    
    render() {
        console.log(this.state.listaGrupos);
        return (
            <div>
                <div className="c">
                    <div className="login request">
                        <form onSubmit={this.save}>
                            {this.state.succes}
                            {this.state.exist}
                            {this.form()}

                        </form>
                    </div>
                </div>
            </div>
        );
    }
}



