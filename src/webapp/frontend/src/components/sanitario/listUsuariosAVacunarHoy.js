import axios from 'axios';
import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css'
import { Modal, ModalBody, FormGroup, ModalFooter, ModalHeader, Button } from 'reactstrap'

class listUsuariosAVacunarHoy extends Component {
    state = {
        citas: [],
        fecha: "",
        modalVacunar: false,
        usuarioAVacunar : ""
    }

    componentDidMount() {
        this.getCitasHoy()
    }

    getCitasHoy() {
        axios.post('https://sigeva-grupo6.herokuapp.com/getCitasHoy', { "emailUsuario": localStorage.getItem("emailUsuario") }
        ).then(res => {
            this.setState({ citas: res.data })
        })
    }

    VacunarClickHandler = e => {
        e.preventDefault()
        console.log(this.state.usuarioAVacunar)
        axios.post('https://sigeva-grupo6.herokuapp.com/marcarVacunado', { "email":  this.state.usuarioAVacunar }
        ).then(res => {
            this.ocultarModalVacunar()
            window.location.reload(true);
        }).catch(error => {
            if (error.response.status === 409) {
                alert("El usuario ya ha sido vacunado hoy");
            } else {
                alert("Error desconocido, por favor contacta con el administrador.")
            }
            this.ocultarModalVacunar()
        })
    }

    mostrarModalVacunar = e => {
        e.preventDefault()
        this.setState({ modalVacunar: true })
        this.setState({ usuarioAVacunar : e.target.id})
    }

    ocultarModalVacunar = () => {
        this.setState({ modalVacunar: false })
    }

    render() {
        return (
            <div>
                <table className="table" style={{ marginTop: 15, marginLeft: 15 }}>
                <thead>
                    <tr>
                        <th>Hora de Vacunación</th>
                        <th>Nombre</th>
                        <th>Apellido</th>
                        <th>DNI</th>
                        <th>Estado de Vacunación</th>
                        <th>Acciones</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.state.citas.map(cita =>
                        <tr key={cita.idCita}>
                            <td>{cita.hora}</td>
                            <td>{cita.usuario.nombre}</td>
                            <td>{cita.usuario.apellido}</td>
                            <td>{cita.usuario.dni}</td>
                            <td>{cita.usuario.estadoVacunacion}</td>
                            <td>
                                <button className="btn btn-primary" id={cita.usuario.email} onClick={this.mostrarModalVacunar} style={{ marginRight: 10 }}>Vacunar</button>
                            </td>
                        </tr>
                    )}
                    </tbody>
                </table>

                <Modal isOpen={this.state.modalVacunar}>
                    <ModalHeader>
                        <div><h3>Confirmación de Vacunación</h3></div>
                    </ModalHeader>
                    <ModalBody>
                        <FormGroup>
                            <label style={{ marginRight: 15 }}>¿Seguro que quieres vacunar a este paciente? </label>
                        </FormGroup>
                    </ModalBody>
                    <ModalFooter>
                        <Button color="success" onClick={this.VacunarClickHandler}>Sí, vacunar</Button>
                        <Button color="danger" onClick={this.ocultarModalVacunar}>Salir</Button>
                    </ModalFooter>
                </Modal>
            </div>
        );
    }
}

export default listUsuariosAVacunarHoy
