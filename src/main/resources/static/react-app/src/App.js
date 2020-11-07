import logo from './logo.svg';
import './App.css';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <span>Primer Sprint realizado por:</span>
		<br></br>
        <span>Alonso Martín, Fernando</span>
		<span>Álvarez García, Gonzalo</span>
		<span>Martínez Fernández, Javier</span>
		<span>Ramos Blanco, María Isabel</span>
		<span>Vilariño Mayo, Javier</span>
		<span>Yugsi Yugsi, Evelyn</span>
		<br></br>
		<br></br>
        <a
          className="App-link"
          href="http://localhost:8081/alumnos"
          target="_blank"
          rel="noopener noreferrer"
        >
          Accede a nuestra primera funcionalidad
        </a>
      </header>
    </div>
  );
}

export default App;
