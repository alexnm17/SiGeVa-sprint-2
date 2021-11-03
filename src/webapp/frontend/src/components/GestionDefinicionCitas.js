import React from "react";
import { Breadcrumb, Button } from "react-bootstrap"

const GestionCentroSalud = () => {
    return (
        <div>
			<Breadcrumb style={{margin:30}}>
                <Breadcrumb.Item href="/">SiGeVa</Breadcrumb.Item>
                <Breadcrumb.Item href="/Administrador">Administrador</Breadcrumb.Item>
                <Breadcrumb.Item href="/Administrador/GestionDefinicionDeCitas">Gestión de Definición De Citas</Breadcrumb.Item>
            </Breadcrumb>
            <div style={{ marginBotton: 20 }}>
                <h5>Estas en la página de Gestión de la Definición de Citas</h5>
            </div>
            <div>
                <p>Selecciona la acción que quieres realizar: </p>
                <a href="/Administrador/GestionDefinicionDeCitas/DefinirFormatoVacunacion">
                    <Button style={{ marginRight: 15 }}>Definir formato de vacunacion</Button>
                </a>
                <a href="/Administrador/GestionDefinicionDeCitas/CrearPlantillaCitas">
                    <Button style={{ marginRight: 15 }}>Crear plantilla de citas</Button>
                </a>
            </div>
        </div>
    );
}

export default GestionCentroSalud