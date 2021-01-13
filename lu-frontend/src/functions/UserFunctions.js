import React from 'react';
import axios from 'axios';
import { defaultUrl } from '../backendConfig';

export default function getUser(setLoggedIn) {
    if (localStorage.getItem('token')) {
        const options = {
            headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') }
        };

        axios.get(`${defaultUrl}/auth/user`, options).then(
            (resp) => {
                setLoggedIn(resp.data);
            },
            (resp) => {
                alert('error getting logged in user data');
                setLoggedIn(undefined);
            }
        );
    }
}