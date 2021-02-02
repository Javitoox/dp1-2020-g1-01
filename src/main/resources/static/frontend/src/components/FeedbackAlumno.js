import React, { Component } from 'react'
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { Rating } from 'primereact/rating';
import MaterialComponent from './MaterialComponent';

export class FeedbackAlumno extends Component{
    constructor(props){
        super(props);
        this.state = {
            comment: null,
            rate: null,
            id:null
        }
        this.materiales= new MaterialComponent();
        this.obtenerFeedback= this.obtenerFeedback.bind(this);
        this.handleSubmit= this.handleSubmit.bind(this);
    }
    comment = this.comment.bind(this);


    componentDidMount() {
        this.obtenerFeedback();
    }

    async obtenerFeedback(){
        await this.materiales.getFeedback(this.props.urlBase, this.props.nickUsuario, this.props.id).then(res => this.setState({
            id: res.data.id,
            comment: res.data.comentario,
            rate: res.data.valoracion
        }))

    }

    comment(event){
        this.setState({comment: event.target.value});
    }

    handleSubmit(event){
        event.preventDefault();
        const formData = new FormData();
        formData.append('rate', this.state.rate) ;
        formData.append('comment', this.state.comment) ;
        formData.append('id', this.state.id);
        this.materiales.updateFeedback(this.props.urlBase, formData);
    }

    render(){
        return(
            <div className="c">
                <form onSubmit={this.handleSubmit}>
                <div className="t">
                    <div><h5>Add rate and comment</h5></div>
                </div>
                <div className="i">
                    <div className="p-inputgroup">
                        <InputText type= "text" placeholder={this.state.comment} onChange={this.comment} />
                    </div>
                </div>

                <div className="i">
                    <div className="p-inputgroup">
                        <Rating value={this.state.rate} onChange={(e) => this.setState({rate: e.value})} />
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