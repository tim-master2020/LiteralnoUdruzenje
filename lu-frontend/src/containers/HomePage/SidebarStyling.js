const drawerWidth = 240; 

export  function styling (theme) {
    var stylingObject = {
        root: {
            display: 'flex',
            boxShadow:0,
        },
        appBar: {
            transition: theme.transitions.create(['margin', 'width'], {
                easing: theme.transitions.easing.sharp,
                duration: theme.transitions.duration.leavingScreen,
            }),
            backgroundColor:'white',
            boxShadow:0,
        },
        appBarShift: {
            width: `calc(100% - ${drawerWidth}px)`,
            backgroundColor:'white',
            marginLeft: drawerWidth,
            transition: theme.transitions.create(['margin', 'width'], {
                easing: theme.transitions.easing.easeOut,
                duration: theme.transitions.duration.enteringScreen,
            }),
            boxShadow:0,
        },
        menuButton: {
            marginRight: theme.spacing(2),
            
        },
        hide: {
            display: 'none',
        },
        drawer: {
            width: drawerWidth,
            flexShrink: 0,
        },
        drawerPaper: {
            width: drawerWidth,
            backgroundColor:'white'
        },
        drawerHeader: {
            display: 'flex',
            alignItems: 'center',
            padding: theme.spacing(0, 1),
            ...theme.mixins.toolbar,
            justifyContent: 'flex-end',
        },
        content: {
            flexGrow: 1,
            padding: theme.spacing(3),
            transition: theme.transitions.create('margin', {
                easing: theme.transitions.easing.sharp,
                duration: theme.transitions.duration.leavingScreen,
            }),
            marginLeft: -(drawerWidth-5),
        },
        contentShift: {
            transition: theme.transitions.create('margin', {
                easing: theme.transitions.easing.easeOut,
                duration: theme.transitions.duration.enteringScreen,
            }),
            marginLeft: 0,
        },

        logoutButton : {
            color:'white',
            
        },toolbar:{
            boxShadow:0,
            backgroundColor:'white',
        },
        button: {
            marginRight: '0.5%'
        }
    }
    return stylingObject;
};