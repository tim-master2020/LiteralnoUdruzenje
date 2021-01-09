import React from 'react';
import { withRouter } from 'react-router-dom';
import axios from 'axios';
import { defaultUrl } from '../../backendConfig.js';
import './Global.css';
import withReactContent from 'sweetalert2-react-content';
import Swal from 'sweetalert2';
import CamundaForm from '../CamundaForm.js';
import { Card } from 'react-bootstrap';
const alert = withReactContent(Swal)

const ComparePlagiats = ({history,updateUser}) => {

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

    console.log('form fileds',formFields);

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
            />
        </Card>
    )
}
export default withRouter(ComparePlagiats);