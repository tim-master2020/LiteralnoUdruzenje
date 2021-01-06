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
import { defaultUrl } from '../../backendConfig';
import axios from 'axios';
import PublishBookGeneralData from "../../components/publish-book/PublishBookGeneralData";

const LoggedInHomepage = ({ loggedInUser, setLoggedIn, history,publishBookGeneralData}) => {
    const [isOpen, setOpen] = useState(false);

    const handleDrawerToggle = () => {
        setOpen(!isOpen);
    };

    const theme = useTheme();
    const useStyles = makeStyles((theme) => (styling(theme)));
    const classes = useStyles();

    const startPublishBookProcess = () => {

        const options = {
            headers: { 'Authorization': 'Bearer ' + localStorage.getItem('token') }
        };

        axios.get(`${defaultUrl}/process/start-process-specific/BookPublishing`, options).then(
            (resp) => {
                history.push({
                    pathname: '/bookGeneralData',
                    state: {
                      taskId: resp.data.taskId
                    }
                  });
            },
            (resp) => {
                alert('fail start of process');
            }
        );
    }

    return (
        <div className={classes.root}>
            <CssBaseline />
            <AppBar position="fixed" className={clsx(classes.appBar, { [classes.appBarShift]: isOpen })}>
                <Toolbar className={classes.toolbar}>
                    <IconButton color="inherit" onClick={handleDrawerToggle} edge="start" className={clsx(classes.menuButton, isOpen && classes.hide)}>
                        <MenuIcon style={{ color: 'black' }} />
                    </IconButton>
                    <div style={{ width: '100%' }}></div>
                    <Button>my account</Button>
                    {loggedInUser.role === 'WRITER' &&

                        <Button
                            onClick={() => { startPublishBookProcess() }}
                        >
                            Book publishing
                        </Button>
                    }
                    <Button onClick={() => {
                        localStorage.clear();
                        history.push('/')
                        setLoggedIn(undefined)
                    }}>logout</Button>
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
                {SidebarList(loggedInUser.role)}
            </Drawer>
            <main className={clsx(classes.content, { [classes.contentShift]: isOpen })}>
                <div className={classes.drawerHeader} />
                <div>
                    { publishBookGeneralData &&
                        <PublishBookGeneralData/>
                    }
                </div>
            </main>
        </div>
    );

}
export default withRouter(LoggedInHomepage);


