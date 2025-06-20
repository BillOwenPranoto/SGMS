# Student Grade Management System (Java CLI)

A Java command-line application to manage students and their subject grades with persistent file storage.

This system allows users to add new students, assign grades for predefined subjects, and save/load student data using file I/O. It is designed with object-oriented principles and is a great portfolio project for beginner-to-intermediate Java developers.

---

## 🚀 Features

- Add new students (first, middle, last name + gender)
- Assign grades using enums for fixed subject list
- Calculate and display average grades
- View all students or look up by student ID
- Auto-generate unique student IDs
- Save student data to file on exit
- Load student data from file on startup
- Simple and clear CLI menu system

---

## 💾 File Format: `listOfStudents.txt`

Each student is saved as a single line:
ID,FIRST,MIDDLE,LAST,GENDER,SUBJECT=GRADE,SUBJECT=GRADE,...

This file is automatically loaded on startup and updated on exit.
Make sure listOfStudents.txt is in the same directory as your .class files, or it will start fresh.
---

## 📁 Project Structure

Student Grade Management System/

├── Main.java

├── Student.java

├── StudentManager.java

├── Subjects.java

├── listOfStudents.txt

├── README.md


---
## 🛠 Technologies Used:
Java (JDK 17+ recommended)

EnumMap, Map, and collections

File I/O (BufferedReader, PrintWriter)

Object-Oriented Programming (OOP)

Command-Line Interface (CLI)

---
## 📌 Future Improvements
Edit or delete students

Sort students by name or grades

Search by name (partial match)

GUI version using JavaFX or Swing

Export printable reports

---
## 👤 Author
Bill Owen Pranoto

GitHub: BillOwenPranoto

---
## 🧑‍💻 How to Run

### Compile

```bash
javac Main.java
java Main


