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
import SidebarList  from './SidebarList';
import { styling } from './SidebarStyling';
import { AppBar, Toolbar } from "@material-ui/core";
import { defaultUrl } from '../../backendConfig';
import InitialUpload from "../../components/uploadPDF/InitialUpload";
import BookReview from '../../components/bookReview/BookReview'
import Payment from '../../components/payment/Payment';
import ReviewPage from '../../components/bookReview/ReviewPage';
import axios from 'axios';
import PublishBookGeneralData from "../../components/publish-book/PublishBookGeneralData";
import ReviewBookGeneral from "../../components/publish-book/ReviewBookGeneral";
import DeclineExplanation from "../../components/publish-book/DeclineExplanation";
import UploadRestOfTheWork from "../../components/publish-book/UploadRestOfTheWork";
import getUser from "../../functions/UserFunctions";
import ComparePlagiats from "../../components/publish-book/ComparePlagiats";
import DownloadBook from "../../components/publish-book/DownloadBook"
import DecideSendingToBeta from "../../components/publish-book/DecideSendingToBeta"
const LoggedInHomepage = ({ loggedInUser,setLoggedIn,history,isInitialUpload,isReview,isPayment,isReviewPreview,publishBookGeneralData,reviewBookGeneral,giveExplanation,uploadRestWork,comparePlagiats,downloadBook,decideBeta}) => {
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

    const updateUser = () => {
        getUser(setLoggedIn);
    }

    return (
        <div className={classes.root}>
            <CssBaseline />
            <AppBar position="fixed" className={clsx(classes.appBar, { [classes.appBarShift]: isOpen })}>
                <Toolbar className={classes.toolbar}>
                    <IconButton color="inherit" onClick={handleDrawerToggle} edge="start" className={clsx(classes.menuButton, isOpen && classes.hide)}>
                        <MenuIcon style={{ color: 'black' }} />
                    </IconButton>

                    <div style={{ width: '100%' }}>
                    <Button className={classes.button} onClick={() => history.push('/')}>Home</Button>
                    { loggedInUser.role === 'WRITER' &&
                        <div>
                            <Button className={classes.button} onClick={() => history.push('/reviews')}>Reviews</Button>
                            <Button
                                onClick={() => { startPublishBookProcess() }}
                            >
                                Book publishing
                            </Button>
                        </div>
                    }
                    { loggedInUser.role === 'COMMITTEE' &&
                        <Button className={classes.button} onClick={() => history.push('/review')}>Book review</Button>
                    }
                    <Button className={classes.button}
                     onClick={() => { 
                            localStorage.clear(); 
                            history.push('/')
                            setLoggedIn(undefined)}}>Logout</Button>

                    <Button onClick={() => {
                        localStorage.clear();
                        history.push('/')
                        setLoggedIn(undefined)
                    }}>logout</Button>
                </div>
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
                {SidebarList({user: loggedInUser})}
            </Drawer>
            <main className={clsx(classes.content, { [classes.contentShift]: isOpen })}>
                <div className={classes.drawerHeader} />
                <div>
                    { publishBookGeneralData &&
                        <PublishBookGeneralData/>
                    }{ reviewBookGeneral &&
                        <ReviewBookGeneral updateUser={()=>updateUser()}/>
                    }{ giveExplanation &&
                        <DeclineExplanation updateUser={()=>updateUser()}/>
                    }{ uploadRestWork &&
                        <UploadRestOfTheWork updateUser={()=>updateUser()}/>
                    }{ comparePlagiats &&
                        <ComparePlagiats updateUser={()=>updateUser()}/>
                    }{ downloadBook &&
                        <DownloadBook updateUser={()=>updateUser()}/>
                    }{ decideBeta &&
                        <DecideSendingToBeta updateUser={()=>updateUser()}/>
                    }{ isInitialUpload &&
                        <InitialUpload updateUser={()=>updateUser()}/>
                    }{ isReview &&
                        <BookReview updateUser={()=>updateUser()} />
                    }{ isPayment &&
                        <Payment updateUser={()=>updateUser()} />
                    }{ isReviewPreview &&
                        <ReviewPage updateUser={()=>updateUser()} />
                    }
                </div>
            </main>
        </div>
    );

}
export default withRouter(LoggedInHomepage);


