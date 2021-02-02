import React from 'react'
import { defaultUrl } from '../../backendConfig.js';
import axios from 'axios';
import { Form, Button, FormGroup, FormControl, ControlLabel, Col, Card } from "react-bootstrap";
import { withRouter } from 'react-router-dom';
import CamundaForm from '../CamundaForm.js';
import { validate } from '../../functions/FormFunctions';
import { alert } from '../../functions/alertSwal' 

const Payment = ({ history,updateUser }) => {

    const [formFields, setformFields] = React.useState([]);
    const [selected, setSelected] = React.useState([]);
    const [isValid, setIsValid] = React.useState({});
    const [shouldSubmit, setShouldSubmit] = React.useState(true);

    const options = {
        headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') }
    };

    React.useEffect(() => {
        axios.get(`${defaultUrl}/process/get-form-fields/${history.location.state.taskId}`, options).then(
            (resp) => {
                setformFields(resp.data.formFields);
            },
            (resp) => { alert("error getting form fields,try again"); }
        );
    }, []);

    const mystyle = {
        fontFamily: 'Calibri',
        fontSize: '21px',
        color: ' rgb(93, 106, 117)'
      };

    function pay(e) {

        let token = localStorage.getItem('token');
        const options = {
            headers: { 'Authorization': 'Bearer ' + token}
        };

        //e.preventDefault();

        axios.post(`${defaultUrl}/api/writers/activate-account/${history.location.state.taskId}`, options).then(
            (resp) => {
                alert('The account is activated')
                updateUser();
                history.push('/');
            },
            (resp) => {
                alert("Error occurd. Couldn't activate the account.");
            }
        );
    }

    return (
        <div className="contentDiv">
            <p style={mystyle}>By clicking on submit button, you are confirming to pay for your registration on this website.</p>
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