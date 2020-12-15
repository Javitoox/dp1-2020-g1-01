import React, { Component } from 'react'
import { BrowserRouter as Router, Route } from 'react-router-dom'
import EditStudent from './EditStudent'
export class EditPreStudent extends Component {
    render() {
        console.log("se esta redireccionando")
        return (
            <div>
                <Router>
                <Route path = "/editStudent" render={() =>
							<EditStudent></EditStudent>
					} /> 
                </Router>
            </div>

        )
    }
}