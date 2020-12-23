import './App.css';
import Routes from './Routes.js';
import { Route, withRouter, Switch, BrowserRouter as Router } from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';
import React from 'react';
import getUser from './functions/UserFunctions';
import Appbar from './components/appbar/Appbar';

const App = () => {
  const [loggedInUser, setLoggedIn] = React.useState(undefined);

  React.useEffect(() => {
    getUser(setLoggedIn);
}, []);

  return (
    <div className="App">
      <Router>
        <Appbar loggedInUser = {loggedInUser} setLoggedIn= {setLoggedIn}/>
        <Routes setLoggedIn= {setLoggedIn}/>
      </Router>
    </div>
  );
}

export default App;
