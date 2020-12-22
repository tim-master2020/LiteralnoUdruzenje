import React from 'react'
import { defaultUrl } from '../../backendConfig.js';
import axios from 'axios';
import { Form, Button, FormGroup, FormControl, ControlLabel, Col, Card } from "react-bootstrap";
import { withRouter } from 'react-router-dom';

const LoginForm = ({ history }) => {

    const [username, setUsername] = React.useState('');
    const [password,setPasword] = React.useState('');

    function handleChange(evt) {
        const value = evt.target.value;
        /*etState({
            ...state,
            [evt.target.name]: value
        });*/
    }

    const sendLoginRequest = (e) => {

        e.preventDefault();
        const fields = [];

        fields.push({username: username, password: password});

        axios.post(`${defaultUrl}/auth/login`,fields).then(
            (resp) => this.onSuccessHandler(resp),
            (resp) => this.onErrorHandler(resp)
        );
    }

    return (
        <div>
            <Card style={{ width: '18rem' }}>
                <Card.Body>
                    <Card.Title>Enter your creditentials</Card.Title>
                    <Form onSubmit={(e) => { sendLoginRequest(e) }}>
                        <label htmlFor="username">Username</label>
                        <input type="text"
                            className="form-control form-control-sm"
                            id="username"
                            name="username"
                            onChange={(e) => {setUsername(e.target.value)}}
                            placeholder="Enter username"
                            required
                        />
                        <br />
                        <label htmlFor="password">Password</label>
                        <input type="password"
                            className="form-control form-control-sm"
                            id="password"
                            name="password"
                            onChange={(e) => {setPasword(e.target.value)}}
                            placeholder="Enter password"
                            required
                        />
                        <Button variant="primary" type="submit">
                            Login
                        </Button>
                    </Form>
                </Card.Body>
            </Card>
        </div>
    );
}
export default LoginForm;