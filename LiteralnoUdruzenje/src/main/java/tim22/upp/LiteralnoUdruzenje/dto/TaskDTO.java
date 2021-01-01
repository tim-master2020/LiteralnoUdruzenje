package tim22.upp.LiteralnoUdruzenje.dto;

public class TaskDTO {

    String taskId;
    String processInstanceId;
    String name;
    String assignee;

    public TaskDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public TaskDTO(String taskId, String processInstanceId, String name, String assignee) {
        super();
        this.taskId = taskId;
        this.processInstanceId = processInstanceId;
        this.name = name;
        this.assignee = assignee;
    }

    public String getprocessInstanceId() {
        return processInstanceId;
    }

    public void setprocessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }
}
