import { AppBar, Card } from '@material-ui/core';
import React from 'react';
import LoggedInHomePage from './LoggedInHomePage';
import LoggedOutHomepage from './LoggedOutHomepage';

const HomePage = ({
    loggedInUser,
    setLoggedIn,
    isInitialUpload,
    isReview,
    isPayment,
    isReviewPreview,
    publishBookGeneralData,
    reviewBookGeneral,
    giveExplanation,
    uploadRestWork,
    comparePlagiats,
    downloadBook,
    decideBeta, 
    isChooseBetaReader,
    leaveComment,
    uploadUpdatedBook,
    editorReview,
    lectorReview,
    mainEditorReview,
    printBook,
    isFileAComplaint,
    isChooseEditor,
    type,
    downloadAndReview,
    isReviewNotes,
    findReplacement}) => {
    if(loggedInUser === undefined){
    return (
        <LoggedOutHomepage/>
    );
    }else if( loggedInUser !== undefined){
        return(
        <LoggedInHomePage 
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
            isChooseBetaReader={isChooseBetaReader}
            leaveComment={leaveComment}
            uploadUpdatedBook={uploadUpdatedBook}
            editorReview={editorReview}
            lectorReview={lectorReview}
            mainEditorReview={mainEditorReview}
            printBook={printBook}
            isFileAComplaint={isFileAComplaint}
            isChooseEditor={isChooseEditor}
            type={type}
            downloadAndReview={downloadAndReview}
            isReviewNotes={isReviewNotes}
            findReplacement={findReplacement}
            />);
    }

}
export default HomePage;