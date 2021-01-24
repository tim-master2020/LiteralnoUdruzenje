import React, { useState } from 'react'
import { defaultUrl } from '../../backendConfig.js';
import axios from 'axios';
import { Form, Button, FormGroup, FormControl, ControlLabel, Col, Card } from "react-bootstrap";
import { withRouter } from 'react-router-dom';
import getUser from '../../functions/UserFunctions.js';
import './BookReview.css';
import withReactContent from 'sweetalert2-react-content';
import Swal from 'sweetalert2';
import validate from '../../functions/FormFunctions.js';
import CamundaForm from '../CamundaForm.js';
import streamSaver from 'streamsaver';



const alert = withReactContent(Swal)

const BookReview = ({ history,updateUser }) => {
    const [formFields, setformFields] = React.useState([]);
    const [ writer, setWriter ] = useState('');
    const [taskId, setTaskId] = React.useState('');
    const [selected,setSelected] =  React.useState([]);
    const [isValid, setIsValid] = React.useState({});
    const [shouldSubmit,setShouldSubmit] = React.useState(true);
    const [validationMessage, setValidationMessage] = React.useState({});

    const options = {
        headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') }
    };
    
    React.useEffect(() => {
        axios.get(`${defaultUrl}/process/get-form-fields/review/${history.location.state.taskId}`, options).then(
            (resp) => {
                setWriter(resp.data.writer);
                setformFields(resp.data.formFields);
            },
            (resp) => { alert.fire({
                text:'Error occured while getting fields, please try again.',
            }); }
        );
    }, []);

    
    function submitReview(e) {

        e.preventDefault();   
        const returnValue = [];
        let dataIsValid = true;
        formFields.forEach(field => {

            validate(field,field.value.value,setIsValid,isValid);
            if(Object.keys(isValid).length > 0){
                setValidationMessage(`Input value for field ${field.id} should be`)
                dataIsValid = false; 
            }
            
            if(field.type.name.includes('enum')){
                field.value.value = selected.value;
            }
            returnValue.push({ fieldId: field.id, fieldValue: field.value.value })
        });
        
        
        if(dataIsValid){
            console.log('taskid',taskId);
            console.log(returnValue);

            axios.post(`${defaultUrl}/api/reviews/save-review/${history.location.state.taskId}/${writer}`, returnValue).then(
            (resp) => {
                console.log(resp);
                alert.fire({
                    text:'You left review successfully!',
                });
                updateUser();
                history.push('/');
            },
            (resp) => { 
                alert.fire({
                    text:'Error occured, please try again.',
                });
            }
        );
    }
    }

    return (     
        <div className="contentDiv">
            <h3>You can download PDF files and rate writer based on them.</h3>
            <div className="reviewDiv">
               <p className="reviewWriter">Writer's username: </p> 
               <p className="reviewWriterName">{writer}</p>
            </div>
            <Card className="registrationCard" id="registrationCard">
                <Card.Title></Card.Title>
                <Card.Body>               
                    <CamundaForm
                    formFields={formFields}
                    onSubmit={(e) => { submitReview(e) }} 
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
export default withRouter(BookReview);