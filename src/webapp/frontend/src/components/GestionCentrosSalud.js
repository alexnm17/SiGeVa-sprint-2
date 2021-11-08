import React, { Component } from "react"
import { Breadcrumb } from "react-bootstrap"
import CentroSaludList from "./CentroSaludList";
import 'bootstrap/dist/css/bootstrap.min.css'
import {Button, Modal, ModalBody, FormGroup, ModalFooter, ModalHeader } from 'reactstrap'


class GestionCentroSalud extends Component {
    state = {
        modalCrear: false,
    }

    mostrarModalCrear = () => {
        this.setState({ modalCrear: true })
    }

    ocultarModalCrear = () => {
        this.setState({ modalCrear: false })
    }

    render() {
        return (
            <div>
                <Breadcrumb style={{ margin: 30 }}>
                    <Breadcrumb.Item href="/">SiGeVa</Breadcrumb.Item>
                    <Breadcrumb.Item href="/Administrador">Administrador</Breadcrumb.Item>
                    <Breadcrumb.Item href="/Administrador/GestionCentrosSalud">Gestion de Centros de Salud</Breadcrumb.Item>
                </Breadcrumb>
                <div style={{ marginBotton: 20 }}>
                    <h5>Estas en la página de Gestión de Centros de Salud</h5>
                </div>
                <div>
                    <p>Selecciona la acción que quieres realizar: </p>
                    {/* <a href="/administrador/GestionCentrosSalud/CrearCentroSalud"> */}
                        <button class="btn btn-success" style={{ marginRight: 15 }} onClick={() => this.mostrarModalCrear()}>Crear centro de salud</button>
                    {/* </a> */}
                    <CentroSaludList />
                </div>

                <Modal isOpen={this.state.modalCrear}>
                    <ModalHeader>
                        <div><h3>Crear Centro de Salud</h3></div>
                    </ModalHeader>
                    <ModalBody>
                        <FormGroup>
                            <label>Nombre:</label>
                            <input className="form-control" type="text"></input>
                        </FormGroup>
                        <FormGroup>
                            <label>Municipio:</label>
                            <input className="form-control" type="text" name="nombre" onChange={this.handleChange}></input>
                        </FormGroup>
                        <FormGroup>
                            <label>Dosis:</label>
                            <input className="form-control" type="text" name="apellidos" onChange={this.handleChange}></input>
                        </FormGroup>
                    </ModalBody>

                    <ModalFooter>
                        <Button color="primary">Aceptar</Button>
                        <Button color="danger" onClick={() => this.ocultarModalCrear()}>Cancelar</Button>
                    </ModalFooter>
                </Modal>
            </div>
        );
    }

}

export default GestionCentroSalud