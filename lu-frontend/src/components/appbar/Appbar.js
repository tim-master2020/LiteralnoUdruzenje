import React from "react";
import { withRouter } from 'react-router-dom';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import image from '../../icons/book.svg';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import { Divider, Button } from "@material-ui/core";
import './Appbar.css';
import LoggedInAppbar from "./LoggedInAppbar";
import LoggedOutAppbar from "./LoggOutAppbar";

const Appbar = ({loggedInUser,setLoggedIn}) => {
    const useStyles = makeStyles((theme) => ({
        root: {
          flexGrow: 1,
        },
        menuButton: {
          marginRight: theme.spacing(2),
        },
        title: {
          flexGrow: 0,
          color:'gray',
          cursor:'pointer',
        },
        toolbar: {
            backgroundColor:'white',
            boxShadow:0
        },
        divider:{
            backgroundColor:'gray'
        },
        appbarButton:{
            flexGrow:1,
            color:'gray',
            borderStyle: 'solid',
            borderColor:'gray',
            borderWidth:'0.5px',
            marginTop:'3px',
            borderRadius:'5px',
            padding:'3px',
            marginLeft:'7px',
            cursor: 'pointer'
        }
      }));

    const classes = useStyles();

    return (
           <div>
                { loggedInUser !== undefined &&
                <LoggedInAppbar loggedInUser = {loggedInUser} setLoggedIn={setLoggedIn}/>
                }
                 { loggedInUser === undefined &&
                <LoggedOutAppbar/>
                }
            </div>
    );
}
export default (Appbar);