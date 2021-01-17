import React from 'react';
import { withRouter } from 'react-router-dom';
import CamundaForm from '../CamundaForm';
import axios from 'axios';
import { defaultUrl } from '../../backendConfig';
import { Card } from 'react-bootstrap';
import Swal from 'sweetalert2';
import withReactContent from 'sweetalert2-react-content';

const alert = withReactContent(Swal)

const FileAComlpaint = ({ taskId, history }) => {

    const [formFields, setformFields] = React.useState([]);
    const [isValid, setIsValid] = React.useState({});
    const [selected, setSelected] = React.useState([]);
    const [shouldSubmit, setShouldSubmit] = React.useState(true);
    const [validationMessage, setValidationMessage] = React.useState({});

    const options = {
        headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') }
    };

    React.useEffect(() => {
        axios.get(`${defaultUrl}/process/get-form-fields/${history.location.state.taskId}`, options).then(
            (resp) => {
                setformFields(resp.data.formFields);
            },
            (resp) => { alert.fire({
                text:'Error occured please try again',
            }); }
        );
    }, []);

    function submitComplaint(e) {

        e.preventDefault();
        const returnArray = [];
        formFields.forEach(field => {
            returnArray.push({ fieldId: field.id, fieldValue: field.value.value });
        });

        axios.post(`${defaultUrl}/plagiarism/file-complaint/${history.location.state.taskId}`, returnArray, options).then(
            (resp) => {

                alert.fire({
                    title: "Success",
                    text: 'Your file a complaint successfully.',
                    type: "success",
                    button: true
                  });
                  history.push('/');
            },
            (resp) => {
                alert.fire({
                    text:'Error occured please try again',
                });
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
                onSubmit={submitComplaint}
                selected={selected}
                setSelected={setSelected}
                setValidationMessage={setValidationMessage}
                shouldSubmit={shouldSubmit}
                setShouldSubmit={setShouldSubmit}
            />
        </Card>
    )

}
export default withRouter(FileAComlpaint);