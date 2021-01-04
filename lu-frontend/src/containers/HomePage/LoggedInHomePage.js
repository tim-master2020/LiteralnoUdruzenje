import React, { useState } from "react";
import Drawer from '@material-ui/core/Drawer';
import Button from '@material-ui/core/Button';
import MenuIcon from '@material-ui/icons/Menu';
import Divider from '@material-ui/core/Divider';
import { withRouter, useParams } from 'react-router-dom';
import { makeStyles, useTheme } from '@material-ui/core/styles';
import CssBaseline from '@material-ui/core/CssBaseline';
import clsx from 'clsx';
import IconButton from '@material-ui/core/IconButton';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import { SidebarList } from './SidebarList';
import { styling } from './SidebarStyling';
import { AppBar, Toolbar } from "@material-ui/core";
import { defaultUrl } from '../../backendConfig';
import InitialUpload from "../../components/uploadPDF/InitialUpload";
import BookReview from '../../components/bookReview/BookReview'

const LoggedInHomepage = ({ loggedInUser, setLoggedIn,history, isInitialUpload, isReview }) => {
    const [isOpen, setOpen] = useState(false);

    const handleDrawerToggle = () => {
        setOpen(!isOpen);
    };

    const theme = useTheme();
    const useStyles = makeStyles((theme) => (styling(theme)));
    const classes = useStyles();

    let {id} = useParams();

    // const startPublishBookProcess = () => {
    //     axios.get(`${defaultUrl}/auth/user`, options).then(
    //         (resp) => {
    //             setLoggedIn(resp.data);
    //         },
    //         (resp) => {
    //             alert('error getting logged in user data');
    //             setLoggedIn(undefined);
    //         }
    //     );
    // }

    return (
        <div className={classes.root}>
            <CssBaseline />
            <AppBar position="fixed" className={clsx(classes.appBar, { [classes.appBarShift]: isOpen })}>
                <Toolbar className={classes.toolbar}>
                    <IconButton color="inherit" onClick={handleDrawerToggle} edge="start" className={clsx(classes.menuButton, isOpen && classes.hide)}>
                        <MenuIcon style={{ color: 'black' }} />
                    </IconButton>
                    <div style={{ width: '100%' }}></div>
                    <Button onClick={() => history.push('/')}>Home</Button>
                    <Button>My account</Button>
                    { loggedInUser.role === 'WRITER' &&
                        <Button>Book publishing</Button>
                    }
                    { loggedInUser.role === 'COMMITTEE' &&
                        <Button onClick={() => history.push('/review')}>Book review</Button>
                    }
                    <Button onClick={() => { 
                            localStorage.clear(); 
                            history.push('/')
                            setLoggedIn(undefined)}}>Logout</Button>
                </Toolbar>
            </AppBar>
            <MenuIcon />
            <Drawer className={classes.drawer} variant="persistent" anchor="left" open={isOpen} classes={{ paper: classes.drawerPaper }}>
                <div className={classes.drawerHeader}>
                    <IconButton onClick={handleDrawerToggle}>
                        {theme.direction === 'ltr' ? <ChevronLeftIcon /> : <ChevronRightIcon />}
                    </IconButton>
                </div>
                <Divider />
                {SidebarList(history, loggedInUser)}
            </Drawer>
            <main className={clsx(classes.content, { [classes.contentShift]: isOpen })}>
                <div className={classes.drawerHeader} />
                { isInitialUpload &&
                    <InitialUpload processId={id}/>
                }
                { isReview &&
                    <BookReview />
                }
            </main>
        </div>
    );

}
export default withRouter(LoggedInHomepage);


