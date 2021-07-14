package com.cognixia.jump.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Todo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank
	@Column(columnDefinition = "varchar(255)")
	private String description;
	
	private Date dueDate;
	
	@ManyToOne
	@JoinColumn(name="user_id", referencedColumnName = "id")
	@JsonBackReference
	private User user;
	
	public Todo() {
		this(-1, "N/A", new Date(System.currentTimeMillis()), new User());
	}

	public Todo(Integer id, @NotBlank String description, Date dueDate, User user) {
		super();
		this.id = id;
		this.description = description;
		this.dueDate = dueDate;
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Todo [id=" + id + ", description=" + description + ", dueDate=" + dueDate + "]";
	}
	
}
