import React, { useState } from "react";
import Drawer from '@material-ui/core/Drawer';
import Button from '@material-ui/core/Button';
import MenuIcon from '@material-ui/icons/Menu';
import Divider from '@material-ui/core/Divider';
import { withRouter } from 'react-router-dom';
import { makeStyles, useTheme } from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';
import clsx from 'clsx';
import IconButton from '@material-ui/core/IconButton';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import { SidebarList } from './SidebarList';
import { styling } from './SidebarStyling';
import { AppBar, Toolbar } from "@material-ui/core";
import { Carousel } from 'react-bootstrap';
import photo1 from '../../icons/photo1.svg';
import photo2 from '../../icons/photo2.svg';
import photo3 from '../../icons/photo3.svg';


const LoggedOutHomepage = ({history }) => {
    const [isOpen, setOpen] = useState(false);

    const handleDrawerToggle = () => {
        setOpen(!isOpen);
    };

    const theme = useTheme();
    const useStyles = makeStyles((theme) => (styling(theme)));
    const classes = useStyles();

    return (
        <div className={classes.root}>
            <CssBaseline />
            <AppBar position="fixed" className={clsx(classes.appBar, { [classes.appBarShift]: isOpen })}>
                <Toolbar className={classes.toolbar}>
                    <div style={{ width: '100%' }}></div>
                    <Button onClick={() => {history.push(`/registration`);}}>register as reader</Button>
                    <Button onClick={() => {history.push(`/registration/writer`);}}>register as writer</Button>
                    <Button onClick={() => {history.push(`/login`);}}>login</Button>
                </Toolbar>
            </AppBar>
            <MenuIcon />

            <main className={clsx(classes.content, { [classes.contentShift]: isOpen })}>
                <Carousel style={{heigth:'30%',width:'40%',marginLeft:'30%',marginTop:'2%'}}>
                    <Carousel.Item>
                        <img className="d-block w-100"src={photo1} style={{height:'inherit'}}/>
                    </Carousel.Item>
                    <Carousel.Item>
                        <img className="d-block w-100"src={photo2} style={{height:'inherit'}}/>
                    </Carousel.Item>
                    <Carousel.Item>
                        <img className="d-block w-100"src={photo3} style={{height:'inherit'}}/>
                    </Carousel.Item>
                </Carousel>
            </main>
        </div>
    );

}
export default withRouter(LoggedOutHomepage);


