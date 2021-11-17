import React, { Component } from "react"
import { Breadcrumb } from "react-bootstrap"
import CentroSaludList from "./CentroSaludList";
import 'bootstrap/dist/css/bootstrap.min.css'
import { Button, Modal, ModalBody, FormGroup, ModalFooter, ModalHeader } from 'reactstrap'
import axios from 'axios'

class GestionCentroSalud extends Component {
    state = {
        form: {
            nombre: "",
            municipio: "",
            dosis: ""
        },
        modalCrear: false
    }

    mostrarModalCrear = () => {
        this.setState({ modalCrear: true })
    }

    ocultarModalCrear = () => {
        this.setState({ modalCrear: false })
    }

    changeHandler = async e => {
        await this.setState({
            form: {
                ...this.state.form,
                [e.target.name]: e.target.value
            }
        })
        console.log(this.state.form)
    }

    changeOnlyStringHandler = e => {
        if (e.target.value.match("^[a-zA-Z ]*$") != null) {
            this.setState({
                form: {
                    ...this.state.form,
                    [e.target.name]: e.target.value,
                }
            });
        }
    }
    
    changeOnlyNumberHandler = e => {
        if (e.target.value.match("^[0-9]*$") != null) {
            this.setState({
                form: {
                    ...this.state.form,
                    [e.target.name]: e.target.value,
                }
            });
        }
    }

    changeOnlyStringAndNumberHandler = e => {
        if (e.target.value.match("^[A-Za-z0-9 ]*$") != null) {
            this.setState({
                form: {
                    ...this.state.form,
                    [e.target.name]: e.target.value,
                }
            });
        }
    }

    submitHandler = e => {
        e.preventDefault()
        axios.post("http://localhost:8080/addCentro", this.state.form)
            .then(res => {
                if (res.status === 200) {
                    alert("Centro creado con éxito")
                    this.ocultarModalCrear()
                    window.location.reload(true);
                }
            }).catch(error => {
                alert("No se ha podido crear el usuario");
            })
    }

    render() {
        const { nombre, municipio, dosis } = this.state.form
        if (localStorage.getItem('rolUsuario') === "Administrador") {
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
                        <button className="btn btn-success" style={{ marginRight: 15 }} onClick={() => this.mostrarModalCrear()}>Crear centro de salud</button>
                        <CentroSaludList />
                    </div>

                    <Modal isOpen={this.state.modalCrear}>
                        <ModalHeader>
                            <div><h3>Crear Centro de Salud</h3></div>
                        </ModalHeader>
                        <ModalBody>
                            <FormGroup>
                                <label>Nombre:</label>
                                <input className="form-control" type="text" name="nombre" onChange={this.changeOnlyStringAndNumberHandler} value={nombre}></input>
                            </FormGroup>
                            <FormGroup>
                                <label>Municipio:</label>
                                <input className="form-control" type="text" name="municipio" onChange={this.changeOnlyStringHandler} value={municipio}></input>
                            </FormGroup>
                            <FormGroup>
                                <label>Dosis:</label>
                                <input className="form-control" type="text" name="dosis" onChange={this.changeOnlyNumberHandler} value={dosis}></input>
                            </FormGroup>
                        </ModalBody>

                        <ModalFooter>
                            <Button color="primary" onClick={this.submitHandler}>Aceptar</Button>
                            <Button color="danger" onClick={() => this.ocultarModalCrear()}>Cancelar</Button>
                        </ModalFooter>
                    </Modal>
                </div>
            );
        } else {
            return (
                <div>
                    <Breadcrumb style={{ margin: 30 }}>
                        <Breadcrumb.Item href="/">SiGeVa</Breadcrumb.Item>
                        <Breadcrumb.Item href="/Administrador">Administrador</Breadcrumb.Item>
                    </Breadcrumb>
                    <p>A esta sección solo pueden acceder los Administradores.</p>
                    <p>Inicie sesión como administrador para continuar.</p>
                </div>
            );
        }

    }
}

export default GestionCentroSalud