import React from 'react';
import Divider from '@material-ui/core/Divider';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import { TaskNameRoutes } from '../../functions/TaskNameRoutes';

export function SidebarList(history, user) {

    function renderTasks(tasks) {
        return tasks.map((task) => {
            return (
                <ListItem  onClick={() => {history.push(`${TaskNameRoutes(task.name)}/${task.taskId}`)}} button>
                <ListItemText primary={task.name} />
                </ListItem>
            );
        })
    }

    return (
        <List>
            {
                user.tasks.length > 0 &&
                <div>
                    {renderTasks(user.tasks)}
                </div>
            }
        </List>
    );
};
