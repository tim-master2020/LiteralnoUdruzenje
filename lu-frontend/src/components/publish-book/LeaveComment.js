import React from 'react';
import axios from 'axios';
import CamundaForm from '../CamundaForm';
import { Card } from 'react-bootstrap';
import { withRouter } from 'react-router-dom';
import { defaultUrl } from '../../backendConfig';
import { alert } from '../../functions/alertSwal';

const LeaveComment =({history,updateUser}) => {

    const [formFields, setformFields] = React.useState([]);
    const [isValid, setIsValid] = React.useState({});
    const [shouldSubmit,setShouldSubmit] = React.useState(true);
    const [validationMessage, setValidationMessage] = React.useState({});

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

    function submitComment(e) {

        e.preventDefault();
        const returnArray = [];
        formFields.forEach(field => {
            returnArray.push({ fieldId: field.id, fieldValue: field.value.value });
        });

        axios.post(`${defaultUrl}/api/books/submit-comment/${history.location.state.taskId}`, returnArray, options).then(
            (resp) => {
                updateUser();
                alert('Comment submited');
                history.push('/');
            },
            (resp) => {
                alert('Error occured please try again');
            }
        );
    }

    return (
        <Card style={{ width: '40%', padding: '15px', marginLeft: '25%' }}>

            <CamundaForm
                id="camundaForm"
                formFields={formFields}
                setformFields={setformFields}
                isValid={isValid}
                setIsValid={setIsValid}
                shouldSubmit={shouldSubmit}
                setShouldSubmit={setShouldSubmit}
                setValidationMessage={setValidationMessage}
                onSubmit={submitComment}
            />
        </Card>
    )


}
export default withRouter(LeaveComment);