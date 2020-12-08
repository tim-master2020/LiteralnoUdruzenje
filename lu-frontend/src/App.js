import './App.css';
import Routes from './Routes.js';
import {Route, withRouter, Switch, BrowserRouter as Router} from "react-router-dom";

function App() {
  return (
    <div className="App">
      <Router>
        <Routes/>
      </Router>
    </div>
  );
}

export default App;
