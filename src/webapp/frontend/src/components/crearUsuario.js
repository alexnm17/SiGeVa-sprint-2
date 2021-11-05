import React, { Component } from "react";
import { Breadcrumb, Button, Col, Container, Dropdown, Form, Row } from "react-bootstrap"
import 'bootstrap/dist/css/bootstrap.min.css'
import axios from "axios";
import DropdownCentros from "./DropdownCentros";

class GestionCentroSalud extends Component {
    constructor(props) {
        super(props)

        this.state = {
            nombre: '',
            apellido: '',
            dni: '',
            password: '',
            centroSalud: '',
            rol: ''
        }
    }

    changeHandler = e => {
        this.setState({ [e.target.name]: e.target.value })
    }

    submitHandler = e => {
        e.preventDefault()
        console.log(this.state)
        axios.post("http://localhost:8080/crearUsuario", this.state)
            .then(res => {
                if (res.status === 200) {
                    window.location = "/Administrador/GestionUsuarios"
                    alert("Usuario creado con éxito")
                }
                console.log(res);
                console.log(res.data);
            }).catch(error => {
                alert("No se ha podido crear el usuario");
            })
    }


    render() {
        const { nombre, apellido, dni, password, centroSalud, rol } = this.state
        return (
            <div style={{ marginLeft: 15 }}>
                <Breadcrumb style={{ margin: 30 }}>
                    <Breadcrumb.Item href="/">SiGeVa</Breadcrumb.Item>
                    <Breadcrumb.Item href="/Administrador">Administrador</Breadcrumb.Item>
                    <Breadcrumb.Item href="/Administrador/GestionUsuarios">Gestion de Usuarios</Breadcrumb.Item>
                    <Breadcrumb.Item href="/Administrador/GestionUsuarios/CrearUsuario">Crear usuario</Breadcrumb.Item>
                </Breadcrumb>
                <div style={{ marginBotton: 20 }}>
                    <h5>Estas en la página de Creacion de Usuarios</h5>
                    <h6>Rellene los campos y pulsa en el botón de "Crear Usuario" para crear un usuario</h6>
                </div>
                <div >
                    <Container>
                        <Form onSubmit={this.submitHandler}>
                            <Row>
                                <Col>
                                    <Form.Label>Nombre</Form.Label>
                                    <Form.Control type='text' name="nombre" placeholder="José" onChange={this.changeHandler} value={nombre}></Form.Control>
                                </Col>
                                <Col>
                                    <Form.Label>Apellidos</Form.Label>
                                    <Form.Control type='text' name="apellido" placeholder="López Martínez" onChange={this.changeHandler} value={apellido}></Form.Control>
                                </Col>
                                <Col>
                                    <Form.Label>DNI</Form.Label>
                                    <Form.Control type='text' name="dni" placeholder="12345678A" onChange={this.changeHandler} value={dni}></Form.Control>
                                </Col>
                            </Row>
                            <Form.Label>Contraseña</Form.Label>
                            <Form.Control type="password" name="password" placeholder="Password" onChange={this.changeHandler} value={password}></Form.Control>
                            <Row>
                                <Col>
                                    <Form.Label>Centro de Salud: </Form.Label>
                                </Col>
                                <Col>
                                    <Form.Label>Rol</Form.Label>
                                </Col>
                            </Row>
                            <Row>
                                <Col>
                                    <DropdownCentros />
                                </Col>
                                <Col>
                                    <select onChange={this.changeHandler} value={rol}>
                                        <option>Paciente</option>
                                        <option >Administrador</option>
                                        <option >Sanitario</option>
                                    </select>
                                </Col>
                            </Row>
                            <Row style={{ marginTop: 15 }}>
                                <Col></Col>
                                <Col>
                                    <Button type="submit">Crear Usuario</Button>
                                </Col>
                                <Col></Col>
                            </Row>
                        </Form>
                    </Container>
                </div>
            </div>
        );
    }
}

export default GestionCentroSalud