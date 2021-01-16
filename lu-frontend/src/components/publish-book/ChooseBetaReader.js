import React from 'react';
import { withRouter } from 'react-router-dom';
import CamundaForm from '../CamundaForm';
import axios from 'axios';
import { defaultUrl } from '../../backendConfig';
import { Card } from 'react-bootstrap';
import './Global.css';
import {alert} from '../../functions/alertSwal';

const ChooseBetaReader = ({ taskId, history,updateUser }) => {

    const [formFields, setformFields] = React.useState([]);
    const [isValid, setIsValid] = React.useState({});
    const [selected, setSelected] = React.useState([]);
    const [shouldSubmit, setShouldSubmit] = React.useState(true);

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

    function choose(e) {

        e.preventDefault();
        var usernames = [];
        formFields.forEach(field => {

            if (field.type.name.includes('multiEnum_betas')) {
                selected.forEach(s => {
                    usernames.push({ fieldId: s.value, fieldValue: s.label });
                })
            }
        });

        console.log(usernames);

        axios.post(`${defaultUrl}/api/betareaders/choose-beta-reader/${history.location.state.taskId}`, usernames, options).then(
            (resp) => {
                updateUser();

                alert("You successfully selected the Beta readers.");
                history.push('/');
            },
            (resp) => {
                alert('Error occured please try again');
                history.push('/');

            }
        );
    }

    return (
        <Card className='cardHolder'>
            <CamundaForm
                id="camundaForm"
                formFields={formFields}
                setformFields={setformFields}
                isValid={isValid}
                setIsValid={setIsValid}
                onSubmit={choose}
                selected={selected}
                setSelected={setSelected}
                shouldSubmit={shouldSubmit}
                setShouldSubmit={setShouldSubmit}
            />
        </Card>
    )

}
export default withRouter(ChooseBetaReader);