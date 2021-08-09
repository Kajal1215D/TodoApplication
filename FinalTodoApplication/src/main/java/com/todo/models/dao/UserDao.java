package com.todo.models.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.todo.models.User;

@Stateless
public class UserDao {
	@PersistenceContext(unitName="finaltodoapplication-pu")
	private EntityManager entityManager;
	
	public List<User> getAllUsers() {
	   	 return entityManager.createNamedQuery("User.findAll", User.class).getResultList();
	   }
	
	public User getUser(int  userId) {
		return entityManager.find(User.class, userId);
	}
	
	public User addUser(User user) {
		if(user.getEmail().equals("admin@gmail.com")){
			user.setRole("admin");
			user.setActStatus(true);
		}
		else {
			user.setRole("user");
		}
		entityManager.persist(user);
		return user;
	}
	
	public String deleteUser(User user) {
		if (!entityManager.contains(user)) {
			user = entityManager.merge(user);
		}
		entityManager.remove(user);
		return "deleted successfully !";
	}
	
	public User updateUser(User updatedUser) {

		if (!entityManager.contains(updatedUser)) {
			updatedUser = entityManager.merge(updatedUser);
		}
		return updatedUser;
	}
}