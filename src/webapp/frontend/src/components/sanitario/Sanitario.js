import React, { Component } from "react";
import { Breadcrumb, Button } from "react-bootstrap"
import 'bootstrap/dist/css/bootstrap.min.css'
import ListUsuariosAVacunarHoy from "./listUsuariosAVacunarHoy";
import { Modal, ModalBody, FormGroup, ModalFooter, ModalHeader } from 'reactstrap'
import axios from 'axios'

class Sanitario extends Component {
    state = {
        modalModificar: false,
        citasFechaOtroDia: []
    }

    mostrarModalConsultarFecha = (usuario) => {
        this.setState({ modalModificar: true})
    }

    ocultarModalConsultarFecha = e => {
        e.preventDefault()
        this.setState({ modalModificar: false })
        this.setState({ citasFechaOtroDia: [] })
    }

    changeHandler = e => {
        e.preventDefault()
        this.getCitasDiaSeleccionado(e.target.value)
    }

    getCitasDiaSeleccionado(fecha) {
        axios.post('http://localhost:8080/getCitasOtroDia', { "emailUsuario": localStorage.getItem("emailUsuario"), "fecha" : fecha }
        ).then(res => {
            this.setState({ citasFechaOtroDia: res.data })
        })
    }

    render() {
        return (
            <div>
                <Breadcrumb style={{ margin: 30 }}>
                    <Breadcrumb.Item href="/">SiGeVa</Breadcrumb.Item>
                    <Breadcrumb.Item href="/Sanitario">Sanitario</Breadcrumb.Item>
                </Breadcrumb>
                <div>
                    <h6>Selecciona una fecha para ver la lista de vacunacion</h6>
                    {/* <input type="date" onChange={this.changeHandler} name="fechaSeleccionada" value={fechaSeleccionada}></input> */}
                    <Button onClick={() => this.mostrarModalConsultarFecha()}>Ver lista de otro día</Button>
                </div>
                <ListUsuariosAVacunarHoy />

                <Modal size="lg" style={{maxWidth: '700px', width: '100%'}} isOpen={this.state.modalModificar}>
                    <ModalHeader>
                        <div><h3>Ver lista de otro día</h3></div>
                    </ModalHeader>
                    <ModalBody>
                        <FormGroup>
                            <label style={{ marginRight: 15 }}>Fecha seleccionada: </label>
                            <input className="form-control" type="date" name="fechaOtroDia" onChange={this.changeHandler} value={this.state.fechaOtroDia}></input>
                        </FormGroup>
                        <div style={{ width: 120 }}>
                            <table class="table" style={{ marginTop: 15, marginLeft: 15 }}>
                                <tr>
                                    <th>Hora de Vacunación</th>
                                    <th>Nombre</th>
                                    <th>Apellido</th>
                                    <th>DNI</th>
                                    <th>Estado de Vacunación</th>
                                </tr>
                                {this.state.citasFechaOtroDia.map(cita =>
                                    <tr key={cita.dni}>
                                        <td>{cita.hora}</td>
                                        <td>{cita.usuario.nombre}</td>
                                        <td>{cita.usuario.apellido}</td>
                                        <td>{cita.usuario.dni}</td>
                                        <td>{cita.usuario.estadoVacunacion}</td>
                                    </tr>
                                )}

                            </table>
                        </div>
                    </ModalBody>
                    <ModalFooter>
                        <Button color="danger" onClick={this.ocultarModalConsultarFecha}>Salir</Button>
                    </ModalFooter>
                </Modal>
            </div>
        );
    }
}

export default Sanitario