import React from 'react';
import { Card } from 'react-bootstrap';
import { withRouter } from 'react-router-dom';
import CamundaForm from '../CamundaForm';
import axios from 'axios';
import { defaultUrl } from '../../backendConfig.js';
import './Global.css';
import withReactContent from 'sweetalert2-react-content';
import Swal from 'sweetalert2';
const alert = withReactContent(Swal)

const UploadRestOfTheWork = ({history,updateUser}) =>{

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

        axios.post(`${defaultUrl}/api/books/submit-rest-of-work/${history.location.state.taskId}`, returnValue, options).then(
            (resp) => {
                updateUser();
                if(resp.data.hasOwnProperty("message")){
                    alert.fire({
                        text:resp.data.message,
                        confirmButtonText: `Okay`,}).then((result) => {

                        if (result.isConfirmed) {
                          history.push('/');
                        }
                      })
                }else{
                alert.fire({
                    text:'Your documents are uploaded successfully.',
                    confirmButtonText: `Okay`,}).then((result) => {
                    if (result.isConfirmed) {
                      history.push('/');
                    }
                  })
                }
                //window.location.reload();
            },
            (resp) => {
                alert.fire({text:'Error while uploading files,please try again.'});
            }
        );
    }

    return(
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
    );
}
export default withRouter(UploadRestOfTheWork);