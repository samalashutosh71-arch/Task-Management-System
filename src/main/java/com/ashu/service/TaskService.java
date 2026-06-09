package com.ashu.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ashu.entity.Task;

public interface TaskService 
{
	public Task createTask(Task task);
	public List<Task>getAlltask();
	
	public Task getTaskById(long id);
	
	public Task updateTask(long id,Task updatedTask);
	
	public String deleteTask(long id) ;
	
	public List<Task>findByTaskStatus(String status);
	
	public Task assignTask(long taskId,long userId,Task updatedTask);
	
	public List<Task> getTaskByUserId(long userid);
	
	public Page<Task>getAllTaskByPage(int page,int size);

}
