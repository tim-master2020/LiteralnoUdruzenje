import React from 'react';
import { withRouter } from 'react-router-dom';
import axios from 'axios';
import { defaultUrl } from '../../backendConfig.js';
import CamundaForm from '../CamundaForm.js';
import { Card } from 'react-bootstrap';
import Swal from 'sweetalert2';
import withReactContent from 'sweetalert2-react-content';
import { TaskNameRoutes } from '../../functions/TaskNameRoutes.js';
import './Global.css'

const alert = withReactContent(Swal)

const ReviewBookGeneral = ({history,updateUser}) => {

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

    const submitReview = (e) => {

            //e.preventDefault();
            const returnArray = [];
            formFields.forEach(field => {
    
                if (field.type.name.includes('enum')) {
                    field.value.value = selected;
                }
                returnArray.push({ fieldId: field.id, fieldValue: field.value.value });
            });
    
            axios.post(`${defaultUrl}/api/books/save-general-book-data-review/${history.location.state.taskId}`, returnArray, options).then(
                (resp) => {
                    updateUser();
                    alert.fire({
                        title: "Success",
                        text: 'Your review has been submited',
                        type: "success",
                        button: true
                      });

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
                        text:'Error occured please try again',
                    });
                }
            );
        }

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
        onSubmit={(e) => { submitReview(e) }}
        />
        </Card>
    )
}
export default withRouter(ReviewBookGeneral);

{/* <CamundaForm
formFields={formFields}
onSubmit={(e) => { SendRegisterRequest(e) }} 
shouldSubmit={shouldSubmit} 
setShouldSubmit={setShouldSubmit}
setValidationMessage={setValidationMessage}
selected={selected}
setSelected={setSelected}
setformFields={setformFields}
isValid={isValid}
setIsValid={setIsValid}
/>        */}