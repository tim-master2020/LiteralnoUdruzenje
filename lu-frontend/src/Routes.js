import React from 'react'
import {Route, withRouter, Switch } from "react-router-dom";
import HomePage from './containers/HomePage/HomePage';
import LoginPage from './containers/LoginReg/LoginPage';
import RegistrationPage from './containers/LoginReg/RegistrationPage';
import BetaReader from './components/registration/BetaReader.js'
import InitialUploadPage from './containers/UploadPDF/InitialUploadPage.js'

const Routes = ({loggedInUser,setLoggedIn}) => {

    return (
        <Switch>
            <Route exact path='/' render={props =>
                    <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn}/>
                } />
            <Route exact path='/login' render={props =>
                    <LoginPage setLoggedIn={setLoggedIn} />
                } />
            <Route exact path='/registration' render={props =>
                    <RegistrationPage props={"ReaderRegistration"} />
                } />
            <Route exact path='/registration/writer' render={props =>
                    <RegistrationPage props={"WriterRegistration"}/>
                } />
             <Route exact path='/betaReader' render={props =>
                    <BetaReader/>
                } />
             <Route exact path='/upload/:id' render={props =>
                    <InitialUploadPage />
                } />
        </Switch>
        );
}

export default withRouter(Routes);