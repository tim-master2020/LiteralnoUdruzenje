import React from 'react';
import { withRouter } from 'react-router-dom';
import CamundaForm from '../CamundaForm';
import axios from 'axios';
import { defaultUrl } from '../../backendConfig';
import { Card } from 'react-bootstrap';
import Swal from 'sweetalert2';
import withReactContent from 'sweetalert2-react-content';
import './Global.css';

const alert = withReactContent(Swal)

const PublishBookGeneralData = ({ taskId, history }) => {

    const [formFields, setformFields] = React.useState([]);
    const [isValid, setIsValid] = React.useState({});
    const [validationMessage, setValidationMessage] = React.useState({});
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

    function submitGeneralBookData(e) {

        e.preventDefault();
        const returnArray = [];
        formFields.forEach(field => {

            if (field.type.name.includes('singleEnum')) {
                field.value.value = selected;
            }
            returnArray.push({ fieldId: field.id, fieldValue: field.value.value });
        });

        axios.post(`${defaultUrl}/api/books/save-general-book-data/${history.location.state.taskId}`, returnArray, options).then(
            (resp) => {

                alert.fire({
                    title: "Success",
                    text: 'Your book is now under review.When review is finished you will be notified via email.Thank you, eBook team.',
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
                onSubmit={submitGeneralBookData}
                setValidationMessage={setValidationMessage}
                selected={selected}
                setSelected={setSelected}
                shouldSubmit={shouldSubmit}
                setShouldSubmit={setShouldSubmit}
            />
        </Card>
    )

}
export default withRouter(PublishBookGeneralData);