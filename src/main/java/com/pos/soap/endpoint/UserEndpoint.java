package com.pos.soap.endpoint;

import com.pos.soap.ws.*;
import com.pos.soap.model.User;
import com.pos.soap.service.UserService;
import com.pos.soap.ws.GetAllUsersRequest;
import com.pos.soap.ws.GetAllUsersResponse;
import com.pos.soap.ws.GetUserByIdRequest;
import com.pos.soap.ws.GetUserByIdResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class UserEndpoint {

    // MUST match the XSD targetNamespace exactly (with 'www')
    private static final String NAMESPACE_URI = "http://www.pos.com/soap/users";

    private final UserService userService;

    public UserEndpoint(UserService userService) {
        this.userService = userService;
    }

    // ===== Mappers =====
    private UserView toView(User e) {
        if (e == null) return null;
        UserView v = new UserView();
        v.setId(e.getId());
        v.setUsername(e.getUsername());
        v.setRole(e.getRole());
        return v;
    }

    private User toEntity(UserRequestModel r) {
        if (r == null) return null;
        return User.builder()
                .id(r.getId())
                .username(r.getUsername())
                .password(r.getPassword()) // hash inside service layer
                .role(r.getRole())
                .build();
    }

    private User toEntity(SaveUserRequest r) {
        // If your XSD defines <xs:element name="SaveUserRequest" type="tns:UserRequestModel"/>,
        // JAXB usually generates SaveUserRequest with the same fields as UserRequestModel.
        // Map from SaveUserRequest directly.
        if (r == null) return null;
        return User.builder()
                .id(r.getId())
                .username(r.getUsername())
                .password(r.getPassword()) // hash inside service layer
                .role(r.getRole())
                .build();
    }

    // ✅ Get all users
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllUsersRequest")
    @ResponsePayload
    public GetAllUsersResponse getAllUsers(@RequestPayload GetAllUsersRequest request) {
        List<User> users = userService.getAllUsers();
        GetAllUsersResponse response = new GetAllUsersResponse();
        users.stream().map(this::toView).forEach(response.getUser()::add); // <user maxOccurs="unbounded"/>
        return response;
    }

    // ✅ Get user by ID
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetUserByIdRequest")
    @ResponsePayload
    public GetUserByIdResponse getUserById(@RequestPayload GetUserByIdRequest request) {
        GetUserByIdResponse response = new GetUserByIdResponse();
        userService.getUserById(request.getId())
                .map(this::toView)
                .ifPresent(response::setUser); // single <user/>
        return response;
    }

    // ✅ Create or update user
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SaveUserRequest")
    @ResponsePayload
    public SaveUserResponse saveUser(@RequestPayload SaveUserRequest request) {
        User toSave = toEntity(request);
        User savedUser = userService.saveUser(toSave);

        SaveUserResponse response = new SaveUserResponse();
        response.setUser(toView(savedUser));    // no password in responses
        response.setStatus("SUCCESS");
        response.setMessage("User saved successfully");
        return response;
    }

    // ✅ Delete user
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteUserRequest")
    @ResponsePayload
    public DeleteUserResponse deleteUser(@RequestPayload DeleteUserRequest request) {
        userService.deleteUser(request.getId());
        DeleteUserResponse response = new DeleteUserResponse();
        response.setStatus("SUCCESS");
        response.setMessage("User deleted successfully");
        return response;
    }
}
