import React, { Component } from "react"
import { Breadcrumb } from "react-bootstrap"
import UsuariosList from "./UsuariosList"
import { Button, Modal, ModalBody, FormGroup, ModalFooter, ModalHeader } from 'reactstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import axios from 'axios'

class GestionUsuarios extends Component {
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

    changeOnlyStringHandler = e => {
        if(e.target.value.match("^[a-zA-Z ]*$") != null){
            this.setState({
                form: {
                    ...this.state.form,
                    [e.target.name]: e.target.value,
                }
            });
        }
    }

    changeDNIHandler = e => {
        if(e.target.value.match("^[0-9]{0,8}[A-Za-z]{0,1}$") != null){
            this.setState({
                form: {
                    ...this.state.form,
                    [e.target.name]: e.target.value,
                }
            });
        }
    }

    changeEmailHandler = e => {
        if(e.target.value.match("^[a-zA-Z0-9_.-]*[@]{0,1}[a-zA-z0-9]*[.]{0,1}[a-zA-Z]{0,4}$") != null){
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
        axios.post("http://localhost:8080/crearUsuario", this.state.form)
            .then(res => {
                if (res.status === 200) {
                    alert("Usuario creado con éxito")
                    this.ocultarModalCrear()
                    window.location.reload(true);
                }
            }).catch(error => {
                if (error.response.status === 400) {
                    alert("Alguno de los campos introducidos es erroneo. Compruebalos y prueba otra vez");
                } else {
                    alert("Error desconocido, por favor contacta con el administrador.")
                }
            })
    }

    render() {
        const { dni, nombre, apellido, centroSalud, rol, email, password } = this.state.form
        if (localStorage.getItem('rolUsuario') === "Administrador") {
            return (
                <div>
                    <Breadcrumb style={{ margin: 30 }}>
                        <Breadcrumb.Item href="/">SiGeVa</Breadcrumb.Item>
                        <Breadcrumb.Item href="/Administrador">Administrador</Breadcrumb.Item>
                        <Breadcrumb.Item href="/Administrador/GestionUsuarios">Gestión de Usuarios</Breadcrumb.Item>
                    </Breadcrumb>
                    <div>
                        <p>Selecciona la acción que quieres realizar: </p>
                        <Button color="success" onClick={() => this.mostrarModalCrear()}>Crear usuario</Button>
                        <UsuariosList />
                    </div>
                    <Modal isOpen={this.state.modalCrear}>
                        <ModalHeader>
                            <div><h3>Crear usuario</h3></div>
                        </ModalHeader>
                        <ModalBody>
                            <FormGroup>
                                <label>Email:</label>
                                <input className="form-control" type="text" name="email" onChange={this.changeEmailHandler} value={email}></input>
                            </FormGroup>
                            <FormGroup>
                                <label>Contraseña:</label>
                                <input className="form-control" type="password" name="password" onChange={this.changeHandler} value={password}></input>
                            </FormGroup>
                            <FormGroup>
                                <label>Dni:</label>
                                <input className="form-control" type="text" name="dni" onChange={this.changeDNIHandler} value={dni}></input>
                            </FormGroup>
                            <FormGroup>
                                <label>Nombre:</label>
                                <input className="form-control" type="text" name="nombre" onChange={this.changeOnlyStringHandler} value={nombre}></input>
                            </FormGroup>
                            <FormGroup>
                                <label>Apellidos</label>
                                <input className="form-control" type="text" name="apellido" onChange={this.changeOnlyStringHandler} value={apellido}></input>
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
        } else {
            <div>
                <Breadcrumb style={{ margin: 30 }}>
                    <Breadcrumb.Item href="/">SiGeVa</Breadcrumb.Item>
                    <Breadcrumb.Item href="/Administrador">Administrador</Breadcrumb.Item>
                </Breadcrumb>
                <p>A esta sección solo pueden acceder los Administradores.</p>
                <p>Inicie sesión como administrador para continuar.</p>
            </div>
        }
    }
}

export default GestionUsuarios