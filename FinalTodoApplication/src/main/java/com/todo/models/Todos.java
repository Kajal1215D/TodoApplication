package com.todo.models;

import java.io.Serializable;
import java.time.LocalDate;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="todos")
@NamedQueries(
{
    @NamedQuery(name = "Todos.findAll", query = "SELECT t FROM Todos t"),
})
public class Todos implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int todo_id;
	private String todoName,description,priority;
    private LocalDate startDate,completionDate;
    private boolean toDoStatus;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonbTransient
	private User user;
	
	public Todos(){};
	
	public Todos(int todo_id, String todoName, String description, String priority, LocalDate startDate,
			LocalDate completionDate, boolean toDoStatus) {
		super();
		this.todo_id = todo_id;
		this.todoName = todoName;
		this.description = description;
		this.priority = priority;
		this.startDate = startDate;
		this.completionDate = completionDate;
		this.toDoStatus = toDoStatus;
	}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public int getTodo_id() {
		return todo_id;
	}

	public void setTodo_id(int todo_id) {
		this.todo_id = todo_id;
	}

	public String getTodoName() {
		return todoName;
	}

	public void setTodoName(String todoName) {
		this.todoName = todoName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(LocalDate completionDate) {
		this.completionDate = completionDate;
	}

	public boolean isToDoStatus() {
		return toDoStatus;
	}

	public void setToDoStatus(boolean toDoStatus) {
		this.toDoStatus = toDoStatus;
	}

	@Override
	public String toString() {
		return "Todos [todo_id=" + todo_id + ", todoName=" + todoName + ", description=" + description + ", priority="
				+ priority + ", startDate=" + startDate + ", completionDate=" + completionDate + ", toDoStatus="
				+ toDoStatus + "]";
	}

}
