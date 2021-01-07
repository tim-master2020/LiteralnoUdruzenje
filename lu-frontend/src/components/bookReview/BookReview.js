import React, { useState } from 'react'
import { defaultUrl } from '../../backendConfig.js';
import axios from 'axios';
import { Form, Button, FormGroup, FormControl, ControlLabel, Col, Card } from "react-bootstrap";
import { withRouter } from 'react-router-dom';
import getUser from '../../functions/UserFunctions.js';
import './BookReview.css';
import CamundaForm from '../CamundaForm.js';
import streamSaver from 'streamsaver';
import {validate} from '../../functions/FormFunctions';

const BookReview = ({ history , setLoggedIn, tId}) => {
    const [formFields, setformFields] = React.useState([]);
    const [ writer, setWriter ] = useState('');
    const [ books, setBooks ] = useState([]);
    const [taskId, setTaskId] = React.useState('');
    const [selected,setSelected] =  React.useState([]);
    const [isValid, setIsValid] = React.useState({});
    const [shouldSubmit,setShouldSubmit] = React.useState(true);
    const [validationMessage, setValidationMessage] = React.useState({});

    React.useEffect(() => {
        axios.get(`${defaultUrl}/api/books/book-review/${tId}`,).then(
            (resp) => {
                setBooks(resp.data.names);
                setWriter(resp.data.writer);
                setformFields(resp.data.formFields);
                setTaskId(resp.data.taskId);
            },
            (resp) => { alert("Cannot load books."); }
        );
    }, []);

    function downloadBook(e, book) {
        e.preventDefault();
        console.log(book);
        var url = `${defaultUrl}/api/books/download/${book}`;
        fetch(url, {
            method: 'GET',
        })
        .then(response => {  
        
            const fileStream = streamSaver.createWriteStream(book);   
            const readableStream = response.body;

            // More optimized
            if (readableStream.pipeTo) {
                return readableStream.pipeTo(fileStream);
            }   
        
            window.writer = fileStream.getWriter();
        
            const reader = response.body.getReader();
            const pump = () => reader.read()
                .then(res => res.done
                    ? writer.close()
                    : writer.write(res.value).then(pump));
        
            pump();
        })
        .catch(error => {
            console.log(error);
        });
    }

    function renderBooks(b) {
        return b.map((book) => {
            return (
                <div onClick={(e) => {downloadBook(e, book)}} className="bookNameDiv">{book}</div>
            );
        })
    }

    function submitReview(e) {

        e.preventDefault();   
        const returnValue = [];
        let dataIsValid = true;
        formFields.forEach(field => {

            validate(field,field.value.value,setIsValid,isValid);
            if(Object.keys(isValid).length > 0){
                setValidationMessage(`Input value for field ${field.id} should be`)
                dataIsValid = false; 
            }
            
            if(field.type.name.includes('enum')){
                field.value.value = selected.value;
            }
            returnValue.push({ fieldId: field.id, fieldValue: field.value.value })
        });
        
        
        if(dataIsValid){
            console.log('taskid',taskId);
            console.log(returnValue);

            axios.post(`${defaultUrl}/api/reviews/save-review/${taskId}/${writer}`, returnValue).then(
            (resp) => {
                console.log(resp);
                alert('You left review successfully!')
                history.push('/');
            },
            (resp) => { 
                alert("Something went wrong."); 
            }
        );
    }
    }

    return (     
        <div className="contentDiv">
            <h3>You can download PDF files and rate writer based on them.</h3>
            <div className="reviewDiv">
               <p className="reviewWriter">Writer's username: </p> 
               <p className="reviewWriterName">{writer}</p>
               <p className="reviewWriter">PDFs: </p>
               { books && 
                <a>
                    {renderBooks(books)}
                </a>
               }
            </div>
            <Card className="registrationCard" id="registrationCard">
                <Card.Title></Card.Title>
                <Card.Body>               
                    <CamundaForm
                    formFields={formFields}
                    onSubmit={(e) => { submitReview(e) }} 
                    shouldSubmit={shouldSubmit} 
                    setShouldSubmit={setShouldSubmit}
                    setValidationMessage={setValidationMessage}
                    selected={selected}
                    setSelected={setSelected}
                    setformFields={setformFields}
                    isValid={isValid}
                    setIsValid={setIsValid}
                    />              
                </Card.Body>
            </Card>
        </div>
       
    );
}
export default withRouter(BookReview);