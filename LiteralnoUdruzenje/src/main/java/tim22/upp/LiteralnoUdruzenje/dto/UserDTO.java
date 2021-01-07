package tim22.upp.LiteralnoUdruzenje.dto;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {

    private String username;

    private String email;

    private List<TaskDTO> tasks = new ArrayList<>();

    private String role;

    public UserDTO(){}

    public UserDTO(String username, String email, List<TaskDTO> tasks,String role) {
        this.username = username;
        this.email = email;
        this.tasks = tasks;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<TaskDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDTO> tasks) {
        this.tasks = tasks;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
