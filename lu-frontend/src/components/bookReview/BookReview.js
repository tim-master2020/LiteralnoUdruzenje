import React, { useState } from 'react'
import { defaultUrl } from '../../backendConfig.js';
import axios from 'axios';
import { Form, Button, FormGroup, FormControl, ControlLabel, Col, Card } from "react-bootstrap";
import { withRouter } from 'react-router-dom';
import getUser from '../../functions/UserFunctions.js';
import './BookReview.css';

const BookReview = ({ history , setLoggedIn}) => {
    const [ books, setBooks ] = useState([]);

    React.useEffect(() => {
        axios.get(`${defaultUrl}/api/books/all`,).then(
            (resp) => {
                setBooks(resp.data);
            },
            (resp) => { alert("Cannot load books."); }
        );
    }, []);
    console.log(books);

    function renderBooks(b) {
        return b.map((book) => {
            return (
            <Card>
                <Card.Title>
                    {book.name}
                </Card.Title>
                <Card.Body>
                    {book.genre}
                    <br />
                    {book.authorNames}
                </Card.Body>
            </Card>
            );
        })
    }

    return (     
        <div>
            <h2>You can review books here.</h2>
            { books.length &&
            <div className='booksDiv'>
                {renderBooks(books)}
            </div>
            }
        </div>
       
    );
}
export default withRouter(BookReview);