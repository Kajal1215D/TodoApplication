package com.todo.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
@NamedQueries({ @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),

})
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int user_id;
	private String firstName, lastName, gender, email, password,role; 
	private LocalDate regDate, actDate, birthDate; 
	private boolean actStatus; 
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonbTransient
	private List<Todos> todos;

	public User() {}
	
	public User(int user_id, String firstName, String lastName, String gender, String email, String password,
			String role, LocalDate regDate, LocalDate actDate, LocalDate birthDate, boolean actStatus) {
		super();
		this.user_id = user_id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.email = email;
		this.password = password;
		this.role = role;
		this.regDate = regDate;
		this.actDate = actDate;
		this.birthDate = birthDate;
		this.actStatus = actStatus;
	}
	
	public List<Todos> getTodos() {
		return todos;
	}

	public void setTodos(List<Todos> todos) {
		this.todos = todos;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public LocalDate getRegDate() {
		return regDate;
	}

	public void setRegDate(LocalDate regDate) {
		this.regDate = regDate;
	}

	public LocalDate getActDate() {
		return actDate;
	}

	public void setActDate(LocalDate actDate) {
		this.actDate = actDate;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public boolean isActStatus() {
		return actStatus;
	}

	public void setActStatus(boolean actStatus) {
		this.actStatus = actStatus;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", firstName=" + firstName + ", lastName=" + lastName + ", gender=" + gender
				+ ", email=" + email + ", password=" + password + ", role=" + role + ", regDate=" + regDate
				+ ", actDate=" + actDate + ", birthDate=" + birthDate + ", actStatus=" + actStatus + "]";
	}
	
	
	
}
