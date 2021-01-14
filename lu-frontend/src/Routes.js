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
                    <RegistrationPage props={"ReaderRegistration"} />
                } />
            <Route exact path='/registration/writer' render={props =>
                    <RegistrationPage props={"WriterRegistration"}/>
                } />
             <Route exact path='/betaReader' render={props =>
                    <BetaReader/>
                } />
             <Route exact path='/review/:id' render={props =>
                    <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} isReview={true}/>
                } />
                 <Route exact path='/upload/:id' render={props =>
                    <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} isInitialUpload={true}/>
                } />
                <Route exact path='/pay/:id' render={props =>
                    <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} isPayment={true}/>
                } />
            <Route exact path='/reviews' render={props =>
                    <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} isReviewPreview={true}/>
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
            <Route exact path='/uploadRestWork/:taskId' render={props =>
                <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} uploadRestWork={true}/>
                } />
             <Route exact path='/comparePlagiats/:taskId' render={props =>
                <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} comparePlagiats={true}/>
                } />
            <Route exact path='/downloadBook/:taskId' render={props =>
                <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} downloadBook={true}/>
                } />
            <Route exact path='/decideBeta/:taskId' render={props =>
                <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} decideBeta={true}/>
                } />
            <Route exact path='/choosebetareader/:taskId' render={props =>
                <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} isChooseBetaReader={true}/>
                } />
        </Switch>
        );
}

export default withRouter(Routes);