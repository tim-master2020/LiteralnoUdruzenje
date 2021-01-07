import React from 'react'
import {Route, withRouter, Switch } from "react-router-dom";
import HomePage from './containers/HomePage/HomePage';
import LoginPage from './containers/LoginReg/LoginPage';
import RegistrationPage from './containers/LoginReg/RegistrationPage';
import BetaReader from './components/registration/BetaReader.js'

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
                    <RegistrationPage/>
                } />
             <Route exact path='/betaReader' render={props =>
                    <BetaReader/>
                } />
             <Route exact path='/bookGeneralData' render={props =>
                    <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} publishBookGeneralData={true}/>
                } />
             <Route exact path='/reviewBookGeneral/:taskId' render={props =>
                <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} reviewBookGeneral={true}/>
                } />
            <Route exact path='/giveExplanation/:taskId' render={props =>
                <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} giveExplanation={true}/>
                } />
        </Switch>
        );
}

export default withRouter(Routes);