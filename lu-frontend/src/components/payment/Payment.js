import React from 'react'
import { defaultUrl } from '../../backendConfig.js';
import axios from 'axios';
import { Form, Button, FormGroup, FormControl, ControlLabel, Col, Card } from "react-bootstrap";
import { withRouter } from 'react-router-dom';
import CamundaForm from '../CamundaForm.js';
import { validate } from '../../functions/FormFunctions';

const Payment = ({ history, tId }) => {

    const [formFields, setformFields] = React.useState([]);
    const [selected, setSelected] = React.useState([]);
    const [isValid, setIsValid] = React.useState({});
    const [taskId, setTaskId] = React.useState('');
    const [shouldSubmit, setShouldSubmit] = React.useState(true);
    console.log(tId);

    React.useEffect(() => {
        axios.get(`${defaultUrl}/api/writers/pay/${tId}`,).then(
            (resp) => {
                console.log(resp.data.formFields);
                setformFields(resp.data.formFields);
                setTaskId(resp.data.taskId);
                
            },
            (resp) => { alert("error getting form fields,try again"); }
        );
    }, []);


    function pay(e) {

        let token = localStorage.getItem('token');
        const options = {
            headers: { 'Authorization': 'Bearer ' + token}
        };

        e.preventDefault();

        axios.post(`${defaultUrl}/api/writers/activate-account/${taskId}`, options).then(
            (resp) => {
                alert('The account is activated')
                history.push('/');
            },
            (resp) => {
                alert("Error occurd. Couldn't activate the account");
            }
        );
    }


    return (
        <div className="contentDiv">
            <Card className="registrationCard" id="registrationCard">
                <Card.Title></Card.Title>
                <Card.Body>
                    <CamundaForm
                        formFields={formFields}
                        onSubmit={(e) => { pay(e) }}
                        shouldSubmit={shouldSubmit}
                        setShouldSubmit={setShouldSubmit}
                        selected={selected}
                        setSelected={setSelected}
                        setformFields={setformFields}
                        isValid={isValid}
                        setIsValid={setIsValid}
                    />
                </Card.Body>
            </Card>
        </div>
    );
}
export default withRouter(Payment);