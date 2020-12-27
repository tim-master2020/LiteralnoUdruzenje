import React from 'react'
import { defaultUrl } from '../../backendConfig.js';
import axios from 'axios';
import { Form, Button, FormGroup, FormControl, ControlLabel, Col, Card } from "react-bootstrap";
import './InitialUpload.css';
import { withRouter } from 'react-router-dom';
import CamundaForm from '../CamundaForm.js';
import { validate } from '../../functions/FormFunctions';

const InitialUpload = ({ history, setLoggedIn, type, processId }) => {

    const [formFields, setformFields] = React.useState([]);
    const [validationMessage, setValidationMessage] = React.useState({});
    const [selected, setSelected] = React.useState([]);
    const [isValid, setIsValid] = React.useState({});
    const [taskId, setTaskId] = React.useState('');
    const [shouldSubmit, setShouldSubmit] = React.useState(true);
    const [uploadedFiles, setUploadedFiles] = React.useState([]);

    React.useEffect(() => {
        axios.get(`${defaultUrl}/api/writers/upload-pdf-task/${processId}`,).then(
            (resp) => {
                console.log(resp.data.formFields);
                setformFields(resp.data.formFields);
                setTaskId(resp.data.taskId);
            },
            (resp) => { alert("error getting form fields,try again"); }
        );
    }, [type]);


    function SavePdfs(e) {

        e.preventDefault();
        const returnValue = [];
        //let dataIsValid = true;
        formFields.forEach(field => {
            returnValue.push({ fieldId: field.id, fieldValue: field.value.value })
        });


        console.log('taskid', taskId);
        console.log(returnValue);

        axios.post(`${defaultUrl}/api/books/save-pdfs/${taskId}`, returnValue).then(
            (resp) => {
                alert('Your documents are uploaded successfully.')
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

