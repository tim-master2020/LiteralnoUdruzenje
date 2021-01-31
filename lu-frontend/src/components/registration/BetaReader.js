import React from 'react';
import { withRouter } from 'react-router-dom';
import CamundaForm from '../CamundaForm';
import axios from 'axios';
import { defaultUrl } from '../../backendConfig.js';
import { Card } from 'react-bootstrap';
import './RegistrationForm.css'
import { alert } from '../../functions/alertSwal';


const BetaReader = ({ history }) => {
    
    const [formFields, setformFields] = React.useState([]);
    const [validationMessage, setValidationMessage] = React.useState({});
    const [selected,setSelected] =  React.useState([]);
    const [isValid, setIsValid] = React.useState({});
    const [taskId, setTaskId] = React.useState('');
    const [shouldSubmit,setShouldSubmit] = React.useState(true);

    const options = {
        headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') }
    };

    React.useEffect(() => {
        axios.get(`${defaultUrl}/process/get-form-fields/${history.location.state.taskId}`, options).then(
            (resp) => {
                setformFields(resp.data.formFields);
            },
            (resp) => { alert('Error occured please try again')
        }
        );
    }, []);

    return (
        <Card className="cardHolder">
            <CamundaForm
                formFields={formFields}
                onSubmit={sendBetaGenres}
                selected={selected}
                setSelected={setSelected}
                shouldSubmit={shouldSubmit} 
                setShouldSubmit={setShouldSubmit}
                setValidationMessage={setValidationMessage}
                setformFields={setformFields}
                isValid={isValid}
                setIsValid={setIsValid}
            />
        </Card>
    )


    function sendBetaGenres(e) {

        var returnArray = [];

        formFields.forEach(field => {
            if (field.type.name.includes('multiEnum_')) {
                field.value.value = selected;
            }
            returnArray.push({ fieldId: field.id, fieldValue: field.value.value });
        });

    
        axios.post(`${defaultUrl}/api/users/submit-beta-user/${history.location.state.taskId}`, returnArray).then(
            (resp) => {
                alert('We have sent you email with conformation link.')
                history.push('/');
            },
            (resp) => { alert("not good"); }
        );
    }


}
export default withRouter(BetaReader);

//{history.location.state.taskId}