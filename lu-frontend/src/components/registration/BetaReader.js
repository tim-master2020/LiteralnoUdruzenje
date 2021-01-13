import React from 'react';
import { withRouter } from 'react-router-dom';
import CamundaForm from '../CamundaForm';
import axios from 'axios';
import { defaultUrl } from '../../backendConfig.js';
import { Card } from 'react-bootstrap';
import './RegistrationForm.css'
import { alert } from '../../functions/alertSwal' 

const BetaReader = ({ history }) => {
    
    const [selected,setSelected] =  React.useState([]);
    const [taskId, setTaskId] = React.useState('');

    return (
        <Card className="cardHolder">
            <CamundaForm
                formFields={history.location.state.formFields}
                onSubmit={(e) => { sendBetaGenres(e) }}
                selected={selected}
                setSelected={setSelected}
            />
        </Card>
    )


    function sendBetaGenres(e) {

        e.preventDefault();
        const returnValue = [{ fieldId: 'betaGenres', fieldValue: selected }]
    
        axios.post(`${defaultUrl}/api/users/submit-beta-user/${history.location.state.taskId}`, returnValue).then(
            (resp) => {
                history.push('/');
            },
            (resp) => { alert("not good"); }
        );
    }


}
export default withRouter(BetaReader);

//{history.location.state.taskId}