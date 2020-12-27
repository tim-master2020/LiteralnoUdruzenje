import React from 'react'
import { withRouter } from 'react-router-dom';
import RegistrationForm from '../../components/registration/Registration'

const RegistrationPage = ({history, props}) => {
    
    React.useEffect(() => {
        if(localStorage.length > 0){
            history.push('/');
        }
    }, []);

    return(!localStorage.getItem('token') && <RegistrationForm type={props} />)
}
export default withRouter(RegistrationPage);