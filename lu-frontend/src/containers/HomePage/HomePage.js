import { AppBar, Card } from '@material-ui/core';
import React from 'react';
import LoggedInHomePage from './LoggedInHomePage';
import LoggedOutHomepage from './LoggedOutHomepage';

const HomePage = ({loggedInUser,setLoggedIn,isInitialUpload,isReview}) => {
    if(loggedInUser === undefined){
    return (
        <LoggedOutHomepage/>
    );
    }else if( loggedInUser !== undefined){
        return(<LoggedInHomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} isInitialUpload={isInitialUpload} isReview={isReview}/>);
    }

}
export default HomePage;