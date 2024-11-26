package com.springboot.TaskManager.model;

import java.time.LocalDate;

import com.springboot.TaskManager.enums.Priority;
import com.springboot.TaskManager.enums.Status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(nullable=false)
	private String title;
	
	@Column(nullable=false)
	private String description;
	
	@Column(nullable=false)
	private LocalDate due_date;
	
	@Enumerated(EnumType.STRING)
	private Priority priority;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	
	public Task() {
		super();
	}
	
	public Task(int id, String title, String description, LocalDate due_date, Priority priority, Status status) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.due_date = due_date;
		this.priority = priority;
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDue_date() {
		return due_date;
	}

	public void setDue_date(LocalDate due_date) {
		this.due_date = due_date;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	
}
