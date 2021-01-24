import React, { useState } from 'react';
import { withRouter } from 'react-router-dom';
import axios from 'axios';
import { defaultUrl } from '../../backendConfig.js';
import './Global.css';
import CamundaForm from '../CamundaForm.js';
import { Card } from 'react-bootstrap';
import { TaskNameRoutes } from '../../functions/TaskNameRoutes';
import {alert} from '../../functions/alertSwal'

const BookReview = ({history,updateUser,type}) => {

    const [formFields, setformFields] = React.useState([]);
    const [isValid, setIsValid] = React.useState({});
    const [shouldSubmit,setShouldSubmit] = React.useState(true);
    const [validationMessage, setValidationMessage] = React.useState({});
    const [selected,setSelected] =  React.useState([]);
    const [taskId,setTaskId] =  React.useState(history.location.state.taskId);

    React.useEffect(() => {
        const options = {
            headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') }
        };

        axios.get(`${defaultUrl}/process/get-form-fields/${history.location.state.taskId}`, options).then(
            (resp) => {
                setformFields(resp.data.formFields);
            },
            (resp) => { alert("error getting form fields,try again"); }
        );
    }, []);

    function handleSubmit(e) {

        const options = {
            headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') }
        };
        
        //e.preventDefault();
        const returnArray = [];
        formFields.forEach(field => {

            if (field.type.name.includes('enum')) {
                field.value.value = selected.value;
            }
            returnArray.push({ fieldId: field.id, fieldValue: field.value.value });
        });
        console.log(returnArray)
        let reviewType = '';
        if (type === 'lector') {
            reviewType = 'lector-review';
        } else if (type === 'mainEditor') {
            reviewType = 'main-editor-review';
        } else if (type === 'editor') {
            reviewType = 'editor-review';
        } else if (type === 'printBook') {
            reviewType = 'print-book';
        }
        console.log(options);
        axios.post(`${defaultUrl}/api/books/${reviewType}/${history.location.state.taskId}`, returnArray, options).then(
            (resp) => {
                updateUser();
                alert('Your decision is successfully submited!');
                // if(resp.data !== "" && resp.data.taskName === "PrintBook"){
                //     history.push({
                //         pathname:`${TaskNameRoutes(resp.data.taskName)}/${resp.data.taskId}`,
                //         state: {
                //           taskId: resp.data.taskId
                //         }
                //       });
                // }else{
                history.push('/');
                //}

            },
            () => {
                alert('Error occured.');
            }
        );
    }

    return(
        <div>
            { type==="printBook" && 
                <p className="title">This book has been approved through all checks. By clicking on submit, book will be
                sent to printing and indexing.</p>
            }
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
export default withRouter(BookReview);