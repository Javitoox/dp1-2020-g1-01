import React, { Component } from 'react'
import { Checkbox } from 'primereact/checkbox'

export default class Inject extends Component {

    change = this.change.bind(this)

    state = {
        button: false
    }

    change(event){
        if(this.props.activated) this.props.onActivate()
        this.setState({ button: !this.state.button })
    }

    activate(){
        if(this.state.button){
            return this.props.content
        }
    }

    render() {
        return (
            <div>
                <div className="i">
                    <div className="p-field-checkbox">
                        <Checkbox onChange={this.change} checked={this.state.button} />
                                    &nbsp;&nbsp;{this.props.message}
                    </div>
                </div>
                {this.activate()}
            </div>
        )
    }
}
