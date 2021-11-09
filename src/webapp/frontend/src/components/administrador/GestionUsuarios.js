import React, { Component } from "react"
import { Breadcrumb } from "react-bootstrap"
import UsuariosList from "./UsuariosList"
import { Button, Modal, ModalBody, FormGroup, ModalFooter, ModalHeader } from 'reactstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import axios from 'axios'
// import DropdownCentros from "./DropdownCentros"


class GestionCentroSalud extends Component {
    state = {
        form: {
            dni: "",
            nombre: "",
            apellido: "",
            password: "",
            centroSalud: "",
            email: "",
            rol: ""
        },
        centroSalud: [],
        modalCrear: false,
    }

    componentDidMount() {
        this.getCentrosSalud()
    }

    mostrarModalCrear = () => {
        this.setState({ modalCrear: true })
    }

    ocultarModalCrear = () => {
        this.setState({ modalCrear: false })
    }

    getCentrosSalud() {
        axios.get('http://localhost:8080/getAllCentros')
            .then(res => {
                this.setState({ centroSalud: res.data })
            })
    }

    changeHandler = async e => {
        await this.setState({
            form: {
                ...this.state.form,
                [e.target.name]: e.target.value
            }
        })
    }

    submitHandler = e => {
        e.preventDefault()
        axios.post("http://localhost:8080/crearUsuario", this.state.form)
            .then(res => {
                if (res.status === 200) {
                    alert("Usuario creado con éxito")
                    this.ocultarModalCrear()
                    window.location.reload(true);
                }
            }).catch(error => {
                alert("No se ha podido crear el usuario");
            })
    }

    render() {
        const { dni, nombre, apellido, centroSalud, rol, email, password } = this.state.form
        return (
            <div>
                <Breadcrumb style={{ margin: 30 }}>
                    <Breadcrumb.Item href="/">SiGeVa</Breadcrumb.Item>
                    <Breadcrumb.Item href="/Administrador">Administrador</Breadcrumb.Item>
                    <Breadcrumb.Item href="/Administrador/GestionUsuarios">Gestion de Usuarios</Breadcrumb.Item>
                </Breadcrumb>
                <div style={{ marginBotton: 20 }}>
                    <h5>Estas en la página de Gestion de Usuarios</h5>
                </div>
                <div>
                    <p>Selecciona la acción que quieres realizar: </p>
                    {/* <a href="/administrador/GestionUsuarios/CrearUsuario"> */}
                    <Button color="success" onClick={() => this.mostrarModalCrear()}>Crear usuario</Button>
                    {/* </a> */}
                    <UsuariosList />
                </div>

                <Modal isOpen={this.state.modalCrear}>
                    <ModalHeader>
                        <div><h3>Crear usuario</h3></div>
                    </ModalHeader>
                    <ModalBody>
                        <FormGroup>
                            <label>Email:</label>
                            <input className="form-control" type="text" name="email" onChange={this.changeHandler} value={email}></input>
                        </FormGroup>
                        <FormGroup>
                            <label>Contraseña:</label>
                            <input className="form-control" type="password" name="password" onChange={this.changeHandler} value={password}></input>
                        </FormGroup>
                        <FormGroup>
                            <label>Dni:</label>
                            <input className="form-control" type="text" name="dni" onChange={this.changeHandler} value={dni}></input>
                        </FormGroup>
                        <FormGroup>
                            <label>Nombre:</label>
                            <input className="form-control" type="text" name="nombre" onChange={this.changeHandler} value={nombre}></input>
                        </FormGroup>
                        <FormGroup>
                            <label>Apellidos</label>
                            <input className="form-control" type="text" name="apellido" onChange={this.changeHandler} value={apellido}></input>
                        </FormGroup>
                        <FormGroup>
                            <label style={{ marginRight: 10 }}>Centro de Vacunacion:</label>
                            <select name="centroSalud" onChange={this.changeHandler} value={centroSalud}>
                                <option defaultValue>Selecciona un centro de salud...</option>
                                {this.state.centroSalud.map(centroSalud =>
                                    <option key={centroSalud.nombre}>{centroSalud.nombre}</option>
                                )}
                            </select>
                        </FormGroup>
                        <FormGroup>
                            <label style={{ marginRight: 10 }}>Rol:</label>
                            <select name="rol" onChange={this.changeHandler} value={rol}>
                                <option defaultValue>Selecciona un rol...</option>
                                <option>Paciente</option>
                                <option >Administrador</option>
                                <option >Sanitario</option>
                            </select>
                        </FormGroup>
                    </ModalBody>
                    <ModalFooter>
                        <Button color="primary" onClick={this.submitHandler}>Aceptar</Button>
                        <Button color="danger" onClick={() => this.ocultarModalCrear()}>Cancelar</Button>
                    </ModalFooter>
                </Modal>
            </div>
        );
    }

}

export default GestionCentroSalud