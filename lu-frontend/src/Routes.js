import React from 'react'
import { Route, withRouter, Switch } from "react-router-dom";
import HomePage from './containers/HomePage/HomePage';
import LoginPage from './containers/LoginReg/LoginPage';
import RegistrationPage from './containers/LoginReg/RegistrationPage';
import BetaReader from './components/registration/BetaReader.js'

const Routes = ({ loggedInUser, setLoggedIn }) => {

    return (
        <Switch>
            <Route exact path='/' render={props =>
                <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} />
            } />
            <Route exact path='/login' render={props =>
                <LoginPage setLoggedIn={setLoggedIn} />
            } />
            <Route exact path='/registration' render={props =>
                <RegistrationPage props={"ReaderRegistration"} />
            } />
            <Route exact path='/registration/writer' render={props =>
                <RegistrationPage props={"WriterRegistration"} />
            } />
            <Route exact path='/betaReader' render={props =>
                <BetaReader />
            } />
            <Route exact path='/review/:id' render={props =>
                <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} isReview={true} />
            } />
            <Route exact path='/upload/:id' render={props =>
                <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} isInitialUpload={true} />
            } />
            <Route exact path='/pay/:id' render={props =>
                <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} isPayment={true} />
            } />
            <Route exact path='/reviews' render={props =>
                <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} isReviewPreview={true} />
            } />
            <Route exact path='/bookGeneralData' render={props =>
                <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} publishBookGeneralData={true} />
            } />
            <Route exact path='/reviewBookGeneral/:taskId' render={props =>
                <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} reviewBookGeneral={true} />
            } />
            <Route exact path='/giveExplanation/:taskId' render={props =>
                <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} giveExplanation={true} />
            } />
            <Route exact path='/uploadRestWork/:taskId' render={props =>
                <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} uploadRestWork={true} />
            } />
            <Route exact path='/comparePlagiats/:taskId' render={props =>
                <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} comparePlagiats={true} />
            } />
            <Route exact path='/downloadBook/:taskId' render={props =>
                <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} downloadBook={true} />
            } />
            <Route exact path='/decideBeta/:taskId' render={props =>
                <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} decideBeta={true} />
            } />
            <Route exact path='/choosebetareader/:taskId' render={props =>
                <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} isChooseBetaReader={true} />
            } />
            <Route exact path='/leaveComment/:taskId' render={props =>
                <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} leaveComment={true} />
            } />
            <Route exact path='/updateBook/:taskId' render={props =>
                <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} uploadUpdatedBook={true} />
            } />
            <Route exact path='/editorReview/:taskId' render={props =>
                <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} editorReview={true} type={"editor"} />
            } />
            <Route exact path='/lectorReview/:taskId' render={props =>
                <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} lectorReview={true} type={"lector"} />
            } />
            <Route exact path='/mainEditorReview/:taskId' render={props =>
                <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} mainEditorReview={true} type={"mainEditor"} />
            } />
            <Route exact path='/printBook/:taskId' render={props =>
                <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} printBook={true} type={"printBook"} />
            } />
            <Route exact path='/fileAComplaint' render={props =>
                <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} isFileAComplaint={true} />
            } />
            <Route exact path='/choose-editor/:taskId' render={props =>
                <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} isChooseEditor={true} />
            } />
            <Route exact path='/download-and-review/:taskId' render={props =>
                <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} downloadAndReview={true} />
            } />
            <Route exact path='/find-replacement/:taskId' render={props =>
                <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} findReplacement={true} />
            } />
            <Route exact path='/reviewnotes/:taskId' render={props =>
                <HomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} isReviewNotes={true} />
            } />
        </Switch>
    );
}

export default withRouter(Routes);