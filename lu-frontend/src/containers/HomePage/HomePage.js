import { AppBar, Card } from '@material-ui/core';
import React from 'react';
import LoggedInHomePage from './LoggedInHomePage';
import LoggedOutHomepage from './LoggedOutHomepage';

const HomePage = ({loggedInUser,setLoggedIn,isInitialUpload,isReview,isPayment,isReviewPreview,publishBookGeneralData,reviewBookGeneral,giveExplanation,uploadRestWork,comparePlagiats,downloadBook,decideBeta}) => {
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
            downloadBook={downloadBook}
            decideBeta={decideBeta}
            isInitialUpload={isInitialUpload}
            isReview={isReview}
            isPayment={isPayment}
            isReviewPreview={isReviewPreview}
            />);
    }

}
export default HomePage;