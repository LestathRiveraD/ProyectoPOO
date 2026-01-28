# 🎮 ProyectoPOO

ProyectoPOO is a **Java Object-Oriented Programming (OOP)** project that implements a game system with server-side logic, board management, and optional database connectivity.

The project was developed as an academic exercise to demonstrate solid OOP principles, clean architecture, and basic client–server design using Java.

---

## 🧠 Overview

This project is a functional example of **Object-Oriented Programming in Java**. Its main goals are:

- Apply core OOP principles (encapsulation, abstraction, modularity)
- Separate game logic from server logic
- Use DAO / DTO patterns for data handling
- Support database connectivity using MySQL (optionally via Docker)
- Maintain a clear and organized project structure

---

## 🧩 Main Components

| Component | Description |
|---------|-------------|
| `Board.java` | Handles the game board logic |
| `Juego.java` | Core game logic and execution flow |
| `Server.java` | Server that manages game sessions |
| `Facade.java` | Simplifies interaction between system components |
| `JuegoDAO.java` | Data access object |
| `JuegoDTO.java` | Data transfer object |
| `ConexionDB.java` | Generic database connection |
| `ConexionMySQLDocker.java` | MySQL connection via Docker |
| `OnlinePlayer.java` | Represents an online player |

---

## 🚀 Getting Started

### 📋 Requirements

- Java JDK 8 or higher
- IntelliJ IDEA, Eclipse, or VS Code (recommended)
- MySQL (optional)
- Docker (optional, for database setup)

---

## 🔧 Installation & Execution

1. Clone the repository:
   ```bash
   git clone https://github.com/LestathRiveraD/ProyectoPOO.git
   cd ProyectoPOO
2. Open the project in your IDE.

3. Compile and run:
  ```bash
  javac *.java
  java Server
