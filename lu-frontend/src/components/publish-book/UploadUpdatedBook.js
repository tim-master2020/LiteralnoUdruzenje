import React from 'react';
import { Card } from 'react-bootstrap';
import { withRouter } from 'react-router-dom';
import CamundaForm from '../CamundaForm';
import axios from 'axios';
import { defaultUrl } from '../../backendConfig.js';
import './Global.css';
import { alert } from '../../functions/alertSwal'

const UploadUpdatedBook = ({history,updateUser}) =>{

    const [formFields, setformFields] = React.useState([]);
    const [isValid, setIsValid] = React.useState({});
    const [shouldSubmit,setShouldSubmit] = React.useState(true);
    const [validationMessage, setValidationMessage] = React.useState({});
    const [selected,setSelected] =  React.useState([]);
    const [uploadedFiles, setUploadedFiles] = React.useState([]);
    const [taskId,setTaskId] =  React.useState(history.location.state.taskId);

    const options = {
        headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') }
    };

    React.useEffect(() => {
        axios.get(`${defaultUrl}/process/get-form-fields/${history.location.state.taskId}`, options).then(
            (resp) => {
                setformFields(resp.data.formFields);
                console.log('form fields in upload',resp.data.formFields);
            },
            (resp) => { alert("error getting form fields,try again"); }
        );
    }, []);

    function SavePdfs(e) {

        let token = localStorage.getItem('token');
        const options = {
            headers: { 'Authorization': 'Bearer ' + token}
        };

        //e.preventDefault();
        const returnValue = [];

        formFields.forEach(field => {
            returnValue.push({ fieldId: field.id, fieldValue: field.value.value })
        });

        axios.post(`${defaultUrl}/api/books/submit-updated-book/${history.location.state.taskId}`, returnValue, options).then(
            (resp) => {
                updateUser();
                if(resp.data.hasOwnProperty("message")){
                    alert(resp.data.message);
                }else{
                    alert('Your document is uploaded successfully!');
                }
                history.push('/');
            },
            (resp) => {
                alert('Error while uploading files,please try again.');
            }
        );
    }

    return(
        <div>
            <p className='title'>You can see comments for your book in your email. Based on them, you can edit book and upload it here.</p>
            <Card className="cardHolder">
                <CamundaForm
                formFields={formFields}
                setformFields={setformFields}
                setShouldSubmit={setShouldSubmit}
                setValidationMessage={setValidationMessage}
                isValid={isValid}
                setIsValid={setIsValid}
                selected={selected}
                setSelected={setSelected}
                onSubmit={(e) => { SavePdfs(e) }}
                uploadedFiles={uploadedFiles}
                setUploadedFiles={setUploadedFiles}
                />
            </Card>
        </div>
    );
}
export default withRouter(UploadUpdatedBook);