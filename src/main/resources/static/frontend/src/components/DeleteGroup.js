import React, { Component } from 'react'
import { Button } from 'primereact/button';
import GrupoComponent from './GrupoComponent';
import { Dropdown } from 'primereact/dropdown';
import { InputText } from 'primereact/inputtext';
import axios from 'axios';
import Auth from './Auth';



export class DeleteGroup extends Component {
    constructor(props) {
        super(props);
        this.state = { 
            grupoS:{
                nombreGrupo: ""
            },
            listaGrupos:{
                nombreGrupo: ""
            },
            comprobation: false,

        }
        this.grupos = new GrupoComponent();
        this.delete = this.delete.bind(this);
        this.handleNG = this.handleNG.bind(this);
        this.allGroupNames= this.allGroupNames.bind(this);
        this.form=this.form.bind(this);
    }
    delete = event => {
        event.preventDefault();

            if(this.state.grupoS.nombreGrupo===""){
                window.alert("You must select a group")

            }else{
                axios.delete("http://localhost:8081/grupos/delete/"+this.state.grupoS.nombreGrupo, {withCredentials: true}).then(res => {
                    this.respuesta(res.status, res.data);        })
            }
           

        

        
        
        
       
    }


    handleNG(event) {
        this.setState({grupoS:{            
           nombreGrupo:event.target.value
        }});
    }

    allGroupNames(){

        var t=this.state.listaGrupos
        console.log(t);
        var i=0
        var groupSelectItems = [];
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

            return <div className="t"><div><h5>There are no groups to delete</h5></div></div>


        }else{
            

            return <div>
                <div className="t"><div><h5>Delete Group</h5></div></div>

                                <div className="i">
                                <div className="p-inputgroup">
                                <InputText value={this.state.grupoS.nombreGrupo} placeholder="Group's name" name="nombreGrupo" onChange={this.handleNG} hidden={true}/>
                                <Dropdown name="nombreGrupo" placeholder="Select group" value={this.state.grupoS.nombreGrupo} options={this.allGroupNames()} onChange={this.handleNG} />

                                </div>
                                </div>

                                <div className="b">
                                <div className="i">
                                <Button className="p-button-secondary" label="Eliminar" icon="pi pi-fw pi-check"/>

                                </div>
                                </div>
                                
                            </div>


        }
    }
    respuesta(status, data){
        console.log("hola?");

        console.log(status);
        if(status===111){
            console.log("no va");

            data.forEach(e => this.error(e.field, e.defaultMessage))
        }else if(status===200){
            console.log("va");

            this.setState({               

                grupoS:{
                    nombreGrupo:""
                },
                succes: <div className="alert alert-success" role="alert">Successful delete</div>
            })
        }else{
            this.setState({exist: <div className="alert alert-danger" role="alert">{data}</div>})
        }
    }

    componentDidMount() {
        axios.get("http://localhost:8081/auth", {withCredentials: true}).then(res => {
            if(res.data==="profesor"){
                this.setState({comprobation: true})
                }
            })
        this.grupos.getEmptyGroupNames().then(data => this.setState({ listaGrupos: data }));
    }

    render() {
        if (!this.state.comprobation) {
            return <Auth authority="teacher"></Auth>
        }else{
            console.log(this.state);
            return (

                <div>
                    <div className="c">
                        <div className="login request">
                            <form onSubmit={this.delete}>
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
}

export default (DeleteGroup);