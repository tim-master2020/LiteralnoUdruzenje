import React, { useState } from 'react';
import { withRouter } from 'react-router-dom';
import axios from 'axios';
import { defaultUrl } from '../../backendConfig.js';
import './Global.css';
import withReactContent from 'sweetalert2-react-content';
import Swal from 'sweetalert2';
import CamundaForm from '../CamundaForm.js';
import { Card } from 'react-bootstrap';
import { TaskNameRoutes } from '../../functions/TaskNameRoutes';

const alert = withReactContent(Swal)

const DownloadBook = ({history,updateUser}) => {

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

    function handleSubmit(e) {

        e.preventDefault();
        const returnArray = [];
        formFields.forEach(field => {

            if (field.type.name.includes('enum')) {
                field.value.value = selected.value;
            }
            returnArray.push({ fieldId: field.id, fieldValue: field.value.value });
        });
        console.log(returnArray)
        axios.post(`${defaultUrl}/api/books/submit-approval/${history.location.state.taskId}`, returnArray, options).then(
            (resp) => {
                updateUser();
                console.log(resp.data);
                //alert.fire({text:'Your decision about this book has been submitted.'});

                if(resp.data !== ""){
                    history.push({
                        pathname:`${TaskNameRoutes(resp.data.taskName)}/${resp.data.taskId}`,
                        state: {
                          taskId: resp.data.taskId
                        }
                      });
                }else{
                    history.push('/');
                }
            },
            (resp) => {
                alert.fire({
                    text:'Error occured, please try again.',
                });
            }
        );
    }

    return(
        <div>
            <p className="title">You can download the book and decide do you aprove it or not.</p>
            <Card className='cardHolder'>
                <CamundaForm
                formFields={formFields}
                setformFields={setformFields}
                setShouldSubmit={setShouldSubmit}
                setValidationMessage={setValidationMessage}
                isValid={isValid}
                setIsValid={setIsValid}
                selected={selected}
                onSubmit={(e) => { handleSubmit(e) }}
                setSelected={setSelected}
                />
            </Card>
        </div>
    )
}
export default withRouter(DownloadBook);