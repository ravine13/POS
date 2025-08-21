package com.pos.soap.ws;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "UserRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UserRequest", propOrder = {
        "id",
        "username",
        "password",
        "role"
})
public class UserRequest {

    private Long id;
    private String username;
    private String password;
    private String role;

    // Constructors
    public UserRequest() {}

    public UserRequest(Long id, String username, String password, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
