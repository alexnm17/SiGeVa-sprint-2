import React from "react";
import { Breadcrumb, Button } from "react-bootstrap"
import CentroSaludList from "./CentroSaludList";
import 'bootstrap/dist/css/bootstrap.min.css'

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
                    <button class="btn btn-success" style={{ marginRight: 15 }} >Crear centro de salud</button>
                </a>
                <CentroSaludList />
            </div>
        </div>
    );
}

export default GestionCentroSalud