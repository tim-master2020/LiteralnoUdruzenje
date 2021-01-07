
import MultiSelect from "react-multi-select-component";
import maintenance from '../icons/maintenance.svg';
import reading from '../icons/readingbook.svg';
import { React, useImperativeHandle, forwardRef } from 'react';
import { Form, Button, Col } from "react-bootstrap";
import {validate} from '../functions/FormFunctions.js';
import Select from 'react-select';


const CamundaForm = ({ formFields,
    onSubmit,
    shouldSubmit,
    setShouldSubmit,
    setValidationMessage,
    selected,
    setSelected,
    isValid,
    setIsValid,
    setformFields}) => {

    return (
        <Form id="camundaForm" onSubmit={onSubmit}>
            {renderFormFields(formFields)}
            <Button className="submitButton" type="submit" variant="outline-dark" disabled={(!(shouldSubmit === undefined || shouldSubmit === null) ? false: shouldSubmit)}>Submit</Button>
        </Form>
    );


    function renderFormFields(formFields) {
        if (formFields !== undefined && formFields.length > 0) {
            return formFields.map((field) => {
                if (field.type.name === "boolean") {
                    return (<div className="checkBoxField">
                        {field.label}
                        <br />
                        <input key={field.id} type="checkbox" id={field.id} name={field.id} onChange={handleChange} defaultValue="false" />
                    </div>
                    );
                }
                if (field.type.name.includes('multiEnum')) {
                    return (
                        <div className="selectDiv">
                            Choose
                            <MultiSelect
                                options={initializeOptions(field.type.values)}
                                value={selected}
                                onChange={setSelected}
                                labelledBy={"Select"}
                                className="multiSelect"
                            />
                        </div>
                    );
                }
                if(field.type.name.includes('singleEnum')){
                    return(
                        <div className="selectDiv">  
                        <Select
                            value={selected}
                            onChange={setSelected}
                            options={initializeOptions(field.type.values)}
                        />
                        </div>
                       
                    )
                }if(field.type.name.includes('label')){
                    return(
                        <Form.Group key={field.id} as={Col} className="singleInputField">
                            <Form.Label id={field.id} name={field.id}><b>{field.label}:</b> {field.value.value}</Form.Label>
                         </Form.Group>
                    // <label id={field.id} name={field.id}>{field.value.value}</label>
                       
                    )
                }

                if (field.type.name.includes('password')) {
                    return (
                        <Form.Group key={field.id} as={Col} className="singleInputField">
                            <Form.Label>{field.label}</Form.Label>
                            <Form.Control type="password" id={field.id} name={field.id} onChange={handleChange} />
                            {isValid.hasOwnProperty(`${field.id}`) &&
                                showValidationErrors(field)
                            }
                         </Form.Group>
                    );
                }
                if (field.type.name.includes('email')) {
                    return (
                        <Form.Group key={field.id} as={Col} className="singleInputField">
                            <Form.Label>{field.label}</Form.Label>
                            <Form.Control type="email" id={field.id} name={field.id} onChange={handleChange} />
                            {isValid.hasOwnProperty(`${field.id}`) &&
                                showValidationErrors(field)
                            }
                         </Form.Group>
                    );
                }
                if (field.type.name.includes('textArea')) {
                    return (
                        <Form.Group key={field.id} as={Col} className="singleInputField">
                            <Form.Label>{field.label}</Form.Label>
                            <textarea class="form-control" id={field.id} rows="3" name={field.id} onChange={handleChange}></textarea>
                            {isValid.hasOwnProperty(`${field.id}`) &&
                                showValidationErrors(field)
                            }
                         </Form.Group>
                    );
                }
                if (field.type.name.includes('enum')) {
                    console.log(initializeOptions(field.type.values));
                    return (
                        <Form.Group key={field.id} as={Col} className="singleInputField">
                            <Form.Label>{field.label}</Form.Label>
                            <Select
                            value={selected}
                            onChange={setSelected}
                            options={initializeOptions(field.type.values)}
                            />
                         </Form.Group>
                    );
                }
                else {
                    return (
                        <Form.Group key={field.id} as={Col} className="singleInputField">
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

    function handleChange(e) {
        var temp = formFields;
        temp.forEach(field => {
            if (e.target.name === field.id) {
                if (field.type.name === "boolean") {
                    field.value.value = (field.value.value === true) ? false : true;
                } else {
                    field.value.value = e.target.value;
                    if (!validate(field, field.value.value,setIsValid,isValid)) {
                        setValidationMessage(`Input value for field ${field.id} should be`);
                        if (Object.keys(isValid).length > 0) {
                            setShouldSubmit(false);
                        } else {
                            setShouldSubmit(true);
                        }
                    }
                }
            }
        });
        setformFields(temp);
    };

    function showValidationErrors(field) {
        debugger;
        if (isValid.hasOwnProperty(`${field.id}`)) {
            return (
                <div style={{ color: 'red' }}>
                    {isValid[`${field.id}`]}
                </div>
            )
        }
    }


    function initializeOptions(fields) {
        let options = [];
        if (fields !== null && fields !== undefined) {
            for (const [key, value] of Object.entries(fields)) {
                options.push({ value: `${value}`, label: `${value}` })
            }

        }
        console.log('options',options);
        return options;
    }
}
export default CamundaForm;


