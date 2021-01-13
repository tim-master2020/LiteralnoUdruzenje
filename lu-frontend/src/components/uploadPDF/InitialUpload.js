import React from 'react'
import { defaultUrl } from '../../backendConfig.js';
import axios from 'axios';
import { Form, Button, FormGroup, FormControl, ControlLabel, Col, Card } from "react-bootstrap";
import './InitialUpload.css';
import { withRouter } from 'react-router-dom';
import CamundaForm from '../CamundaForm.js';
import { validate } from '../../functions/FormFunctions';
import { alert } from '../../functions/alertSwal' 

const InitialUpload = ({ history,updateUser }) => {

    const [formFields, setformFields] = React.useState([]);
    const [validationMessage, setValidationMessage] = React.useState({});
    const [selected, setSelected] = React.useState([]);
    const [isValid, setIsValid] = React.useState({});
    const [taskId, setTaskId] = React.useState('');
    const [shouldSubmit, setShouldSubmit] = React.useState(true);
    const [uploadedFiles, setUploadedFiles] = React.useState([]);
    const [taskName, setTaskName] = React.useState('');

    const options = {
        headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') }
    };
    
    React.useEffect(() => {
        axios.get(`${defaultUrl}/process/get-form-fields/${history.location.state.taskId}`, options).then(
            (resp) => {
                setTaskName(resp.data.taskName);
                setformFields(resp.data.formFields);
            },
            (resp) => { alert("error getting form fields,try again"); }
        );
    }, []);


    function SavePdfs(e) {

        e.preventDefault();
        const returnValue = [];

        formFields.forEach(field => {
            returnValue.push({ fieldId: field.id, fieldValue: field.value.value })
        });

        console.log(returnValue);

        var numOfPdfs = 0;

        if(taskName === 'UploadPDFForm'){
            returnValue[0].fieldValue.forEach(item => {numOfPdfs++});
            if(numOfPdfs < 4) {
                alert("You have to upload minimum of 2 PDF files!");
                return;
            }
        }

        axios.post(`${defaultUrl}/api/books/save-pdfs/${history.location.state.taskId}`, returnValue, options).then(
            (resp) => {
                alert('Your documents are uploaded successfully.')
                updateUser();
                history.push('/');
            },
            (resp) => {
                alert("Uploading failed, try again.");
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
                        onSubmit={(e) => { SavePdfs(e) }}
                        shouldSubmit={shouldSubmit}
                        setShouldSubmit={setShouldSubmit}
                        setValidationMessage={setValidationMessage}
                        selected={selected}
                        setSelected={setSelected}
                        setformFields={setformFields}
                        isValid={isValid}
                        setIsValid={setIsValid}
                        uploadedFiles={uploadedFiles}
                        setUploadedFiles={setUploadedFiles}
                    />
                </Card.Body>
            </Card>
        </div>
    );
}
export default withRouter(InitialUpload);

