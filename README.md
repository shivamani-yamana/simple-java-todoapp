# Java Todo List Application

[![Java](https://img.shields.io/badge/Java-11-blue.svg)](https://www.oracle.com/java/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

A command-line Todo List application built in Java that allows users to register, log in, and manage personal tasks. The application leverages MySQL for data storage and uses jBCrypt for secure password handling.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Database Setup](#database-setup)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Future Enhancements](#future-enhancements)
- [License](#license)

## Overview

This project is a lightweight, console-based Todo List manager. Users can create accounts, securely log in, and manage their tasks using simple CRUD (Create, Read, Update, Delete) operations. It's designed to demonstrate core Java programming, database connectivity with JDBC, and secure authentication practices.

## Features

- **User Authentication:**  
  Register and log in with secure password hashing using jBCrypt.

- **Task Management:**  
  - Add new tasks  
  - Modify task names and statuses  
  - Delete tasks  
  - Retrieve tasks sorted by creation date

- **Database Integration:**  
  Uses MySQL to persist user data and task information.

## Prerequisites

Before running the application, ensure you have the following installed:

- **Java Development Kit (JDK) 8 or higher**
- **MySQL Server**
- **MySQL Connector/J (JDBC Driver)**
- **jBCrypt Library**

## Installation

1. **Clone the Repository:**

   ```bash
   git clone https://github.com/<your-username>/java-todo-list.git
   cd java-todo-list
   ```
2. **Compile the Code:**

   Make sure to include the required libraries (MySQL Connector/J and jBCrypt) in your classpath.

   ```bash
   javac -cp ".:lib/mysql-connector-java.jar:lib/jbcrypt.jar" -d bin src/db/DatabaseConnection.java src/auth/UserAuth.java src/todo/TodoManager.java src/mainApp/app.java
   ```

3. **Run the Application:**

   Navigate to the `bin` directory and execute the app:

   ```bash
   java -cp ".:../lib/mysql-connector-java.jar:../lib/jbcrypt.jar" mainApp.app
   ```

## Database Setup

1. **Create the Database:**

   Log in to your MySQL server and run:

   ```sql
   CREATE DATABASE todo_db;
   ```

2. **Create Tables:**

   Execute the following SQL statements:

   ```sql
   -- Users Table
   CREATE TABLE users (
       id INT AUTO_INCREMENT PRIMARY KEY,
       username VARCHAR(50) UNIQUE NOT NULL,
       password VARCHAR(255) NOT NULL,
       createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
   );

   -- Todo List Table
   CREATE TABLE todolists (
       id INT AUTO_INCREMENT PRIMARY KEY,
       username VARCHAR(50) NOT NULL,
       taskName VARCHAR(255) NOT NULL,
       status VARCHAR(50) DEFAULT 'Pending',
       createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE
   );
   ```

3. **Update Database Credentials:**

   If necessary, update the database connection details in the `DatabaseConnection.java` file:

   ```java
   private static final String connectionUrl = "jdbc:mysql://localhost:3306/todo_db";
   private static final String user = "root";
   private static final String password = "root";
   ```

## Usage

1. **Start the Application:**

   Run the app and choose between registering a new user or logging in with an existing account.

2. **Manage Your Tasks:**

   - **View Tasks:** Displays tasks in descending order of creation.
   - **Add Task:** Enter a task name to create a new task.
   - **Modify Task:** Update task name and/or status by specifying the task ID.
   - **Delete Task:** Remove a task by providing its task ID.
   - **Logout:** Exit the session when done.

## Project Structure

```
java-todo-list/
├── src/
│   ├── auth/
│   │   └── UserAuth.java       // User registration and login logic
│   ├── db/
│   │   └── DatabaseConnection.java   // Database connection setup
│   ├── todo/
│   │   └── TodoManager.java    // Task CRUD operations
│   └── mainApp/
│       └── app.java            // Main application with user interface
├── lib/
│   ├── mysql-connector-java.jar  // MySQL JDBC driver
│   └── jbcrypt.jar               // jBCrypt library for password hashing
└── README.md
```

## Future Enhancements

- **Graphical User Interface (GUI):** Create a desktop or web interface for a richer user experience.
- **Advanced Security:** Implement features such as two-factor authentication or account lockout mechanisms.
- **Task Categorization:** Allow users to organize tasks by categories or priorities.
- **RESTful API:** Expose the application functionalities via a web service.

## License

This project is licensed under the [MIT License](LICENSE).

```

Feel free to update the repository URL and any other details as needed for your GitHub project. Enjoy showcasing your project!
