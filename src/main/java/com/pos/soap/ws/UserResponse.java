package com.pos.soap.ws;

import com.pos.soap.model.User;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "UserResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserResponse", propOrder = {
        "users"
})
public class UserResponse {

    @XmlElement(name = "user")
    private List<User> users = new ArrayList<>();

    // Constructors
    public UserResponse() {}

    public UserResponse(List<User> users) {
        this.users = users;
    }

    // Getter and setter
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    // Add a single user
    public void addUser(User user) {
        this.users.add(user);
    }
}
