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
          flexGrow: 1,
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
      }));

    const classes = useStyles();

    return (
           <AppBar elevation={0} position="static">
            <Toolbar  className={classes.toolbar}> 
                <Typography variant="h6" className={classes.title} onClick={() => { history.push(`/`);}}>
                    <img alt="logo" src={image} style={{borderRadius:'5px',height:'40px',width:'40px',marginRight:'1%',cursor:'pointer'}} ></img>
                eBook
                </Typography>
                <div className="appbarButton" 
                     onClick={() => {history.push(`/login`);}}
                >Login</div>
                <div className="appbarButton" 
                    onClick={() => { history.push(`/registration`);}}
                >Register</div>
            </Toolbar>
            <Divider className={classes.divider}/>
            </AppBar>
    );
}
export default withRouter(Appbar);