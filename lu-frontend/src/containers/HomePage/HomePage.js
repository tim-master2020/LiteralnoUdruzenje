import { AppBar, Card } from '@material-ui/core';
import React from 'react';
import LoggedInHomePage from './LoggedInHomePage';
import LoggedOutHomepage from './LoggedOutHomepage';

const HomePage = ({loggedInUser,setLoggedIn,publishBookGeneralData}) => {
    if(loggedInUser === undefined){
    return (
        <LoggedOutHomepage/>
    );
    }else if( loggedInUser !== undefined){
        return(<LoggedInHomePage loggedInUser={loggedInUser} setLoggedIn={setLoggedIn} publishBookGeneralData={publishBookGeneralData}/>);
    }

}
export default HomePage;