import React, { Component } from "react";
import { Breadcrumb, Button, Container, Row, Col, Form } from "react-bootstrap"
import axios from "axios";

class CrearCentroSalud extends Component {
    constructor(props) {
        super(props)

        this.state = {
            nombre: '',
            municipio: ''
        }
    }

    changeHandler = e => {
        this.setState({ [e.target.name]: e.target.value })
    }

    submitHandler = e => {
        e.preventDefault()
        console.log(this.state)
        axios.post("http://localhost:8080/addCentro", this.state)
            .then(res => {
                window.location = "/Administrador/GestionCentrosSalud"
                alert("Centro de salud creado con éxito")
                console.log(res);
                console.log(res.data);
            }).catch(error=>{
                alert("No se ha podido crear el Centro de Salud");
            })
    }


    render() {
        const { nombre, municipio } = this.state
        return (
            <div>
			<Breadcrumb style={{margin:30}}>
                    <Breadcrumb.Item href="/">SiGeVa</Breadcrumb.Item>
                    <Breadcrumb.Item href="/Administrador">Administrador</Breadcrumb.Item>
                    <Breadcrumb.Item href="/Administrador/GestionCentrosSalud">Gestion de Centros de Salud</Breadcrumb.Item>
                    <Breadcrumb.Item href="/Administrador/GestionCentrosSalud/CrearCentroSalud">Crear Centro de Salud</Breadcrumb.Item>
                </Breadcrumb>
                <div style={{ marginBotton: 20 }}>
                    <h5>Estas en la página de creación de centros de salud</h5>
                    <h6>Rellene los campos y pulsa en el botón de "Crear Centro de Salud" para crear un centro de salud</h6>
                </div>
                <div>
                    <Container>
                        <Form onSubmit={this.submitHandler}>
                            <Row>
                                <Col>
                                    <Form.Label>Nombre</Form.Label>
                                    <Form.Control type="text" name="nombre" placeholder="El bombo" onChange={this.changeHandler} value={nombre}></Form.Control>
                                </Col>
                                <Col>
                                    <Form.Label>Municipio</Form.Label>
                                    <Form.Control type="text" name="municipio" placeholder="Tomelloso" onChange={this.changeHandler} value={municipio}></Form.Control>
                                </Col>
                            </Row>
                            <Row style={{ marginTop: 15 }}>
                                <Button type="submit">Crear Centro de Salud</Button>
                            </Row>
                        </Form>
                    </Container>
                </div>
            </div>
        );
    }

}

export default CrearCentroSalud