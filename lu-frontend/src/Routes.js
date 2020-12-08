import React from 'react'
import {Route, withRouter, Switch } from "react-router-dom";
import Appbar from './components/appbar/Appbar';
import HomePage from './containers/HomePage/HomePage';
import LoginPage from './containers/LoginReg/LoginPage';
import RegistrationPage from './containers/LoginReg/RegistrationPage';

const Routes = () => {

    return (
        <Switch>
            <Route exact path='/' render={props =>
                <div>
                    <HomePage />
                </div>
                } />
            <Route exact path='/login' render={props =>
                <div>
                    <Appbar/>
                    <LoginPage/>
                </div>
                } />
            <Route exact path='/registration' render={props =>
                <div>
                    <Appbar/>
                    <RegistrationPage/>
                </div>
                } />
        </Switch>
        );
}

export default withRouter(Routes);