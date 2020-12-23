import React, { Component } from 'react'

export default class Auth extends Component {
    render() {
        return (
            <div className="alert alert-warning">
                <strong>¡Access denied!</strong> You are not identified as a {this.props.authority}
            </div>
        )
    }
}
