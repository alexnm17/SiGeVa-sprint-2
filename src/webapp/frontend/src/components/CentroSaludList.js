import axios from 'axios';
import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css'


class centroSaludList extends Component {
    state = {
        centroSalud: []
    }

    componentDidMount() {
        this.getCentrosSalud()
    }

    getCentrosSalud() {
        axios.get('http://localhost:8080/getCentrosSalud')
            .then(res => {
                this.setState({ centroSalud: res.data })
            })
    }

    ModificarClickHandler = e => {
        e.preventDefault()
        console.log(e.target.id)
    }

    EliminarClickHandler = e => {
        e.preventDefault()
        console.log(e.target.id)
        /*axios.get('http://localhost:8080/',e.target.id)
            .then(res => {
                this.setState({ usuarios: res.data })
            })*/
        this.getCentrosSalud()
    }

    render() {
        return (
            <table class="table" style={{ marginTop: 15, marginLeft: 15 }}>
                <tr>
                    <th>Nombre</th>
                    <th>Municipio</th>
                </tr>
                {this.state.centroSalud.map(centroSalud =>
                    <tr key={centroSalud.nombre}>
                        <td>{centroSalud.nombre}</td>
                        <td>{centroSalud.municipio}</td>
                        <td>
                            <button class="btn btn-primary" id={centroSalud.nombre} style={{ marginRight: 10 }}>Modificar usuario</button>
                            <button class="btn btn-danger" id={centroSalud.nombre}>Eliminar usuario</button>
                        </td>
                    </tr>
                )}
            </table>
        );
    }
}

export default centroSaludList




