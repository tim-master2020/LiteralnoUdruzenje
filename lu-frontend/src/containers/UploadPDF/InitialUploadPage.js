import React from 'react'
import InitialUpload from '../../components/uploadPDF/InitialUpload';
import { withRouter } from 'react-router-dom';
import HomePage from '../HomePage/HomePage';


const InitialUploadPage = ({setLoggedIn,history}) => {

    React.useEffect(() => {
        if(localStorage.length > 0){
            history.push('/');
        }
    }, []);

    return(!localStorage.getItem('token') && <InitialUpload setLoggedIn={setLoggedIn}/>);

}
export default withRouter(InitialUploadPage);