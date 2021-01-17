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
import { alert } from '../../functions/alertSwal' 

const ReviewPage = ({ history}) => {
    const [ writer, setWriter ] = useState('');
    const [reviews, setReviews] = React.useState('');

    React.useEffect(() => {

        let token = localStorage.getItem('token');
        const options = {
            headers: { 'Authorization': 'Bearer ' + token}
        };

        axios.get(`${defaultUrl}/api/reviews/all`, options).then(
            (resp) => {
                setReviews(resp.data);
                console.log(resp.data);
            },
            (resp) => { alert("Cannot load reviews."); }
        );
    }, []);

    function renderReviews() {
        return(
            reviews.map(review => {
                return(
                    <Card className="reviewCard">
                        <Card.Body className={review.vote ? "cardBodyReview" : "cardBodyComment"}> 
                            <div className="cardLeft">   
                                {review.bookName && <p>Book name: </p>}        
                                {review.vote && <p>Vote: </p>}
                                <p>Comment: </p>
                            </div>
                            <div className="cardRight">
                                {review.bookName && <p>{review.bookName}</p>}
                                {review.vote && <p>{review.vote}</p>}
                                <p>{review.comment}</p>
                            </div> 
                        </Card.Body>
                    </Card>
                )
            })
        )
    }
    

    return (     
        <div className="contentDiv">
            <h3>Reviews</h3>
            { reviews.length > 0 &&
                <div className="reviewsDiv">
                    {renderReviews()}
                </div>
            }
        </div>
       
    );
}
export default withRouter(ReviewPage);