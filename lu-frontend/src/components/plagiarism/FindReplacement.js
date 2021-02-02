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
    const [maxToChoose,setMaxToChoose] = React.useState(undefined);

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
        
        axios.get(`${defaultUrl}/plagiarism/get-max-replacement/${history.location.state.taskId}`, options).then(
            (resp) => {
                    setMaxToChoose(resp.data);
            },
            (resp) => { alert("error getting form fields,try again"); }
        );


    }, []);

    const submitReplacements = (e) => {

        //e.preventDefault();
        const returnArray = [];

        formFields.forEach(field => {
            if (field.type.name.includes('multiEnum_')) {
                selected.forEach(s => {
                    returnArray.push({ fieldId: s.value, fieldValue: s.label });
                })
            }
        });

        if(maxToChoose !== undefined){
            if(selected.length !== maxToChoose){
                alert(`You can only choose ${maxToChoose} editors for replacement`);
                return;
            }
        }
        
        axios.post(`${defaultUrl}/plagiarism/submit-replacements/${history.location.state.taskId}`, returnArray, options).then(
            (resp) => {
                updateUser();
                alert('You submitted replacement editors successfully.');
                history.push('/');
            },
            (resp) => {
                alert("Error occured.");
            }
        );
    }

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
                onSubmit={submitReplacements}
                />
            </Card>
        </div>
    )

}
export default withRouter(FindReplacement);