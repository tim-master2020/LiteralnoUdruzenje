import React from 'react'
import { defaultUrl } from '../../backendConfig.js';
import axios from 'axios';
import { Form, Button, FormGroup, FormControl, ControlLabel,Col,Card } from "react-bootstrap";
import './RegistrationForm.css';

const RegistrationForm = () => {
    
    const [formFields, setformFields] = React.useState([]);
    const [genreFields, setGenreFields] = React.useState([]);
    const [taskId, setTaskId] = React.useState('');
    React.useEffect(() => {
        axios.get(`${defaultUrl}/api/users/reg-task-user`,).then(
            (resp) => { setformFields(resp.data.formFields);
                        setTaskId(resp.data.taskId);
                      },
            (resp) => { alert("error getting form fields,try again"); }
        );
    },[]);

    /*function handleChange(e){
        var temp = formFields;
        temp.forEach(field => {
            if(e.target.name === field.name){
                field.value = e.target.value;
                alert(e.target.name + " : " + field.target.value);
            }
        });
        setformFields(temp);
    }*/
    console.log(taskId);

    const handleChange = e => {
        var temp = formFields;
        temp.forEach(field => {
            if(e.target.name === field.id){
                if(field.id === "betaReader"){
                    field.value.value = (field.value.value === true)? false : true;
                }else{
                field.value.value = e.target.value;
                }
            }
        });
        setformFields(temp);
        console.log(temp);
     };

     function SendRegisterRequest(e){
        e.preventDefault();
        
        const returnValue = [];
        formFields.forEach(field => {
           field.value.value = (field.id === "betaReader" && field.value.value ===  null)? false : field.value.value;        
            returnValue.push({fieldId: field.id,fieldValue:field.value.value})
        });
        console.log(returnValue);
        axios.post(`${defaultUrl}/api/users/submit-reg-data/${taskId}`,returnValue).then(
            (resp) => { 
                    if(resp.data !== null){
                        setGenreFields(resp.data.formFields);
                    }
            },
            (resp) => { alert("not registered"); }
        );
    }

    console.log('genre fields:',genreFields);

    function renderFormFields(formFields){  
        if(formFields !== undefined && formFields.length > 0){           
            return formFields.map(field =>
                (field.type.name === "boolean") ?
                
                (   <div className="checkBoxField">
                    {field.label}
                    <br/>
                    <input type="checkbox"  id={field.id} name={field.id} onChange={handleChange} defaultValue="false"/>
                    </div>
                )
                :           
                (<Form.Group as={Col} className="singleInputField">
                    <Form.Label>{field.label}</Form.Label>
                    <Form.Control type={field.type.name} id={field.id} name={field.id} onChange={handleChange}/>
                </Form.Group>)
                
            )
        }
    }


    return (
        <Card className="registrationCard">
            <Card.Body>
            <Form onSubmit={(e) => {SendRegisterRequest(e)}}>
                {renderFormFields(formFields)}
            <Button className="submitButton" type="submit" variant="outline-dark">Submit</Button>
            {genreFields !== null &&
            <div>
                {renderFormFields(genreFields)}
            </div>
            }
            </Form>
            </Card.Body>
        </Card>
    );
} 
export default RegistrationForm;

