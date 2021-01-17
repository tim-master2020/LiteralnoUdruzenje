import React from 'react';
import { withRouter } from 'react-router-dom';
import CamundaForm from '../CamundaForm';
import axios from 'axios';
import { defaultUrl } from '../../backendConfig';
import { Card } from 'react-bootstrap';
import { alert } from '../../functions/alertSwal';

const DownloadBooksAndLeaveNotes = ({history,updateUser})=> {

    const [formFields, setformFields] = React.useState([]);
    const [isValid, setIsValid] = React.useState({});
    const [shouldSubmit,setShouldSubmit] = React.useState(true);
    const [validationMessage, setValidationMessage] = React.useState({});
    const [selected,setSelected] =  React.useState([]);
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

    function submitNotes(e) {

        e.preventDefault();
        const returnArray = [];
        formFields.forEach(field => {
            returnArray.push({ fieldId: field.id, fieldValue: field.value.value });
        });

        axios.post(`${defaultUrl}/plagiarism/submit-review/${history.location.state.taskId}`, returnArray, options).then(
            (resp) => {
                updateUser();
                alert('Your  notes successfully.');
                history.push('/');
            },
            (resp) => {
                alert("Error,please try again");
            }
        );
    }

    return(
        <div>
            <p className="title">You can download both books,compare them and leave your notes.</p>
            <Card className='cardHolder'>
                <CamundaForm
                formFields={formFields}
                setformFields={setformFields}
                setShouldSubmit={setShouldSubmit}
                setValidationMessage={setValidationMessage}
                isValid={isValid}
                setIsValid={setIsValid}
                selected={selected}
                setSelected={setSelected}
                onSubmit={submitNotes}
                />
            </Card>
        </div>
    )

}
export default withRouter (DownloadBooksAndLeaveNotes);