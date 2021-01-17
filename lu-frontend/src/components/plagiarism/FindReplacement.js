import React from 'react';
import { withRouter } from 'react-router-dom';
import CamundaForm from '../CamundaForm';
import axios from 'axios';
import { defaultUrl } from '../../backendConfig';
import { Card } from 'react-bootstrap';
import { alert } from '../../functions/alertSwal';

const FindReplacement = ({history,updateUser}) => {

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
                console.log(resp.data.formFields);
            },
            (resp) => { alert("error getting form fields,try again"); }
        );
    }, []);

    return(
        <div>
            <p className="title">Please choose replacement editors to review plagiats.</p>
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
                />
            </Card>
        </div>
    )

}
export default withRouter(FindReplacement);