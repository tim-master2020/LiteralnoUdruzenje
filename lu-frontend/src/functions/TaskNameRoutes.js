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
        case 'ReviewNewWriter':
            route = '/review';
            break;
        case 'reviewBookToPublish':
            route = '/reviewBookGeneral';
            break;
        case 'GiveExplanation':
            route = '/giveExplanation';
            break;
        default:
            route = '/';
            break;
    }
    return route;
};