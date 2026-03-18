# Moon Task

**Moon Task** is a task manager application. Here you can add task. Filter or search task and can perform other task related operations.

## ✨ Features

* **User Authentication:** Secure Login and Registration using JWT (JSON Web Tokens).
* **Task Management:** Full CRUD operations (Create, Read, Update) for personal tasks.
* **Dynamic UI:** Interactive task dashboard built with React and CSS Modules.
* **Task Categorization:** Sort and filter tasks by Priority and Status.
* **Smart Completion:** Automatic server-side timestamping when a task is marked as finished.
* **Containerized:** Local development environment managed via Docker.

## ⚙️ Tech Stack

**Frontend:**  React, HTML5, CSS(module), JavaScript  
**Backend:**  SpringBoot, Java  
**Database:** PostgreSQL(running via docker)  
**Testing:** (backend) JUnit, Mockito
## 🚀 Getting Started

**1. Prerequisites:** JDK 22, Node.js, PostgreSQl  

**2. Clone the Repository:** 
```bash 
git clone https://github.com/praveenKavali/MoonTask.git
```
**3. Backend setup:** Configure application.yaml and run the Spring Boot app.  

**4. Frontend setup:** 
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