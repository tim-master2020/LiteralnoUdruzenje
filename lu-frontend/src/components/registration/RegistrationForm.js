import React from 'react'
import { defaultUrl } from '../../backendConfig.js';
import axios from 'axios';
import { Form, Button, FormGroup, FormControl, ControlLabel, Col, Card } from "react-bootstrap";
import './RegistrationForm.css';
import MultiSelect from "react-multi-select-component";
import maintenance from '../../icons/maintenance.svg';
import reading from '../../icons/readingbook.svg';
import { withRouter } from 'react-router-dom';

const RegistrationForm = ({history}) => {

    const [formFields, setformFields] = React.useState([]);
    const [validator, setValidator] = React.useState({});
    const [validationMessage, setValidationMessage] = React.useState({});
    const [isValid, setIsValid] = React.useState({});

    const [selected, setSelected] = React.useState([]);
    const [selectedBeta, setSelectedBeta] =React.useState([]);
   
    const [betaGenreFields, setBetaGenreFields] = React.useState({});
    const [betaFieldsAvaliable,setBetaFieldsA] = React.useState(false);

    const [shouldSubmit,setShouldSubmit] = React.useState(true);

    const [taskId, setTaskId] = React.useState('');
    React.useEffect(() => {
        axios.get(`${defaultUrl}/api/users/reg-task-user`,).then(
            (resp) => {
                setformFields(resp.data.formFields);
                setTaskId(resp.data.taskId);
            },
            (resp) => { alert("error getting form fields,try again"); }
        );
    }, []);

    const validate = (field, value) => {
        field.validationConstraints.forEach(constraint => {

            if (constraint.name === 'required') {
                if (value === null || value === undefined || value === null || value === '') {
                    setIsValid(isValid[field.id] = (`${field.id} is required`));
                }else{
                    delete isValid[`${field.id}`];;
                    setIsValid(isValid);
                }
            }


            if (constraint.configuration !== null) {
                
                if(value !== undefined && value !== null && value !== ''){
                    if (constraint.name === 'minlength' && field.type.name === 'string') {
                        if (value.length < constraint.configuration) {
                            setIsValid(isValid[field.id] = (`${field.id} should have at least ${constraint.configuration} characters`));
                        }else{
                           delete isValid[`${field.id}`];
                           setIsValid(isValid);
                        }
                    }

                    if (constraint.name === 'maxlength' && field.type.name === 'string') {
                        if (value.length > constraint.configuration) {
                            setIsValid(isValid[field.id] = (`${field.id} should have maximum ${constraint.configuration} characters`));
                        }else{
                            delete isValid[`${field.id}`];
                            setIsValid(isValid);
                        }
                    }

                    if (constraint.name === 'min' && field.type.name === 'long') {
                        if (value.length < constraint.configuration) {
                            setIsValid(isValid[field.id] = (`${field.id} should have at least ${constraint.configuration} digits`));
                        }else{
                            delete isValid[`${field.id}`];;
                            setIsValid(isValid);
                        }
                    }

                    if (constraint.name === 'max' && field.type.name === 'long') {
                        if (value.length > constraint.configuration) {
                            setIsValid(isValid[field.id] = (`${field.id} should have maximum ${constraint.configuration} digits`));
                        }else{
                            delete isValid[`${field.id}`];
                            setIsValid(isValid);
                        }
                    }

                    if (constraint.name === 'readonly') {
                        if (value !== null || value !== undefined || value !== null || value !== '') {
                            setIsValid(isValid[field.id] = (`${field.id} is readonly`));
                    }else{
                        delete isValid[`${field.id}`];
                        setIsValid(isValid);
                    }
                }
            }
        }
        });
        return setIsValid(isValid);
    }

    const handleChange = e => {
        var temp = formFields;
        temp.forEach(field => {
            if (e.target.name === field.id) {
                if (field.id === "betaReader") {
                    field.value.value = (field.value.value === true) ? false : true;
                } else {
                    field.value.value = e.target.value;
                    if (!validate(field, field.value.value)) {
                        setValidationMessage(`Input value for field ${field.id} should be`);
                        if(Object.keys(isValid).length > 0){
                            setShouldSubmit(false);
                        }else{
                            setShouldSubmit(true);
                        }
                    }
                }
            }
        });
        setformFields(temp);
    };

    function SendRegisterRequest(e) {

        e.preventDefault();
        const returnValue = [];
        let dataIsValid = true;
        formFields.forEach(field => {

            validate(field,field.value.value);
            if(Object.keys(isValid).length > 0){
                setValidationMessage(`Input value for field ${field.id} should be`)
                dataIsValid = false;
                
            }
            field.value.value = (field.id === "betaReader" && field.value.value === null) ? false : field.value.value;
            if(field.id === 'multiEnum_genres'){
                field.value.value = selected;
            }
            returnValue.push({ fieldId: field.id, fieldValue: field.value.value })
        });
        
        
        if(dataIsValid){
            axios.post(`${defaultUrl}/api/users/submit-general-data/${taskId}`, returnValue).then(
            (resp) => {
                console.log(resp);
                if (resp.data !== "") {
                    setBetaGenreFields(resp.data.formFields[0].type.values);
                    setBetaFieldsA(true);
                    setShouldSubmit(false);
                    setTaskId(resp.data.taskId);
                    var element = document.getElementById("registrationCard");
                    var form = document.getElementById("registerForm");
                    element.classList.add("moveregistrationCard");
                    form.classList.add("readOnlyFields");                   
                }else{
                    history.push(`/login`);
                }
            },
            (resp) => { alert("not registered"); }
        );
    }
    }

    function sendBetaGenres(e) {

        e.preventDefault();
        const returnValue = [{fieldId:'betaGenres',fieldValue:selectedBeta}]
        console.log('beta genres return value',returnValue);
        axios.post(`${defaultUrl}/api/users/submit-beta-user/${taskId}`, returnValue).then(
            (resp) => {
                alert('super')
            },
            (resp) => { alert("not good"); }
        );
    }

    const showValidationErrors = (field) => {

        if(isValid.hasOwnProperty(`${field.id}`)){
        return (
            <div style={{ color: 'red'}}>
               {isValid[`${field.id}`]}
            </div>    
        )
    }
    }

    const renderFormFields = (formFields) => {
        if (formFields !== undefined && formFields.length > 0) {
            return formFields.map((field) => {

                if (field.type.name === "boolean") {

                   return ( <div className="checkBoxField">
                        {field.label}
                        <br />
                        <input key={field.id} type="checkbox" id={field.id} name={field.id} onChange={handleChange} defaultValue="false" />
                    </div>
                   );

                }
                if (field.id === 'multiEnum_genres') {
                    return(
                    <div className="selectDiv">
                        Select genres
                        <MultiSelect
                          options={initalizeGenreOptions(field.type.values)}
                          value={selected}
                          onChange={setSelected}
                          labelledBy={"Select"}
                          className="multiSelect"
                        />
                      </div>
                      );
                }
                else {

                    return (<Form.Group key={field.id} as={Col} className="singleInputField">
                        <Form.Label>{field.label}</Form.Label>
                        <Form.Control type={field.type.name} id={field.id} name={field.id} onChange={handleChange} />

                        {isValid.hasOwnProperty(`${field.id}`) &&
                            showValidationErrors(field)
                        }
                    </Form.Group>
                    );

                }
            }
            )
        }
    }

    function initalizeGenreOptions(fields) {

        let genreOptions = [];
        if( fields !== null && fields !== undefined){
        for (const [key, value] of Object.entries(fields)) {
            genreOptions.push({value: `${value}`,label:`${value}`})
          }
        
        }
        return genreOptions;
    }

    const renderChoosenBetaGenres = () => {
        console.log(selectedBeta);
        return selectedBeta.map(function(item, i){
            return <p className="genres" key={i}><b>{item.label}</b></p>
          });

    }
    
    return (
        <div className="contentDiv">
        <Card className="registrationCard" id="registrationCard">
            <Card.Title>
            </Card.Title>
            <Card.Body>
                <Form id="registerForm" onSubmit={(e) => { SendRegisterRequest(e) }}>
                    {renderFormFields(formFields)}
                    <Button className="submitButton" type="submit" variant="outline-dark" disabled={!shouldSubmit}>Submit</Button>

                </Form>
                { betaFieldsAvaliable && 
                    <img style={{height:'inherit',width:'inherit'}} src={reading}/>
                }
            </Card.Body>
        </Card>
        { betaFieldsAvaliable  &&

            <Card className="betaReaderCard">
            <Card.Title className="betaReaderCardTitle">Hello beta reader</Card.Title>
            <Card.Body>
                <Form onSubmit={(e) => { sendBetaGenres(e) }}>
                <div className="avatarDiv">
                <img alt="Genre" src={maintenance} style={{height:'30px',width:'30px',marginRight:'2%'}}/>
                <p>Please choose genres</p>
                </div>
                <div className="betaReaderSelectDiv">
                <MultiSelect
                    options={initalizeGenreOptions(betaGenreFields)}
                    value={selectedBeta}
                    onChange={setSelectedBeta}
                    labelledBy={"Select"}
                    className="betaReaderSelect"
                />
            </div>

            { selectedBeta.length > 0 &&
            <Card className="genresCard">
                {renderChoosenBetaGenres()}
            </Card>
        }
        <Button variant="primary" type="submit" className="finishButton">Finish</Button>
        </Form>
        </Card.Body>
            </Card>
        }
        </div>
    );
}
export default withRouter(RegistrationForm);

