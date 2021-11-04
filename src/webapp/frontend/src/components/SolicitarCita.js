import React, { Component } from "react";
import { Breadcrumb, Form, Container, Row, Col, Button } from "react-bootstrap"
import axios from "axios";

class SolicitarCita extends Component {
    constructor(props) {
        super(props)

        this.state = {
            dni: '',
            msg: "",
        }
    }

    changeHandler = e => {
        this.setState({ [e.target.name]: e.target.value })
    }

    submitHandler = e => {
        e.preventDefault()
        console.log(this.state)
        axios.post("http://localhost:8080/solicitarCita", this.state)
            .then(res => {
                if (res.status === 200) {
                    window.location = "/paciente"
                    alert("Cita creada con éxito")
                }
                console.log(res);
                console.log(res.data);
            }).catch(error => {
                alert("No se ha podido crear la cita");
            })
    }

    render() {
        const { dni, msg } = this.state
        return (
            <div>
                <Breadcrumb style={{ margin: 30 }}>
                    <Breadcrumb.Item href="/">SiGeVa</Breadcrumb.Item>
                    <Breadcrumb.Item href="/Paciente">Paciente</Breadcrumb.Item>
                    <Breadcrumb.Item href="/paciente/SolicitarCita">Solicitar Cita</Breadcrumb.Item>
                </Breadcrumb>
                <div style={{ marginBotton: 20 }}>
                    <h5>Estas en la página de Solicitud de Citas</h5>
                    <h6>Añade tu dni y pulsa en "Solicitar Cita" para pedir cita</h6>
                </div>
                <div>
                    <Container>
                        <Form onSubmit={this.submitHandler}>
                            <Row>
                            <Col></Col>
                                <Col>
                                    <Form.Label>DNI</Form.Label>
                                    <Form.Control type='text' name="dni" placeholder="12345678A" onChange={this.changeHandler} value={dni}></Form.Control>
                                </Col>
                                <Col></Col>
                            </Row>
                            <Button type="submit" style={{ marginTop: 15 }}>Solicitar Cita</Button>
                        </Form>
                        <p>{msg}</p>
                    </Container>
                </div>
            </div>
        );
    }
}

export default SolicitarCita