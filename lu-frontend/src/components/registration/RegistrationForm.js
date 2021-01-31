import React from 'react'
import { defaultUrl } from '../../backendConfig.js';
import axios from 'axios';
import { Form, Button, FormGroup, FormControl, ControlLabel, Col, Card } from "react-bootstrap";
import './RegistrationForm.css';
import { withRouter } from 'react-router-dom';
import CamundaForm from '../CamundaForm.js';

import BetaReader from './BetaReader';
import { alert } from '../../functions/alertSwal' 
import validate from '../../functions/FormFunctions.js';

const RegistrationForm = ({history, type}) => {

    const [formFields, setformFields] = React.useState([]);
    const [validationMessage, setValidationMessage] = React.useState({});
    const [selected,setSelected] =  React.useState([]);
    const [isValid, setIsValid] = React.useState({});
    const [taskId, setTaskId] = React.useState('');
    const [shouldSubmit,setShouldSubmit] = React.useState(true);

    React.useEffect(() => {
        axios.get(`${defaultUrl}/api/users/reg-task/${type}`,).then(
            (resp) => {
                setformFields(resp.data.formFields);
                setTaskId(resp.data.taskId);
            },
            (resp) => { alert("error getting form fields,try again"); }
        );
    }, [type]);


    function SendRegisterRequest() {

        //e.preventDefault();   
        const returnValue = [];
        let dataIsValid = true;
        formFields.forEach(field => {

            // validate(field,field.value.value,setIsValid,isValid);
            // if(Object.keys(isValid).length > 0){
            //     setValidationMessage(`Input value for field ${field.id} should be`)
            //     dataIsValid = false; 
            // }

            field.value.value = (field.id === "betaReader" && field.value.value === null) ? false : field.value.value;
            
            if(field.type.name.includes('multiEnum_genres')){
                field.value.value = selected;
            }
            returnValue.push({ fieldId: field.id, fieldValue: field.value.value })
        });
        
        
        //f(dataIsValid){
           
            var role = (type === "WriterRegistration") ? "writer" : "reader";
            axios.post(`${defaultUrl}/api/users/submit-general-data/${taskId}/${role}`, returnValue).then(
            (resp) => {
                if (resp.data !== "") {
                
                    history.push({
                        pathname: '/betaReader',
                        state: {
                          formFields: resp.data.formFields,
                          taskId: resp.data.taskId
                        }
                      });
                                       
                }else{
                    alert('We have sent you email with conformation link.')
                    history.push('/');
                }
            },
            (resp) => { 
                alert("Validation failed or this user already exists."); 
            }
        );
    //}
    }


    return (
        <div className="contentDiv">
            <Card className="registrationCard" id="registrationCard">
                <Card.Title></Card.Title>
                <Card.Body>               
                    <CamundaForm
                    formFields={formFields}
                    onSubmit={(e) => { SendRegisterRequest(e) }} 
                    shouldSubmit={shouldSubmit} 
                    setShouldSubmit={setShouldSubmit}
                    setValidationMessage={setValidationMessage}
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
export default withRouter(RegistrationForm);

