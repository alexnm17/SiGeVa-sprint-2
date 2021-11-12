import axios from 'axios';
import React, { Component } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css'
import { Button, Modal, ModalBody, FormGroup, ModalFooter, ModalHeader } from 'reactstrap'


class centroSaludList extends Component {
    state = {
        centroSalud: [],
        modalModificar: false,
        nombreCentro: ""
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

    EliminarClickHandler = e => {
        e.preventDefault()
        console.log(e.target.id)
        /*axios.get('http://localhost:8080/',e.target.id)
            .then(res => {
                this.setState({ usuarios: res.data })
            })*/
        this.getCentrosSalud()
    }

    mostrarModalModificar = (centro) => {
        this.setState({ modalModificar: true })
        this.setState({ nombreCentro: centro })
    }

    ocultarModalModificar = () => {
        this.setState({ modalModificar: false })
    }

    render() {
        return (
            <div>
                <table className="table" style={{ marginTop: 15, marginLeft: 15 }}>
                    <thead>
                        <tr>
                            <th>Nombre</th>
                            <th>Municipio</th>
                            <th>Dosis</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.centroSalud.map(centroSalud =>
                            <tr key={centroSalud.nombre}>
                                <td>{centroSalud.nombre}</td>
                                <td>{centroSalud.municipio}</td>
                                <td>{centroSalud.dosis}</td>
                                <td>
                                    <button className="btn btn-primary" id={centroSalud.nombre} style={{ marginRight: 10 }} onClick={() => this.mostrarModalModificar(centroSalud.nombre)}>Modificar centro</button>
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>

                <Modal isOpen={this.state.modalModificar}>
                    <ModalHeader>
                        <div><h3>Modificar centro de salud</h3></div>
                    </ModalHeader>

                    <ModalBody>
                        <FormGroup>
                            <label style={{ marginRight: 15 }}>Nombre:</label>
                            <label>{this.state.nombreCentro}</label>
                        </FormGroup>
                        <FormGroup>
                            <label>Municipio:</label>
                            <input className="form-control" type="text" name="centroVacunacion" onChange={this.handleChange}></input>
                        </FormGroup>
                        <FormGroup>
                            <label>Dosis</label>
                            <input className="form-control" type="text"></input>
                        </FormGroup>
                    </ModalBody>

                    <ModalFooter>
                        <Button color="primary">Aceptar</Button>
                        <Button color="danger" onClick={() => this.ocultarModalModificar()}>Cancelar</Button>
                    </ModalFooter>
                </Modal>
            </div>
        );
    }
}

export default centroSaludList




