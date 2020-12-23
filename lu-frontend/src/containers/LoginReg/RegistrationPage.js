import React from 'react'
import { withRouter } from 'react-router-dom';
import RegistrationForm from '../../components/registration/Registration'

const RegistrationPage = ({history}) => {
    
    React.useEffect(() => {
        debugger;
        if(localStorage.length > 0){
            history.push('/');
        }
    }, []);

    return(!localStorage.getItem('token') && <RegistrationForm/>)
}
export default withRouter(RegistrationPage);