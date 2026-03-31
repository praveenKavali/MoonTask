# Moon Task

**Moon Task** is a task manager application. Here you can add task. Filter or search task and can perform other task related operations.

## ✨ Features

* **User Authentication:** Secure Login and Registration using JWT (JSON Web Tokens).
* **Task Management:** Full CRUD operations (Create, Read, Update) for personal tasks.
* **Dynamic UI:** Interactive task dashboard built with React and CSS Modules.
* **Task Categorization:** Sort and filter tasks by Priority and Status.
* **Smart Completion:** Automatic server-side timestamping when a task is marked as finished.
* **Containerized:** Local development environment managed via Docker.
* **Redis Caching:** Integrated Redis to cache frequent task queries, reducing database load and improving response times for the Task Dashboard.

## ⚙️ Tech Stack

**Frontend:**  React, HTML5, CSS(module), JavaScript  
**Backend:**  SpringBoot, Java  
**Database:** PostgreSQL(running via docker)  
**Testing:** (backend) JUnit, Mockito  
**DevOps:** Docker, Docker Compose
### 🚀 Getting Started

The easiest way to run the entire Moon Task ecosystem (Frontend, Backend, Postgres and Redis) is using Docker Compose.
#### 1.Prerequisites
* **Docker & Docker Compose** (Recommended) Ensure you have [Docker Desktop](https://www.docker.com/products/docker-desktop/) installed.
* **JDK 22**, **Node.js**, **Redis 7**, **PostgreSQL** (Only if running without Docker) 



#### 2. Clone the Repository:
```bash 
git clone https://github.com/praveenKavali/MoonTask.git
```
#### 3. Run with Docker
create a .env file in the root directory with your database/email credential, then run
```docker-compose up --build```  
The app will be available at
http://localhost:5173  
#### 4.Manual Setup(Optional)
if you prefer to run the components individually:
**Backend setup:** Configure application.yaml and run the Spring Boot app.  

**Frontend setup:** 
```bash
npm install
npm run dev
```  

### API Endpoints

| Resource     | Method | EndPoint                                   | Description                                                 |
|:-------------|:-------|:-------------------------------------------|:------------------------------------------------------------|
| **User**     | POST   | '/register', '/login'                      | Register user to the database or login to an account.       |
| **User**     | PUT    | '/update'                                  | Updates the user details in database.                       |
| **user**     | DELETE | '/delete'                                  | Delete the user account from the database.                  |
| **user**     | GET    | '/username'                                | Get the username from the database                          |
| **Tasks**    | POST   | '/task/create'                             | Create a new task                                           |
| **Tasks**    | GET    | '/task/all'                                | Retries the all task for logged in user from the data base. |
| **Search**   | GET    | '/task/priority', 'task/status'            | Retries the task based on priority or status                |
| **Search**   | GET    | '/task/search'                             | Filter tasks by provided name                               |
| **Update**   | PATCH  | '/task/{id}/priority', '/task/{id}/status' | Updates the priority and status                             |
| **Complete** | PATCH  | '/task/complete/{id}'                      | Mark a specific task as complete.                           |


### 📸 Screenshots
![homepage](.\Backend\screenshots\task.png)