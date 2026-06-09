package com.ashu.repositary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashu.entity.Task;

public interface TaskRepositary extends JpaRepository<Task, Long> 
{
	public List<Task>findByStatus(String status);

}
