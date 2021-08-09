package com.todo.resources;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.todo.models.User;
import com.todo.models.dao.UserDao;

@RequestScoped
@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
	
	@Inject
	UserDao userDao;
	
//Get all Users
	@GET
	public Response getAllUsers() {
		System.out.println("In getAllUser");
		return Response.ok(userDao.getAllUsers()).build();
	}
	
//	Get Particular User by UserId
	@GET
	@Path("{userId}")
	public Response getUserByUserId(@PathParam("userId") int userId) {
		return Response.ok(userDao.getUser(userId)).build();
	}
	
//	Add User
	@POST
	public Response addUser(User user) {
		return Response.ok(userDao.addUser(user)).build();
	}
	
//	Delete User
	@DELETE
	@Path("{userId}")
	public Response deleteUser(@PathParam("userId") int userId){
		User user = userDao.getUser(userId);
		return Response.ok(userDao.deleteUser(user)).build();
	}

//	Update User
	@PUT
	@Path("{userId}")
	public Response updateUser(@PathParam("userId") int userId, User updatedUser) {
		
		User user = userDao.getUser(userId);
		
		user.setFirstName(updatedUser.getFirstName());
		user.setLastName(updatedUser.getLastName());
		user.setGender(updatedUser.getGender());
		user.setEmail(updatedUser.getEmail());
		user.setBirthDate(updatedUser.getBirthDate());
		user.setPassword(updatedUser.getPassword());
		user.setRegDate(updatedUser.getRegDate());
		user.setActStatus(updatedUser.isActStatus());
		user.setActDate(updatedUser.getActDate());
		user.setRole(updatedUser.getRole());
		
		userDao.updateUser(user);
		
		return Response.ok().build();
	}

}
