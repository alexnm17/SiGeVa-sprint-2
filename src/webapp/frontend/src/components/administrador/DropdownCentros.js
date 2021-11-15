import axios from 'axios';
import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css'


class DropdownCentros extends Component {
    state = {
        centroSalud: [],
        seleccionado: ""
    }

    componentDidMount() {
        this.getCentrosSalud()
    }

    getCentrosSalud() {
        axios.get('http://localhost:8080/getAllCentros')
            .then(res => {
                this.setState({ centroSalud: res.data })
            })
    }

    changeHandler = e => {
        this.setState({ [e.target.name]: e.target.value })
    }

    render() {
        const { centroSalud } = this.state.centroSalud
        return (
            <select class="form-select" onChange={this.changeHandler} value={centroSalud}>
                {this.state.centroSalud.map(centroSalud =>
                    <option>{centroSalud.nombre}</option>
                )}
            </select>
        )
    }
}

export default DropdownCentros