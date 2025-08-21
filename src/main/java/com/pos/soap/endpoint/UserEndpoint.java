package com.pos.soap.endpoint;

import com.pos.soap.model.User;
import com.pos.soap.service.UserService;
import com.pos.soap.ws.UserRequest;
import com.pos.soap.ws.UserResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class UserEndpoint {

    private static final String NAMESPACE_URI = "http://pos.com/soap/ws";

    private final UserService userService;

    public UserEndpoint(UserService userService) {
        this.userService = userService;
    }

    // Get all users
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllUsersRequest")
    @ResponsePayload
    public UserResponse getAllUsers(@RequestPayload UserRequest request) {
        List<User> users = userService.getAllUsers();
        UserResponse response = new UserResponse();
        response.getUsers().addAll(users);
        return response;
    }

    // Get user by ID
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetUserByIdRequest")
    @ResponsePayload
    public UserResponse getUserById(@RequestPayload UserRequest request) {
        UserResponse response = new UserResponse();
        userService.getUserById(request.getId())
                .ifPresent(response::addUser);
        return response;
    }

    // Create or update user
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SaveUserRequest")
    @ResponsePayload
    public UserResponse saveUser(@RequestPayload UserRequest request) {
        User user = new User();
        user.setId(request.getId());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());

        User savedUser = userService.saveUser(user);

        UserResponse response = new UserResponse();
        response.addUser(savedUser);
        return response;
    }

    // Delete user
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteUserRequest")
    @ResponsePayload
    public UserResponse deleteUser(@RequestPayload UserRequest request) {
        userService.deleteUser(request.getId());
        return new UserResponse(); // empty response
    }
}
