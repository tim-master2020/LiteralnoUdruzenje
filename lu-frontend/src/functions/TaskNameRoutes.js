import React from 'react';
import Divider from '@material-ui/core/Divider';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';

export function TaskNameRoutes(name) {
    var route = '';
    switch (name) {
        case 'UploadPDFForm':
            route = '/upload';
            break;
    
        default:
            route = '/';
            break;
    }
    return route;
};
