import React from 'react';
import { withRouter } from 'react-router-dom';
import CamundaForm from '../CamundaForm';
import axios from 'axios';
import { defaultUrl } from '../../backendConfig.js';

const BetaReader = ({ history }) => {
    
    const [selected,setSelected] =  React.useState([]);
    const [taskId, setTaskId] = React.useState('');

    return (
        <div>
            <CamundaForm
                formFields={history.location.state.formFields}
                onSubmit={(e) => { sendBetaGenres(e) }}
                selected={selected}
                setSelected={setSelected}
            />
        </div>
    )


    function sendBetaGenres(e) {

        e.preventDefault();
        const returnValue = [{ fieldId: 'betaGenres', fieldValue: selected }]
    
        axios.post(`${defaultUrl}/api/users/submit-beta-user/${history.location.state.taskId}`, returnValue).then(
            (resp) => {
                alert('super')
            },
            (resp) => { alert("not good"); }
        );
    }


}
export default withRouter(BetaReader);

//{history.location.state.taskId}