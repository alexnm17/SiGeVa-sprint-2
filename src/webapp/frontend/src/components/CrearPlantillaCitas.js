import React, { Component } from "react";
import { Breadcrumb, Button } from "react-bootstrap"
import axios from "axios";

class GestionCentroSalud extends Component {
    submitHandler = e => {
        e.preventDefault()
        alert("Espera mientras se crean las citas, el tiempo que esto tarda dependerá de las citas que se creen")
        axios.post("http://localhost:8080/crearPlantillasCitaVacunacion")
            .then(res => {
                window.location = "/administrador/GestionDefinicionDeCitas"
                alert("Plantilla de citas generada con éxito")
                console.log(res);
                console.log(res.data);
            }).catch(error => {
                alert("No se pudo generar la plantilla de citas")
            })
    }
    render() {
        return (
            <div>
			<Breadcrumb style={{margin:30}}>
                    <Breadcrumb.Item href="/">SiGeVa</Breadcrumb.Item>
                    <Breadcrumb.Item href="/Administrador">Administrador</Breadcrumb.Item>
                    <Breadcrumb.Item href="/Administrador/GestionDefinicionDeCitas">Gestión de Definición De Citas</Breadcrumb.Item>
                    <Breadcrumb.Item href="/Administrador/GestionDefinicionDeCitas/CrearPlantillaCitas">Crear plantilla de citas</Breadcrumb.Item>
                </Breadcrumb>
                <div style={{ marginBotton: 20 }}>
                    <h5>Estas en la página de Gestión de creacion de Plantilla de Citas</h5>
                    <h6>Dándole al botón "Crear plantilla de Citas" creará las citas disponibles para este año</h6>
                    <Button onClick={this.submitHandler}>Crear plantilla de Citas</Button>
                </div>
            </div>
        );
    }
}

export default GestionCentroSalud