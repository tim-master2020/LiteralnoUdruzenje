import React from 'react';
import Divider from '@material-ui/core/Divider';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';

export function SidebarList(role) {
    debugger;
    return (
        <List>
           { role === 'WRITER' &&
            <div>
                <ListItem button>
                <ListItemText primary="Something" />
                </ListItem>
            </div>
            }
        </List>
    );
};
