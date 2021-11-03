import React from "react";
import { Button, Breadcrumb } from "react-bootstrap"

const Paciente = () => {
    return (
        <div>
            <Breadcrumb style={{ margin: 30 }}>
                <Breadcrumb.Item href="/">SiGeVa</Breadcrumb.Item>
                <Breadcrumb.Item href="/Paciente">Paciente</Breadcrumb.Item>
            </Breadcrumb>
            <div style={{ marginBotton: 20 }}>
            </div>
            <div>
                <p>Selecciona la acción que quieres realizar: </p>
                <a href="/paciente/SolicitarCita">
                    <Button style={{ marginRight: 15 }}>Solicitar cita</Button>
                </a>
                <Button variant="secondary" style={{ marginRight: 15 }}>Consultar cita</Button>
            </div>
        </div>
    );
}

export default Paciente