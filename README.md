# Task Management System - Backend

## Project Overview

A Full Stack Task Management System developed using Spring Boot and React.

This backend application provides REST APIs for managing users, tasks, authentication, authorization, task assignment, pagination, search, and password management.

---

## Technologies Used

* Java 17
* Spring Boot
* Spring Security (JWT Authentication)
* Spring Data JPA
* Hibernate
* MySQL
* Maven

---

## Features

### Authentication & Authorization

* JWT Based Login
* Role-Based Access Control
* Manager and User Roles

### User Management

* Create User
* Update User
* Delete User
* View Users
* Manager-Specific User Listing
* Search User by Name or ID
* Pagination Support

### Task Management

* Create Task
* Update Task
* Delete Task
* Assign Task to User
* View Tasks
* Filter Tasks by Status
* Pagination Support

### User Features

* View Assigned Tasks
* Update Task Status
* Change Password
* Deadline Tracking
* Completion Time Tracking

---

## Database

* MySQL Database
* One Manager can manage multiple Users
* One User can have multiple Tasks

---

## API Endpoints

### Authentication

* POST /api/auth/login

### Users

* POST /api/saves/createUser
* GET /api/saves/getAllUser
* GET /api/saves/{id}
* PUT /api/saves/update/{id}
* DELETE /api/saves/delete/{id}

### Tasks

* POST /api/tasks/createTask
* GET /api/tasks/getAllTask
* PUT /api/tasks/update/{id}
* DELETE /api/tasks/delete/{id}
* PUT /api/tasks/assign/{taskId}/{userId}

---

## Author

Ashutosha Samal
