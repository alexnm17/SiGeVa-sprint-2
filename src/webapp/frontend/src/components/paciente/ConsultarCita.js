 import React from "react";
import { Breadcrumb } from "react-bootstrap"

const GestionCentroSalud = () => {
    return (
        <div>
			<Breadcrumb style={{margin:30}}>
                <Breadcrumb.Item href="/">SiGeVa</Breadcrumb.Item>
                <Breadcrumb.Item href="/Paciente">Paciente</Breadcrumb.Item>
                <Breadcrumb.Item href="/Paciente/ConsultarCita">Consultar cita</Breadcrumb.Item>
            </Breadcrumb>
            <div style={{ marginBotton: 20 }}>
                <h5>Estas en la página de consulta de Citas</h5>
            </div>
            <div>
                <p>Selecciona la acción que quieres realizar: </p>
            </div>
        </div>
    );
}

export default GestionCentroSalud