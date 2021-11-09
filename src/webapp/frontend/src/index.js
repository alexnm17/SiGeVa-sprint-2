import React from 'react'
import ReactDOM from 'react-dom'
import './index.css'
import reportWebVitals from './reportWebVitals'
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import App from './App'
import Administrador from './components/administrador.component'
import Paciente from './components/paciente.component'
import GestionUsuarios from './components/GestionUsuarios'
import SolicitarCita from './components/SolicitarCita'
import GestionCentroSalud from './components/GestionCentrosSalud'
import GestionDefinicionCitas from './components/GestionDefinicionCitas'
import CrearUsuario from './components/crearUsuario'
import CrearCentroSalud from './components/crearCentroSalud'
import CrearPlantillaCitas from './components/CrearPlantillaCitas'
import DefinirFormatoVacunacion from './components/DefinirFormatoVacunacion'
import Sanitario from './components/Sanitario'
import Login from './components/login'

function Routing() {
  return (
    <div align="center">
      <Router>
        <h1>SiGeVa</h1>
        <Switch>
          <Route exact path="/" component={Login} />
          {/* <Route exact path="/paciente" component={Paciente} /> */}
          <Route exact path="/paciente" render={props => (<Paciente {...props} />)} />
          <Route exact path="/paciente/SolicitarCita" component={SolicitarCita} />
          <Route exact path="/administrador" component={Administrador} />
          <Route exact path="/administrador/GestionUsuarios" component={GestionUsuarios} />
          <Route exact path="/administrador/GestionUsuarios/CrearUsuario" component={CrearUsuario} />
          <Route exact path="/administrador/GestionCentrosSalud" component={GestionCentroSalud} />
          <Route exact path="/administrador/GestionCentrosSalud/CrearCentroSalud" component={CrearCentroSalud} />
          <Route exact path="/administrador/GestionDefinicionDeCitas" component={GestionDefinicionCitas} />
          <Route exact path="/Administrador/GestionDefinicionDeCitas/CrearPlantillaCitas" component={CrearPlantillaCitas} />
          <Route exact path="/Administrador/GestionDefinicionDeCitas/DefinirFormatoVacunacion" component={DefinirFormatoVacunacion} />
          <Route exact path="/Sanitario" component={Sanitario} />
        </Switch>
      </Router>
    </div>

  )
}

ReactDOM.render(
  <React.StrictMode>
    <Routing />
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
