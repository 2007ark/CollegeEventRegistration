# College Event Registration Portal

A desktop-based Event Registration and Management Portal built using Java Swing and MySQL JDBC.

## 🚀 Features
- **Student Registration:** Full validation for student name, email, class/sem, department, and event selection.
- **Admin Management Gate:** Secure login authentication (`admin` / `admin123`) to view and modify registration records.
- **Full CRUD Support:** Add, view, edit, and delete student registration records in real time.
- **Search & Filter:** Live text filtering across records.
- **Export to CSV:** Export student lists directly to CSV format.
- **Dynamic Theme Engine:** Switch between Light Mode and Dark Mode.
- **Live Status Tracker:** Real-time database connection status monitor.

## 🛠️ Tech Stack & Environment
- **Language:** Java (JDK 8+)
- **GUI Framework:** Java Swing (`javax.swing`)
- **Database:** MySQL 8.0 (`LMCST` schema)
- **Database Connectivity:** JDBC Driver (`mysql-connector-j`)
- **IDE:** Eclipse

## 🗄️ Database Setup
Run the following SQL script in your MySQL Workbench or command line:

```sql
CREATE DATABASE IF NOT EXISTS LMCST;
USE LMCST;

CREATE TABLE IF NOT EXISTS registrations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    class_name VARCHAR(50) NOT NULL,
    department VARCHAR(50) NOT NULL,
    event_name VARCHAR(100) NOT NULL,
    registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);