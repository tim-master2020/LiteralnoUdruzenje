import React from 'react'
import { defaultUrl } from '../../backendConfig.js';
import axios from 'axios';
import { Form, Button, FormGroup, FormControl, ControlLabel,Col,Card } from "react-bootstrap";
import './RegistrationForm.css';

const RegistrationForm = () => {
    
    const [formData, setformData] = React.useState([]);
    React.useEffect(() => {
        axios.get(`${defaultUrl}/api/users/reg-task-user`,).then(
            (resp) => { setformData(resp.data);},
            (resp) => { alert("error getting form fields,try again"); }
        );
    },[]);

    console.log(formData);


    return (
        <Card className="registrationCard">
            <Card.Body>
            <Form onSubmit={(e) => {SendRegisterRequest(e)}}>
                {renderFormFields(formData.formFields)}
            <Button className="submitButton" type="submit" variant="outline-dark">Submit</Button>
            </Form>
            </Card.Body>
        </Card>
    );
} 
export default RegistrationForm;


function renderFormFields(formFields){  
    if(formFields !== undefined && formFields.length > 0){           
        return formFields.map(field =>
            (field.id === 'betaReader') ?
            
            (   <div className="checkBoxField">
                {field.label}
                <br/>
                <input type="checkbox"  id={field.id} name={field.id}/>
                </div>
            )
            :
            
            (<Form.Group as={Col} className="singleInputField">
                <Form.Label>{field.label}</Form.Label>
                <Form.Control type={field.type.name} id={field.id} name={field.id}/>
            </Form.Group>)
            
        )
    }
}

function SendRegisterRequest(e){
    e.preventDefault();
    alert('registerd');
}