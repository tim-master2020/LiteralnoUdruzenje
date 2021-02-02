import React from 'react';
import Divider from '@material-ui/core/Divider';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemText from '@material-ui/core/ListItemText';
import { TaskNameRoutes } from '../../functions/TaskNameRoutes';
import { withRouter } from 'react-router-dom';


const SidebarList = ({history, user}) => {
    function renderTasks(tasks) {
        return tasks.map((task) => {
            console.log(task);
            return (
                <ListItem  onClick={() => {
                    history.push({
                        pathname:`${TaskNameRoutes(task.name)}/${task.taskId}`,
                        state: {
                          taskId: task.taskId
                        }
                      });                  
                    }} button>
                <ListItemText primary={(task.name.replace(/([A-Z])/g, ' $1').trim()).toUpperCase()} />
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
}
export default withRouter(SidebarList);


