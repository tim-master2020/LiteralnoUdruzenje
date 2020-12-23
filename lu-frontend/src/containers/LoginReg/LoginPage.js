import React from 'react'
import LoginForm from '../../components/login/LoginForm';
import './LoginPage.css';
import { withRouter } from 'react-router-dom';
import HomePage from '../HomePage/HomePage';


const LoginPage = ({setLoggedIn,history}) => {

    React.useEffect(() => {
        if(localStorage.length > 0){
            history.push('/');
        }
    }, []);

    return(!localStorage.getItem('token') && <LoginForm setLoggedIn={setLoggedIn}/>);

}
export default withRouter(LoginPage);