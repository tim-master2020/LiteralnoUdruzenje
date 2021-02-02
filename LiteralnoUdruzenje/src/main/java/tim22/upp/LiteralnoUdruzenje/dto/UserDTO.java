package tim22.upp.LiteralnoUdruzenje.dto;

import tim22.upp.LiteralnoUdruzenje.model.enums.Role;
import tim22.upp.LiteralnoUdruzenje.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {

    private String firstname;

    private String lastname;

    private String city;

    private String country;

    private String email;

    private String username;

    private Role role;

    private List<TaskDTO> tasks = new ArrayList<>();

    public UserDTO(String firstname, String lastname, String city, String country, String email, String username, Role role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.city = city;
        this.country = country;
        this.email = email;
        this.username = username;
        this.role = role;
    }

    public UserDTO(User user){
        this(user.getFirstname(), user.getLastname(), user.getCity(), user.getCountry(), user.getEmail(), user.getUsername(), user.getRole());
    }

    public UserDTO() { }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<TaskDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskDTO> tasks) {
        this.tasks = tasks;
    }
}
