import axios from 'axios';
import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css'


class listUsuariosAVacunarHoy extends Component {
    state = {
        citas: [],
        fecha: ""
    }

    componentDidMount() {
        this.getCitasHoy()
    }

    getCitasHoy() {
        axios.post('http://localhost:8080/getCitasHoy', { "emailUsuario": localStorage.getItem("emailUsuario") }
        ).then(res => {
            this.setState({ citas: res.data })
        })
    }

    VacunarClickHandler = e => {
        e.preventDefault()
        console.log(e.target.id)
    }

    render() {
        return (
            <table class="table" style={{ marginTop: 15, marginLeft: 15 }}>
                <tr>
                    <th>Hora de Vacunación</th>
                    <th>Nombre</th>
                    <th>Apellido</th>
                    <th>DNI</th>
                    <th>Estado de Vacunación</th>
                    <th>Acciones</th>
                </tr>
                {this.state.citas.map(cita =>
                    <tr key={cita.dni}>
                        <td>{cita.hora}</td>
                        <td>{cita.usuario.nombre}</td>
                        <td>{cita.usuario.apellido}</td>
                        <td>{cita.usuario.dni}</td>
                        <td>{cita.usuario.estadoVacunacion}</td>
                        <td>
                            <button class="btn btn-primary" id={cita.usuario.email} onClick={this.VacunarClickHandler} style={{ marginRight: 10 }}>Vacunar</button>
                        </td>
                    </tr>
                )}

            </table>
        );
    }
}

export default listUsuariosAVacunarHoy
