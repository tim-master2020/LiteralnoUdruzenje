import React from 'react';
import { Card } from 'react-bootstrap';
import { withRouter } from 'react-router-dom';
import axios from 'axios';
import { defaultUrl } from '../../backendConfig.js';
import CamundaForm from '../CamundaForm.js';
import Swal from 'sweetalert2';
import withReactContent from 'sweetalert2-react-content';
import './Global.css'

const alert = withReactContent(Swal)

const DeclineExplanation = ({history,updateUser}) => {

    const [formFields, setformFields] = React.useState([]);
    const [isValid, setIsValid] = React.useState({});
    const [shouldSubmit,setShouldSubmit] = React.useState(true);
    const [validationMessage, setValidationMessage] = React.useState({});
    const [selected,setSelected] =  React.useState([]);

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

    function submitExplanation(e) {

        e.preventDefault();
        const returnArray = [];
        formFields.forEach(field => {

            if (field.type.name.includes('singleEnum')) {
                field.value.value = selected;
            }
            returnArray.push({ fieldId: field.id, fieldValue: field.value.value });
        });

        axios.post(`${defaultUrl}/api/books/submit-explanation/${history.location.state.taskId}`, returnArray, options).then(
            (resp) => {
                updateUser();
                alert.fire({text:'Explanation submited'});
                history.push('/');
            },
            (resp) => {
                alert.fire({
                    text:'Error occured please try again',
                });
            }
        );
    }

    return(
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
        onSubmit={(e) => { submitExplanation(e) }}  
        />
        </Card>

    )

}
export default withRouter(DeclineExplanation);