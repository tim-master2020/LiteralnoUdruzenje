import React from 'react'
import { defaultUrl } from '../../backendConfig.js';
import axios from 'axios';
import { Form, Button, FormGroup, FormControl, ControlLabel, Col, Card } from "react-bootstrap";
import { withRouter } from 'react-router-dom';
import getUser from '../../functions/UserFunctions.js';
import '../../containers/LoginReg/LoginPage.css';

const LoginForm = ({ history , setLoggedIn}) => {

    const [username, setUsername] = React.useState('');
    const [password,setPasword] = React.useState('');

    function handleChange(evt) {
        const value = evt.target.value;
    }

    const sendLoginRequest = (e) => {

        e.preventDefault();
        const fields = [];

        const user = {username: username, password: password};

        axios.post(`${defaultUrl}/api/users/login`,user).then(
            (resp) => {               
                alert('success');
                localStorage.setItem('token', resp.data.accessToken);
                history.push({pathname: '/'});
                getUser(setLoggedIn);
        },
            (resp) => {alert('wrong')}
        );
    }

    return (     
            <Card className="loginFormCard">
                <Card.Body>
                    <Card.Title className="loginFormTitle">Enter your creditentials</Card.Title>
                    <Form onSubmit={(e) => { sendLoginRequest(e) }} className="loginForm">

                        <Form.Group  as={Col} className="loginFormInputField">
                            <Form.Label>Username</Form.Label>
                            <Form.Control 
                            id="username" 
                            name="username" 
                            onChange={(e) => {setUsername(e.target.value)}}
                            required
                            placeholder="Enter username"
                             />
                        </Form.Group>

                        <Form.Group  as={Col} className="loginFormInputField">
                            <Form.Label>Password</Form.Label>
                            <Form.Control 
                            id="password" 
                            name="password"
                            type="password" 
                            onChange={(e) => {setPasword(e.target.value)}}
                            required
                            placeholder="Enter password"
                             />
                        </Form.Group>
                        
                        <Form.Group  as={Col} className="loginFormInputField">
                            <Button variant="primary" type="submit" className="loginButton">
                            Login
                        </Button>
                        </Form.Group>
                            
                    </Form>
                </Card.Body>
            </Card>
       
    );
}
export default withRouter(LoginForm);