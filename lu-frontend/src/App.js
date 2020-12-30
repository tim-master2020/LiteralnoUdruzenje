import './App.css';
import Routes from './Routes.js';
import { Route, withRouter, Switch, BrowserRouter as Router } from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';
import React from 'react';
import getUser from './functions/UserFunctions';

const App = () => {
  const [loggedInUser, setLoggedIn] = React.useState(undefined);

  React.useEffect(() => {
    getUser(setLoggedIn);
}, []);

//<Appbar loggedInUser = {loggedInUser} setLoggedIn= {setLoggedIn}/>

  return (
    <div className="App">
      <Router>
        <Routes setLoggedIn= {setLoggedIn} loggedInUser={loggedInUser}/>
      </Router>
    </div>
  );
}

export default App;
