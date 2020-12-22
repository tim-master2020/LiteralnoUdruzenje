import React from "react";
import { withRouter } from 'react-router-dom';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import image from '../../icons/book.svg';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import { Divider, Button } from "@material-ui/core";
import './Appbar.css';

const Appbar = ({history}) => {
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
           <AppBar elevation={0} position="static">
            <Toolbar  className={classes.toolbar}> 
                <Typography variant="h6" className={classes.title} onClick={() => { history.push(`/`);}}>
                    <img alt="logo" src={image} className="logo" ></img>
                    eBook
                </Typography>
                <div className="loginRegButtons">
                    
                    <div className= {classes.appbarButton}
                        onClick={() => {history.push(`/login`);}}
                    >Login</div>
                    
                    <div className={classes.appbarButton}
                        onClick={() => { history.push(`/registration`);}}
                    >Register</div>
                   
                </div>
            </Toolbar>
            <Divider className={classes.divider}/>
            </AppBar>
    );
}
export default withRouter(Appbar);