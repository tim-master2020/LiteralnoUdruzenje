import React from 'react';
import Divider from '@material-ui/core/Divider';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import { TaskNameRoutes } from '../../functions/TaskNameRoutes';

export function SidebarList(history, user) {
    return (
        <List>
            {
                user.tasks &&
                <div>
                    <ListItem  onClick={() => {history.push(`${TaskNameRoutes(user.tasks[0].name)}/${user.tasks[0].taskId}`)}} button>
                    <ListItemText primary={user.tasks[0].name} />
                    </ListItem>
                </div>
            }
        </List>
    );
};
