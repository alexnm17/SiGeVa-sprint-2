import { Button, Breadcrumb } from 'react-bootstrap'
import './App.css'
import 'bootstrap/dist/css/bootstrap.min.css'

function App() {
	return (
		<div className="App" align="center">
			<Breadcrumb style={{margin:30}}>
				<Breadcrumb.Item href="/">SiGeVa</Breadcrumb.Item>
			</Breadcrumb>
			<a href="/administrador">
				<Button style={{ marginRight: 15 }}>Entrar como Administrador</Button>
			</a>
			<a href="/paciente">
				<Button>Entrar como Paciente</Button>
			</a>
		</div>
	)
}

export default App;