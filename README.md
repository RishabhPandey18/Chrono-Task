# ⏳ ChronoTask – Task Management Application

**ChronoTask** is a Java-based desktop task management application designed to help users organise, track, and manage their daily tasks through a graphical user interface.

Built using **Java Swing, JDBC, and SQLite**, the application provides a simple way to create tasks, monitor their completion status, assign priorities, and maintain an activity history.

---

## 📌 Overview

Managing multiple tasks can become difficult without a proper tracking system.

ChronoTask provides a desktop-based solution where users can:

- Create new tasks with titles and descriptions.
- Assign deadlines to tasks.
- Set task priorities (High, Medium, Low).
- View their existing tasks.
- Mark tasks as completed.
- Delete tasks when they are no longer needed.
- View an activity log of their actions.

The application stores task information in an SQLite database, allowing tasks to remain available even after the application is closed.

---

## ✨ Features

### 1. Add Tasks
Create tasks by entering:
- Task title
- Description
- Due date
- Priority level

### 2. View Tasks
View the available tasks along with their:
- Title
- Description
- Due date
- Priority
- Completion status

### 3. Complete Tasks
Mark pending tasks as completed to keep track of finished work.

### 4. Delete Tasks
Remove tasks that are no longer required.

### 5. Refresh Tasks
Refresh the task list to retrieve the latest information from the database.

### 6. Activity Log
View a history of actions performed during the current application session, such as adding, updating, and deleting tasks.

---

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java | Core application development |
| Java Swing | Graphical User Interface |
| JDBC | Database connectivity |
| SQLite | Local database storage |
| Collections Framework | Managing task and activity data |
| Multithreading | Performing database operations in the background |
| Exception Handling | Handling application and database errors |

---

## 🧠 Java Concepts Implemented

ChronoTask demonstrates several important Object-Oriented Programming and Java concepts.

### Object-Oriented Programming
- **Inheritance:** The `Task` class inherits from the abstract `AuditableEntity` class.
- **Polymorphism:** The application uses the `TaskRepository` interface through its SQLite implementation.
- **Abstraction:** Interfaces and abstract classes separate responsibilities.
- **Encapsulation:** Task data is maintained using private fields and public methods.

### Collections and Generics
- `ArrayList` for storing collections of tasks and activities.
- `List` for working with task collections.
- `DefaultListModel<Task>` for displaying tasks in the Swing interface.
- Generics for type-safe collection handling.

### Exception Handling
Custom exception handling is implemented through the `TaskException` class to manage application errors.

### Multithreading and Synchronization
- `ExecutorService` executes database operations in a background thread.
- `SwingUtilities.invokeLater()` updates GUI components on the Event Dispatch Thread.
- Synchronization is used in repository operations and activity logging to protect shared data.

### JDBC Connectivity
JDBC connects the Java application to the SQLite database and performs database operations using SQL queries and prepared statements.

---

## 📂 Project Structure


ChronoTask/
│
├── lib/
│   └── sqlite-jdbc.jar
│
├── src/
│   └── com/
│       └── chronotask/
│           │
│           ├── Main.java
│           │
│           ├── model/
│           │   ├── AuditableEntity.java
│           │   ├── Task.java
│           │   └── Priority.java
│           │
│           ├── db/
│           │   ├── DatabaseManager.java
│           │   ├── TaskRepository.java
│           │   └── SQLiteTaskRepository.java
│           │
│           ├── service/
│           │   ├── TaskService.java
│           │   └── ActivityLog.java
│           │
│           ├── exception/
│           │   └── TaskException.java
│           │
│           └── ui/
│               └── ChronoTaskFrame.java
│
├── out/
│
├── .gitignore
└── README.md


---

## ⚙️ Installation and Setup

Follow these steps to run ChronoTask on your computer.

### Prerequisites

Make sure you have:

1. Java Development Kit (JDK 17 or newer).
2. Visual Studio Code or another Java-compatible IDE.
3. SQLite JDBC driver (`sqlite-jdbc.jar`).

### Step 1: Clone the Repository

Open your terminal and execute:


git clone https://github.com/RishabhPandey18/Chrono-Task.git

Navigate to the project directory:


cd Chrono-Task


### Step 2: Verify the JDBC Driver

Make sure the SQLite JDBC driver is present inside the `lib` directory:

lib/sqlite-jdbc.jar


If it is not included in the repository, download the SQLite JDBC driver and place it inside the `lib` directory with that filename.

### Step 3: Compile the Java Files

On Windows PowerShell, run:


javac -cp "lib\sqlite-jdbc.jar" -d out (Get-ChildItem -Recurse src -Filter *.java | ForEach-Object { $_.FullName })


This compiles the Java source files and stores the compiled classes inside the `out` directory.

### Step 4: Run the Application

Execute:


java -cp "out;lib\sqlite-jdbc.jar" com.chronotask.Main


The ChronoTask desktop application should now launch.

---

## 🗄️ Database

ChronoTask uses SQLite as its local database.

The database is initialized automatically when the application starts.

The `tasks` table stores:

| Column | Description |
|---|---|
| id | Unique task identifier |
| title | Task title |
| description | Task description |
| due_date | Task deadline |
| priority | Task priority |
| completed | Task completion status |

The database file is named:


chronotask.db


It is created in the application's working directory if it does not already exist.

Task records remain stored between application sessions.

**Note:** The activity log is currently maintained in memory and is reset when the application closes.

---

## 🎯 Project Objective

The primary objective of ChronoTask is to demonstrate the practical application of Java programming concepts through a working desktop application.

The project combines object-oriented programming, database connectivity, collections, exception handling, multithreading, synchronization, and GUI development into one application.

It also demonstrates how different components of a Java application can be organised into separate packages to improve code structure and maintainability.

---

## 👨‍💻 Developer

Developed as an academic Java project.

**Project Name:** ChronoTask  
**Language:** Java  
**Database:** SQLite  
**Interface:** Java Swing

---

*ChronoTask – Organise your tasks. Manage your time.*
