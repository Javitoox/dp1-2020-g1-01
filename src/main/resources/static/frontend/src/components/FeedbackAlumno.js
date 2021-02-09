import React, { Component } from 'react'
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { Rating } from 'primereact/rating';
import MaterialComponent from './MaterialComponent';
import Auth from './Auth';

export class FeedbackAlumno extends Component{
    constructor(props){
        super(props);
        this.state = {
            comment: "",
            rate: 0,
            id:null,
            succes: null,
            comprobation: true
        }
        this.materiales= new MaterialComponent();
        this.obtenerFeedback= this.obtenerFeedback.bind(this);
        this.handleSubmit= this.handleSubmit.bind(this);
        this.comment = this.comment.bind(this);
        this.rate = this.rate.bind(this);
    }


    componentDidMount() {
        this.obtenerFeedback();
    }

    async obtenerFeedback(){
        await this.materiales.getFeedback(this.props.urlBase, this.props.nickUsuario, this.props.id).then(res => this.setState({
            id: res.data.id,
            comment: res.data.comentario,
            rate: res.data.valoracion
        })).catch(error => this.setState({comprobation: false}));

    }

    comment(event){
        this.setState({comment: event.target.value});
    }

    rate(event){
        this.setState({rate: event.target.value});
    }

    handleSubmit(event){
        event.preventDefault();
        const formData = new FormData();
        formData.append('rate', this.state.rate) ;
        formData.append('comment', this.state.comment) ;
        formData.append('id', this.state.id);

        this.materiales.updateFeedback(this.props.urlBase, formData).then(() => this.setState({
            succes: <div className="alert alert-success" role="alert">The feedback has been send successfully</div>
        }));
    }

    render(){
        if (!this.state.comprobation) {
            return <Auth authority="student"></Auth>
        } else {
            return(
                <div className="c">
                    <form onSubmit={this.handleSubmit}>
                    {this.state.succes}
                    <div className="t">
                        <div><h5>Add rate and comment</h5></div>
                    </div>
                    <div className="i">
                        <div className="p-inputgroup">
                            <InputText type= "text" placeholder="Comment" value={this.state.comment} onChange={this.comment} />
                        </div>
                    </div>
                    <div className="i " >
                        <div className="p-inputgroup c">
                            <Rating value={this.state.rate} cancel={false} onChange={this.rate} />
                        </div>
                    </div>
                    

                    <div className="b">
                        <div className="i">
                            <Button className="p-button-secondary" label="Add feedback" icon="pi pi-fw pi-upload" />
                        </div>
                    </div>
                    </form>
                </div>
            );
        }
    }
}
