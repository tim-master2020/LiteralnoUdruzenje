import React from 'react'
import {Route, withRouter, Switch } from "react-router-dom";

const Routes = () => {

    return (
        <Switch>
            <Route exact path='/' render={props =>
                <div>
                    <Header />
                    <HomePage />
                </div>
                } />
            <Route exact path='/login' render={props =>
                <div>
                </div>
                } />
            <Route exact path='/register' render={props =>
                <div>
                </div>
                } />
        </Switch>
        );
}

export default withRouter(Routes);