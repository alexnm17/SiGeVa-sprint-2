import React from "react";
import { Breadcrumb, Button } from "react-bootstrap"

const GestionCentroSalud = () => {
    return (
        <div>
			<Breadcrumb style={{margin:30}}>
                <Breadcrumb.Item href="/">SiGeVa</Breadcrumb.Item>
                <Breadcrumb.Item href="/Administrador">Administrador</Breadcrumb.Item>
                <Breadcrumb.Item href="/Administrador/GestionCentrosSalud">Gestion de Centros de Salud</Breadcrumb.Item>
            </Breadcrumb>
            <div style={{ marginBotton: 20 }}>
                <h5>Estas en la página de Gestión de Centros de Salud</h5>
            </div>
            <div>
                <p>Selecciona la acción que quieres realizar: </p>
                <a href="/administrador/GestionCentrosSalud/CrearCentroSalud">
                    <Button style={{ marginRight: 15 }}>Crear centro de salud</Button>
                </a>
                <Button variant="secondary" style={{ marginRight: 15 }}>Modificar centro de salud</Button>
                <Button variant="secondary" style={{ marginRight: 15 }}>Borrar centro de salud</Button>
            </div>
        </div>
    );
}

export default GestionCentroSalud