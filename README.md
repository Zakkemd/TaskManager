# Task Manager Project

This is a simple task manager project built using Spring Boot, Hibernate, and MySQL.

## Prerequisites

- JDK 18
- Maven
- MySQL Server

## Instructions for setting up the project locally

1. Download and install JDK 18.

2. Download and install Maven.

3. Download and install MySQL Server. You can find the installer (https://dev.mysql.com/downloads/mysql/).

4. Download and install MySQL Workbench for easier database management: https://dev.mysql.com/downloads/workbench/  (optional).

5. Run the following SQL script to create the schema, tables, and columns in MySQL:

CREATE SCHEMA taskmanager;

USE taskmanager;

CREATE TABLE users (
id BIGINT AUTO_INCREMENT PRIMARY KEY,

username VARCHAR(255) NOT NULL UNIQUE,

password VARCHAR(255) NOT NULL,

email VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE tasks (
id BIGINT AUTO_INCREMENT PRIMARY KEY,

name VARCHAR(255) NOT NULL,

description VARCHAR(255),

deadline DATETIME NOT NULL,

status ENUM('TODO', 'IN_PROGRESS', 'DONE') NOT NULL,

user_id BIGINT NOT NULL,

FOREIGN KEY (user_id) REFERENCES users(id)
);

## Clone the Repository

Open a terminal/command prompt and run the following command:

git clone https://github.com/Zakkemd/TaskManager.git

This command will create a folder named TaskManager in your current directory, containing the project files.

Download as ZIP
Alternatively, you can download the project as a ZIP file from the GitHub web interface:

Go to the repository page on GitHub (e.g., https://github.com/Zakkemd/TaskManager.git)
Click the green "Code" button near the top right corner of the page.
In the dropdown menu, click "Download ZIP."
Once the ZIP file is downloaded, extract its contents to a folder on your machine.

## Configure the `application.properties` file with your database connection details. Replace the following lines with your own MySQL connection settings:

spring.datasource.url=jdbc:mysql://localhost:3306/taskmanager?useSSL=false

spring.datasource.username=YOUR_MYSQL_USERNAME

spring.datasource.password=YOUR_MYSQL_PASSWORD



##  Running the application:
Open a terminal/command prompt. For Windows, open the terminal as administrator.
Navigate to the project's root directory using the cd command.
For example, if your project is located in C:\Projects\TaskManager, you would type: cd C:\Projects\TaskManager.

There are two ways to run the application: using mvn spring-boot:run or java -jar. Choose the method that suits your needs.

Method 1: Using mvn spring-boot:run (recommended for development and testing)

Open a terminal/command prompt.
Navigate to the project's root directory using the command cd <path_to_project_root_directory>.
Run the application with the command: mvn spring-boot:run.
The application will start and listen on port 8080.

Method 2: Using java -jar (recommended for running the packaged JAR file)

Open a terminal/command prompt.
Navigate to the project's root directory using the command cd <path_to_project_root_directory>.
Build the project with the command: mvn clean install. This will create an executable JAR file in the target directory.
Navigate to the target directory using the command cd target.
Run the application with the command: java -jar TaskManager-1.0-SNAPSHOT.jar (replace TaskManager-1.0-SNAPSHOT.jar with the name of your JAR file if it's different).
The application will start and listen on port 8080.
After starting the application using either method, you can access the API by sending requests to http://localhost:8080.

## API Endpoints

### TaskController endpoints:
- Get a task by ID: `GET /api/tasks/{taskId}`
- Get all tasks: `GET /api/tasks`
- Get tasks by user: `GET /api/tasks/user/{userId}`
- Get tasks by status: `GET /api/tasks/status/{status}`
- Get tasks by deadline range: `GET /api/tasks/deadline?start={start}&end={end}`
- Add a new task: `POST /api/tasks`
- Update a task: `PUT /api/tasks/{taskId}`
- Delete a task: `DELETE /api/tasks/{taskId}`

### UserController endpoints:
- Get a user by ID: `GET /api/users/{userId}`
- Get all users: `GET /api/users`
- Get a user by username: `GET /api/users/username/{username}`
- Add a new user: `POST /api/users`
- Delete a user: `DELETE /api/users/{userId}`





