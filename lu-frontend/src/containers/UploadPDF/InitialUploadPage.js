import React from 'react'
import InitialUpload from '../../components/uploadPDF/InitialUpload';
import { withRouter, useParams } from 'react-router-dom';
import HomePage from '../HomePage/HomePage';


const InitialUploadPage = ({setLoggedIn,history}) => {

    let { id } = useParams();
    console.log(id)

    React.useEffect(() => {
        if(localStorage.length > 0){
            history.push('/');
        }
    }, []);

    return(!localStorage.getItem('token') && <InitialUpload setLoggedIn={setLoggedIn} processId={id}/>);

}
export default withRouter(InitialUploadPage);