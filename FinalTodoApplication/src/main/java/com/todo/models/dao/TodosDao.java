package com.todo.models.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.todo.models.Todos;
import com.todo.models.User;

@Stateless
public class TodosDao {
	@Inject
	UserDao userDao;
	@PersistenceContext(unitName = "finaltodoapplication-pu")
	private EntityManager entityManager;
	
	public Todos getTodoByTodoId(int todoId) {
		return entityManager.find(Todos.class, todoId);
	}
	
	public List<Todos> getAllTodosofUser(int user_id){
		User find= entityManager.find(User.class, user_id);
    	List list=null;
        list=entityManager.createQuery("select a from Todos a  where a.user=:user_id")
        		.setParameter("user_id", find).getResultList();
        return list;
	}

	public Todos addTodo(Todos todo) {
		entityManager.persist(todo);
		return todo;
	}
	
	public void deleteTodo(int todoId) {
		entityManager.remove(entityManager.find(Todos.class, todoId));
	}
	
	public Todos updateTodo(Todos todo) {
		if(!entityManager.contains(todo)) {
			todo = entityManager.merge(todo);
		}
		return todo;
	}
}
