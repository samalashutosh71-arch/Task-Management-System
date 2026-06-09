package com.ashu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ashu.entity.Task;
import com.ashu.service.TaskService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/tasks")
public class TaskController 
{
	@Autowired
	private TaskService taskServ;
	
	//if inside controller you have to use anything inside method param when you have to read anything from url path or web
	@PostMapping("/createTask")
	public ResponseEntity<Task>createTask(@RequestBody Task task)
	{
		Task save=taskServ.createTask(task);
		return new ResponseEntity<Task>(save,HttpStatus.OK);	
		
	}
	@GetMapping("/getAllTask")
	public ResponseEntity<List<Task>> getAllTask()
	{
		List<Task>tasks=taskServ.getAlltask();
		return new ResponseEntity<List<Task>>(tasks,HttpStatus.OK);
		
		
	}
	//gettaskByTaskId
	@GetMapping("gettask/{id}")
	public ResponseEntity<Task>getTaskById(@PathVariable int id)
	{
		Task task=taskServ.getTaskById(id);
		return new ResponseEntity<Task>(task,HttpStatus.OK);
			
	}
	//update task
	@PutMapping("update/{id}")
	public ResponseEntity<Task> updateTaskById(@PathVariable int id, @RequestBody Task updatedTask) {
	    Task task = taskServ.updateTask(id, updatedTask); // must return Task
	    return new ResponseEntity<Task>(task, HttpStatus.OK); // return full updated task
	}
	@DeleteMapping("delete/{id}")
	public ResponseEntity<String>deleteTaskById(@PathVariable int id)
	{
		String msg=taskServ.deleteTask(id);
		return new ResponseEntity<String>(msg,HttpStatus.OK);
			
	}
	
	@GetMapping("taskStatus/{status}")
	public ResponseEntity<List<Task>> getTaskByStatus(@PathVariable String status)
	{
		List<Task>taskList=taskServ.findByTaskStatus(status);
		return new ResponseEntity<List<Task>>(taskList,HttpStatus.OK);
			
	}
	
	//asigntask
	@PutMapping("/assign/{taskId}/{userId}")
	public ResponseEntity<Task> assignTask(
	        @PathVariable Long taskId,
	        @PathVariable Long userId,
	        @RequestBody Task updatedTask )                       {

	    Task task = taskServ.assignTask(taskId, userId,updatedTask);
	    return ResponseEntity.ok(task);
	}
	
	//get task by userid
	@GetMapping("/yourtasks/{userId}")
	public ResponseEntity<List<Task>>getTaskByUserId(@PathVariable long userId)
	{
		List<Task>tasks=taskServ.getTaskByUserId(userId);
		return new ResponseEntity<List<Task>>(tasks,HttpStatus.OK);
		
	}
	
	//
	@GetMapping("/pagetasks")
	public ResponseEntity<Page<Task>> getAllTaskPage(@RequestParam int page, @RequestParam int size)
	{
		Page<Task> allTaskByPage = taskServ.getAllTaskByPage(page, size);
		return new ResponseEntity<Page<Task>>(allTaskByPage,HttpStatus.OK);
	}


}
