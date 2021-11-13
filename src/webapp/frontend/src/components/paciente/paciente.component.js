import React, { Component } from "react";
import { Breadcrumb } from "react-bootstrap"
import { Button, Modal, ModalBody, FormGroup, ModalFooter, ModalHeader } from "reactstrap"
import axios from "axios"
import 'bootstrap/dist/css/bootstrap.min.css'

class Paciente extends Component {
    state = {
        citaUsuario: [],
        email: "",
        modalSolicitar: false

    }

    componentDidMount() {
        this.getCitaPaciente()
    }

    getCitaPaciente() {
        axios.get('http://localhost:8080/getCitaByEmail', { params: { email: localStorage.getItem("emailUsuario") } })
            .then(res => {
                this.setState({ citaUsuario: res.data })
            })
    }

    SolicitarClickHandler = () => {
        axios.post("http://localhost:8080/solicitarCita", { email: localStorage.getItem("emailUsuario") })
            .then(res => {
                this.ocultarModalSolicitar()
                window.location.reload(true);
            }).catch(error => {
                this.ocultarModalSolicitar()
                alert("No se ha podido crear la cita");
            })
    }

    anularHandler = e => {
        axios.delete('http://localhost:8080/anularCita', { data: { idCita: e.target.id } })
            .then(res => {
                window.location.reload(true);
            })
    }

    mostrarModalSolicitar = () => {
        this.setState({ modalSolicitar: true })
    }

    ocultarModalSolicitar = () => {
        this.setState({ modalSolicitar: false })
    }

    render() {
        if (localStorage.getItem('rolUsuario') === "Paciente") {
            return (
                <div>
                    <Breadcrumb style={{ margin: 30 }}>
                        <Breadcrumb.Item href="/">SiGeVa</Breadcrumb.Item>
                        <Breadcrumb.Item href="/Paciente">Paciente</Breadcrumb.Item>
                    </Breadcrumb>
                    <div>
                        <p>Selecciona la acción que quieres realizar: </p>
                        <Button color="primary" onClick={this.mostrarModalSolicitar} style={{ marginRight: 15, marginBottom: 20 }}>Solicitar cita</Button>
                    </div>
                    <div>
                        <h5>Tus citas</h5>
                        <table className="table">
                            <thead>
                                <tr>
                                    <th>Día</th>
                                    <th>Hora</th>
                                    <th>Centro Vacunacion</th>
                                    <th>Municipio</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                {this.state.citaUsuario.map(cita =>
                                    <tr style={{ marginBottom: 15 }}>
                                        <td>{cita.fecha}</td>
                                        <td>{cita.hora}</td>
                                        <td>{cita.centroVacunacion.nombre}</td>
                                        <td>{cita.centroVacunacion.municipio} </td>
                                        <td>
                                            <Button color="primary" style={{ marginRight: 15 }}>Modificar</Button>
                                            <Button color="danger" id={cita.idCita} onClick={this.anularHandler}>Anular</Button>
                                        </td>
                                    </tr>
                                )
                                }
                            </tbody>
                        </table>
                    </div>

                    {/* Modal para confirmar solicitud de cita */}
                    <Modal isOpen={this.state.modalSolicitar}>
                        <ModalHeader>
                            <div><h3>Confirmación de Solicitud</h3></div>
                        </ModalHeader>
                        <ModalBody>
                            <FormGroup>
                                <label style={{ marginRight: 15 }}>Para solicitar sus citas de vacunación pulse "Solicitar"</label>
                            </FormGroup>
                        </ModalBody>
                        <ModalFooter>
                            <Button color="success" onClick={this.SolicitarClickHandler}>Solicitar</Button>
                            <Button color="danger" onClick={this.ocultarModalSolicitar}>Salir</Button>
                        </ModalFooter>
                    </Modal>
                </div>
            );
        } else {
            return (
                <div>
                    <Breadcrumb style={{ margin: 30 }}>
                        <Breadcrumb.Item href="/">SiGeVa</Breadcrumb.Item>
                        <Breadcrumb.Item href="/Paciente">Paciente</Breadcrumb.Item>
                    </Breadcrumb>
                    <p>A esta sección solo pueden acceder los Pacientes.</p>
                    <p>Inicie sesión como paciente o contacte con un administrador.</p>
                </div>
            );
        }
    }
}

export default Paciente