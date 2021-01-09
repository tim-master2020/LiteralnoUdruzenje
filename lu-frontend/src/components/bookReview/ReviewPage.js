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

const ReviewPage = ({ history}) => {
    const [ writer, setWriter ] = useState('');
    const [reviews, setReviews] = React.useState('');

    React.useEffect(() => {

        let token = localStorage.getItem('token');
        const options = {
            headers: { 'Authorization': 'Bearer ' + token}
        };
        console.log(options);
        axios.get(`${defaultUrl}/api/reviews/all`, options).then(
            (resp) => {
                console.log(resp.data);
                setReviews(resp.data);
            },
            (resp) => { alert("Cannot load reviews."); }
        );
    }, []);

    

    return (     
        <div className="contentDiv">
            <h3>Reviews</h3>
            <Card className="registrationCard" id="registrationCard">
                <Card.Title></Card.Title>
                <Card.Body>               
            
                </Card.Body>
            </Card>
        </div>
       
    );
}
export default withRouter(ReviewPage);