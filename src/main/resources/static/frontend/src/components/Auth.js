import React, { Component } from 'react'

export default class Auth extends Component {
    render() {
        return (
            <div className="alert alert-warning">
                <strong>¡Access denied!</strong> You are not logged as a {this.props.authority}
            </div>
        )
    }
}
