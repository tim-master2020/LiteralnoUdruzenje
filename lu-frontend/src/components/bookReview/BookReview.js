import React, { useState } from 'react'
import { defaultUrl } from '../../backendConfig.js';
import axios from 'axios';
import { Form, Button, FormGroup, FormControl, ControlLabel, Col, Card } from "react-bootstrap";
import { withRouter } from 'react-router-dom';
import getUser from '../../functions/UserFunctions.js';
import './BookReview.css';
import CamundaForm from '../CamundaForm.js';

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
                console.log(resp.data);
            },
            (resp) => { alert("Cannot load books."); }
        );
    }, []);

    function downloadBook(e, book) {
        e.preventDefault();
        var name = book.split(".");
        console.log(book);
        console.log(name);
        axios.get(`${defaultUrl}/api/books/download/${name[0]}`,).then(
            (resp) => {
                console.log(resp.data);
            },
            (resp) => { alert("Cannot download this book."); }
        );
    }

    function renderBooks(b) {
        return b.map((book) => {
            return (
                <div onClick={(e) => {downloadBook(e, book)}} className="bookNameDiv">{book}</div>
            );
        })
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
                    onSubmit={(e) => { renderBooks(e) }} 
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