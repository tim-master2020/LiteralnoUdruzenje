import { AppBar, Card } from '@material-ui/core';
import React from 'react';
import LoggedInHomePage from './LoggedInHomePage';
import LoggedOutHomepage from './LoggedOutHomepage';

const HomePage = ({loggedInUser,setLoggedIn,publishBookGeneralData,reviewBookGeneral,giveExplanation,uploadRestWork,comparePlagiats}) => {
    if(loggedInUser === undefined){
    return (
        <LoggedOutHomepage/>
    );
    }else if( loggedInUser !== undefined){
        return(<LoggedInHomePage 
            loggedInUser={loggedInUser} 
            setLoggedIn={setLoggedIn} 
            publishBookGeneralData={publishBookGeneralData}
            reviewBookGeneral={reviewBookGeneral}
            giveExplanation={giveExplanation}
            uploadRestWork={uploadRestWork}
            comparePlagiats={comparePlagiats}
            />);
    }

}
export default HomePage;