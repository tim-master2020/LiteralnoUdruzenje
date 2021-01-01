import React from 'react'
import InitialUpload from '../../components/uploadPDF/InitialUpload';
import { withRouter, useParams } from 'react-router-dom';
import HomePage from '../HomePage/HomePage';


const InitialUploadPage = ({history}) => {

    let { id } = useParams();

    React.useEffect(() => {
        if(localStorage.length == 0){
            history.push('/');
        }
    }, []);

    return(localStorage.getItem('token') && <InitialUpload processId={id}/>);

}
export default withRouter(InitialUploadPage);