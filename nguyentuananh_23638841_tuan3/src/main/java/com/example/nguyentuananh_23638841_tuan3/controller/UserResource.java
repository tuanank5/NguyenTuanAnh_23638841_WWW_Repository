package com.example.nguyentuananh_23638841_tuan3.controller;

import com.example.nguyentuananh_23638841_tuan3.model.User;
import jakarta.inject.Named;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


import java.util.ArrayList;
import java.util.List;

@Named
@Path("/user")
public class UserResource {
    
    private static final List<User> userList = new ArrayList<>();

    static {
        userList.add(new User(1, "Mai Hoang", "hoang@gmail.com"));
        userList.add(new User(2, "Lam Tong", "tong@gmail.com"));
    }

    @GET
    @Path("/view")
    @Produces("text/json")
    public String viewUser(){
    return "Hoang Minh";
}
    @GET
    @Path("/add/{a}/{b}")
    public int Add(@PathParam("a") int a, @PathParam("b") int b){
    return a+b;
}

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers(){
            return Response.ok(userList).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User getUserById(@PathParam("id") int id) {
        return userList.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(User user) {
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("User không hợp lệ")
                    .build();
        }

        userList.add(user);

        return Response.status(Response.Status.CREATED)
                .entity(user)
                .build();
    }
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") int id, User updatedUser) {
        return userList.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .map(user -> {
                    user.setName(updatedUser.getName());
                    user.setEmail(updatedUser.getEmail());
                    return Response.ok(user).build();
                })
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND)
                        .entity("Không tìm thấy user có id = " + id)
                        .type(MediaType.TEXT_PLAIN)
                        .build());
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("id") int id) {
        User userToDelete = userList.stream()
                .filter(user -> user.getId() == id)
                .findFirst()
                .orElse(null);

        if (userToDelete == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Không tìm thấy user có id = " + id)
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }

        userList.remove(userToDelete);

        return Response.ok(userToDelete).build();
    }
}
