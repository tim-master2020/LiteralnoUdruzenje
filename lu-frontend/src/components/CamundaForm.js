import MultiSelect from "react-multi-select-component";
import validate from "../functions/FormFunctions";
import Select from 'react-select';
import { React, useImperativeHandle, forwardRef } from 'react';
import { Form, Button, Col, Card } from "react-bootstrap";
import './bookReview/BookReview.css';
import { Link } from "@material-ui/core";
import { downloadBook } from '../functions/downloadBook'
import { keys } from "@material-ui/core/styles/createBreakpoints";


const CamundaForm = ({ formFields,
    onSubmit,
    shouldSubmit,
    setShouldSubmit,
    setValidationMessage,
    selected,
    setSelected,
    isValid,
    setIsValid,
    setformFields,
    uploadedFiles,
    setUploadedFiles }) => {

    const onCamundaFormSubmit = (e) =>{
        e.preventDefault();

        formFields.forEach(field => {
            if (!validate(field,field.value.value,setIsValid,isValid,selected)) {
                setValidationMessage(`Input value for field ${field.id} should be`);
            }       
        });

        if (Object.keys(isValid).length > 0) {
            alert("Input values are not valid,please try again.");
        } else {
            onSubmit();
        }
        
    }

    return (
        <Form id="camundaForm" onSubmit={onCamundaFormSubmit}>
            {renderFormFields(formFields)}
            <Button className="submitButton" type="submit" variant="outline-dark" disabled={(!(shouldSubmit === undefined || shouldSubmit === null) ? false : shouldSubmit)}>Submit</Button>
        </Form>
    );

    function updateSelected(e,field){
        setSelected(e);
        //field.value.value = e;
        const values = e.map(element => element.label);
        if (!validate(field,values,setIsValid,isValid,e)) {
            setValidationMessage(`Input value for field ${field.id} should be`);
            if (Object.keys(isValid).length > 0) {
                setShouldSubmit(false);
            } else {
                setShouldSubmit(true);
            }
        }
        console.log('isValid',isValid);
    }

    function renderFormFields(formFields) {
        console.log('form fields', formFields);
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
                if (field.type.name.includes('multiEnum') || field.type.name.includes('multipleEnum')) {
                    return (
                        <div className="selectDiv">
                            Choose
                            <MultiSelect
                                options={initializeOptions(field.type.values)}
                                value={selected}
                                onChange={(e)=>{updateSelected(e,field)}}
                                labelledBy={"Select"}
                                className="multiSelect"
                            />
                            {isValid.hasOwnProperty(`${field.id}`) &&
                                showValidationErrors(field)
                            }
                        </div>
                    );
                }
                if (field.type.name.includes('singleEnum')) {
                    return (
                        <div className="selectDiv">
                            <Form.Label id={field.id} name={field.id}>{field.label}: {field.value.value}</Form.Label>
                            <Select
                                value={selected}
                                onChange={setSelected}
                                options={initializeOptions(field.type.values)}
                            />
                        </div>

                    )
                } if (field.type.name.includes('label')) {
                    return (
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
                if (field.type.name.includes('input_file')) {
                    console.log(field);
                    return (
                        <Form.Group key={field.id} as={Col} className="singleInputField">
                            <Form.Label>{field.label}</Form.Label>
                            <br />
                            <input multiple type="file" id={field.id} name={field.id} onChange={fileSelectedHandler} />
                            {isValid.hasOwnProperty(`${field.id}`) &&
                                showValidationErrors(field)
                            }
                        </Form.Group>);

                } if (field.type.name.includes('textArea')) {
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
                if (field.type.name.includes('button_type')) {
                    return (
                        <Form.Group key={field.id} as={Col} className="singleInputField">
                            <Form.Label className="buttonType">{field.label}</Form.Label>
                            <br />
                        </Form.Group>
                    )
                }
                if (field.type.name.includes('pdfs')) {
                    console.log(initializeOptions(field.type.values));
                    var names = field.defaultValue.replace('[', '').replace(']', '').split(', ');
                    console.log(names);
                    return (
                        names.map(name => {
                            return (
                                <div>
                                    <Form.Label className="titlePdf">{field.label}</Form.Label>
                                    <Form.Label onClick={(e) => {downloadBook(e, name)}} className="bookNameDiv">{name}</Form.Label>
                                    <br/>
                                </div>
                            )
                        })
                    );
                }
                if (field.type.name.includes('input_single')) {
                    return (
                        <Form.Group key={field.id} as={Col} className="singleInputField">
                            <Form.Label>{field.label}</Form.Label>
                            <br />
                            <input type="file" id={field.id} name={field.id} onChange={fileSelectedHandler} />
                        </Form.Group>
                    );
                }
                if (field.type.name.includes('multiFilesDownload')) {
                    return (
                        Object.keys(field.type.values).map((val, k) => {
                            return (
                                <div>
                                    <a onClick={(e) => { downloadBook(e, val) }} className="bookNameDiv">{val}</a>
                                    <br />
                                </div>
                            )
                        })
                    );
                }

                if (field.type.name.includes('multiNotesLabel')) {
                    return (
                        Object.keys(field.type.values).map((val, k) => {
                            return (
                                <div>
                                    <Card>
                                        <Card.Body><b>Note:</b> {val}</Card.Body>
                                    </Card>
                                    <br />
                                </div>
                            )
                        })
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
        };
    }


    function fileSelectedHandler(e) {
        var field = formFields;

        var fileBytes = [];
        var fileNames = [];
        var all = [];
        var array = e.target.files;
        var i;

        for (i = 0; i < array.length; i++) {

            var fileName = "Filename" + array[i].name;
            var reader = new FileReader();

            reader.onload = (em) => {
                all.push(em.target.result);

            }

            all.push(fileName);

            reader.readAsDataURL(e.target.files[i]);
        }
        console.log(fileBytes);

        setUploadedFiles(fileBytes);
        var temp = formFields;
        temp.forEach(field => {
            if (e.target.name === field.id) {
                field.value.value = all;
                validate(field,all,setIsValid,isValid);
            }
        });
        debugger;
        setformFields(temp);
        
        //setShouldSubmit(true);
    }

    function handleChange(e) {
        var temp = formFields;
        debugger;
        temp.forEach(field => {
            if (e.target.name === field.id) {
                if (field.type.name === "boolean") {
                    field.value.value = (field.value.value === true) ? false : true;
                } else {
                    field.value.value = e.target.value;
                    if (!validate(field, field.value.value, setIsValid, isValid)) {
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
                options.push({ value: `${key}`, label: `${value}` })
            }

        }
        return options;
    }

}
export default CamundaForm;


