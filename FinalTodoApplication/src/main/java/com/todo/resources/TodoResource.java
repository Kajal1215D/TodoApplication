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

import com.todo.models.Todos;
import com.todo.models.User;
import com.todo.models.dao.TodosDao;
import com.todo.models.dao.UserDao;

@RequestScoped
@Path("users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TodoResource {
	
	@Inject
	UserDao userDao;
	
	@Inject
	TodosDao todosDao;
	
	//Get All Todos of User by userId
	@GET
	@Path("{userId}/todos")
	public Response getAllTodosOfUser(@PathParam("userId") int userId) {
		System.out.println("coming into all tasks");
		return Response.ok(todosDao.getAllTodosofUser(userId)).build();
	}
	
	//Get Perticular Todo of User by todoId
	@GET
	@Path("{userId}/todos/{todoId}")
	public Response getTodoByTodoId(@PathParam("userId") int userId,@PathParam("todoId") int todoId) {
		return Response.ok(todosDao.getTodoByTodoId(todoId)).build();
	}
	
	//Post(Add) Todo
	@POST
	@Path("{userId}/todos")
	public Response addTask(@PathParam("userId") int userId, Todos todo) {
		System.out.println("In add todo");
		User user = userDao.getUser(userId);
		todo.setUser(user);
		return Response.ok(todosDao.addTodo(todo)).build();
	}
	
	
	//Delete Todo
	@DELETE
	@Path("{userId}/todos/{todoId}")
	public String deleteTodo(@PathParam("userId") int userId, @PathParam("todoId") int todoId) {
		Todos todo = todosDao.getTodoByTodoId(todoId);
		if(todo.getUser().getUser_id() == userId) {
			todosDao.deleteTodo(todoId);
			return "Deleted Successfully";
		}
		else{
			return "No item to delete...!!!";
		}
	}
	
	//Update Todo
	@PUT
	@Path("{userId}/todos/{todoId}")
	public Response updateTodo(@PathParam("userId") int userId, @PathParam("todoId") int todoId,Todos updatedTodo) {
		
		User user = userDao.getUser(userId);
		
		if(user == null)
			throw new NullPointerException("User not found");
			
			Todos todo = todosDao.getTodoByTodoId(todoId);
			todo.setTodoName(updatedTodo.getTodoName());
			todo.setDescription(updatedTodo.getDescription());
			todo.setStartDate(updatedTodo.getStartDate());
			todo.setCompletionDate(updatedTodo.getCompletionDate());
			todo.setPriority(updatedTodo.getPriority());
			todo.setToDoStatus(updatedTodo.isToDoStatus());
			
			todosDao.updateTodo(todo);
			
			return Response.ok().build();
	}
		
}
