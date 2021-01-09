import React from 'react'
import { defaultUrl } from '../../backendConfig.js';
import axios from 'axios';
import { Form, Button, FormGroup, FormControl, ControlLabel, Col, Card } from "react-bootstrap";
import './InitialUpload.css';
import { withRouter } from 'react-router-dom';
import CamundaForm from '../CamundaForm.js';
import { validate } from '../../functions/FormFunctions';

const InitialUpload = ({ history, type, processId }) => {

    const [formFields, setformFields] = React.useState([]);
    const [validationMessage, setValidationMessage] = React.useState({});
    const [selected, setSelected] = React.useState([]);
    const [isValid, setIsValid] = React.useState({});
    const [taskId, setTaskId] = React.useState('');
    const [shouldSubmit, setShouldSubmit] = React.useState(true);
    const [uploadedFiles, setUploadedFiles] = React.useState([]);
    console.log(processId);

    React.useEffect(() => {
        axios.get(`${defaultUrl}/api/writers/upload-pdf-task/${processId}`,).then(
            (resp) => {
                setformFields(resp.data.formFields);
                setTaskId(resp.data.taskId);
            },
            (resp) => { alert("error getting form fields,try again"); }
        );
    }, []);


    function SavePdfs(e) {

        let token = localStorage.getItem('token');
        const options = {
            headers: { 'Authorization': 'Bearer ' + token}
        };

        e.preventDefault();
        const returnValue = [];

        formFields.forEach(field => {
            returnValue.push({ fieldId: field.id, fieldValue: field.value.value })
        });


        console.log('taskid', taskId);
        console.log(returnValue);
        console.log(options);

        var numOfPdfs = 0;

        returnValue[0].fieldValue.forEach(numOfPdfs++);
        if(numOfPdfs < 4) {
            alert("You have to upload minimum of 2 PDF files!");
            return;
        }

        axios.post(`${defaultUrl}/api/books/save-pdfs/${taskId}`, returnValue, options).then(
            (resp) => {
                alert('Your documents are uploaded successfully.')
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

