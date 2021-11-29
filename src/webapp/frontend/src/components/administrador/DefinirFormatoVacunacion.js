import React, { Component } from "react";
import { Breadcrumb, Container, Col, Row, Form, Button } from "react-bootstrap"
import axios from "axios";

class GestionCentroSalud extends Component {
    constructor(props) {
        super(props)

        this.state = {
            horaInicio: '',
            horaFin: '',
            duracionFranja: '',
            personasAVacunar: ''
        }
    }

    changeHandler = e => {
        this.setState({ [e.target.name]: e.target.value })
    }

    submitHandler = e => {
        e.preventDefault()
        console.log(this.state)
        axios.post("https://sigeva-grupo6.herokuapp.com/definirFormatoVacunacion", this.state)
            .then(res => {
                alert("Formato de Vacunacion definido con éxito")
                document.getElementById("txtHoraIni").disabled=true;
                document.getElementById("txtFinVac").disabled=true;
                document.getElementById("txtDurFranja").disabled=true;
                document.getElementById("txtNumPersonas").disabled=true;

            }).catch(error => {
                if (error.response.status === 409) {
                    alert("Las horas del formato no son correctas");
                } else {
                    alert("Error desconocido, por favor contacta con el administrador.")
                }
            })
    }

    render() {
        const { horaInicio, horaFin, duracionFranja, personasAVacunar } = this.state
        return (
            <div>
			<Breadcrumb style={{margin:30}}>
                    <Breadcrumb.Item href="/">SiGeVa</Breadcrumb.Item>
                    <Breadcrumb.Item href="/Administrador">Administrador</Breadcrumb.Item>
                    <Breadcrumb.Item href="/Administrador/GestionDefinicionDeCitas">Gestión de Definición De Citas</Breadcrumb.Item>
                    <Breadcrumb.Item href="Administrador/GestionDefinicionDeCitas/DefinirFormatoVacunacion">Definir formato de Vacunacion</Breadcrumb.Item>
                </Breadcrumb>
                <div style={{ marginBotton: 20 }}>
                    <h5>Estas en la página de la Definición del Formato de Citas</h5>
                    <h6>Rellene los campos y pulsa en el botón de "Crear Formato Vacunacion" para definir el formato de vacunacion que tendrán todos los centros</h6>
                </div>
                <div>
                    <Container>
                        <Form onSubmit={this.submitHandler}>
                            <Row>
                                <Col>
                                    <Form.Label>Hora de Inicio de Vacunacion</Form.Label>
                                    <Form.Control id="txtHoraIni" type='text' name="horaInicio" placeholder="08:00" onChange={this.changeHandler} value={horaInicio} ></Form.Control>
                                </Col>
                                <Col>
                                    <Form.Label>Hora de Fin de Vacunacion</Form.Label>
                                     <Form.Control id="txtFinVac" type='text' name="horaFin" placeholder="10:00" onChange={this.changeHandler} value={horaFin} ></Form.Control>
                                </Col>
                            </Row>
                            <Row>
                                <Col>
                                    <Form.Label>Duracion de cada franja (en minutos)</Form.Label>
                                    <Form.Control id="txtDurFranja" type='text' name="duracionFranja" placeholder="30" onChange={this.changeHandler} value={duracionFranja} ></Form.Control>
                                </Col>
                                <Col>
                                    <Form.Label>Número de personas a vacunar</Form.Label>
                                    <Form.Control id="txtNumPersonas" type='text' name="personasAVacunar" placeholder="5" onChange={this.changeHandler} value={personasAVacunar} ></Form.Control>
                                </Col>
                            </Row>
                            <Row style={{ marginTop: 15 }}>
                                <Button type="submit">Crear Formato Vacunacion</Button>
                            </Row>
                        </Form>
                    </Container>
                </div>
            </div>
        );
    }
}

export default GestionCentroSalud