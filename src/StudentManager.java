import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StudentManager {
    private static final String FILE_NAME = "listOfStudents.txt";

    // User input handler
    private final Scanner scanner = new Scanner(System.in);
    String userChoice;

    // Multiple student handler
    private final Map<Integer, Student> studentsMap = new HashMap<>();
    private int nextStudentId = 100001;

    public void run() {
        loadStudentsFromFile();
        label:
        while(true)
        {
            ShowMenu();
            userChoice = scanner.nextLine().trim();

            switch (userChoice) {
                case "1":
                    handleAddStudent();
                    break;

                case "2":
                    handleViewAllStudents();
                    break;

                case "3":
                    handleStudentLookUp();
                    break;

                case "4":
                    System.out.println("Exiting program...");
                    saveStudentsToFile();
                    break label;
                default:
                    System.out.println("Choice is unrecognized! Please retype.");
            }
        }

    }

    private Student createStudentFromInput() {
        System.out.println("**Enter the student's First Name: ");
        String fName = scanner.nextLine().trim();

        System.out.println("Enter the student's Middle Name (Leave empty by pressing 'Enter' if none): ");
        String mName = scanner.nextLine().trim();

        System.out.println("**Enter the student's Last Name: ");
        String lName = scanner.nextLine().trim();

        if (fName.isBlank() || lName.isBlank()) {
            System.out.println("\n-->First and Last name are required. Please re-enter.<--");
            return createStudentFromInput();
        }

        System.out.println("**Enter the student's gender (M/F): ");
        String genderInput = scanner.nextLine().trim().toUpperCase();

        char gender;
        if (genderInput.isEmpty()) {
            System.out.println("\n>--No gender provided. Defaulting to U (unknown).<--");
            gender = 'U';
        } else {
            gender = genderInput.charAt(0);
            if (gender != 'M' && gender != 'F') {
                System.out.println("\n-->Invalid gender input. Defaulting to U (unknown).<--");
                gender = 'U';
            }
        }

        if (mName.isBlank()) {
            return new Student(nextStudentId,fName, lName, gender);
        } else {
            return new Student(nextStudentId,fName, mName, lName, gender);
        }
    }

    private void enterGrades(Student student) {
        while (true) {
            System.out.println("\nAVAILABLE SUBJECTS: ");
            for (Subjects s : Subjects.values()) {
                System.out.println("-" + s);
            }

            System.out.print("Enter subject name (or 'done' to finish): ");
            String subjectInput = scanner.nextLine().trim();

            if (subjectInput.equalsIgnoreCase("done")) break;

            if (subjectInput.isEmpty()) {
                System.out.println("\n-->Subject cannot be empty.<--");
                continue;
            }

            Subjects subject;
            try {
                subject = Subjects.valueOf(subjectInput.toUpperCase());
            } catch(IllegalArgumentException e) {
                System.out.println("\n-->Invalid subject. Please enter a valid subject name! <--");
                continue;
            }

            System.out.print("Enter grade for " + subjectInput.toUpperCase() + ": ");
            try {
                float grade = Float.parseFloat(scanner.nextLine());
                student.enterGrade(subject, grade);
            } catch (NumberFormatException e) {
                System.out.println("\n-->Invalid grade format. <--");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    private void ShowMenu() {
        System.out.println("\n========================== STUDENT MENU ================================");
        System.out.println("Welcome! I'm a Student Grade Management Program. How may I help? ");
        System.out.println("========================================================================");
        System.out.println("1. Add a new student");
        System.out.println("2. View all students");
        System.out.println("3. View a specific student's details");
        System.out.println("4. Exit");
        System.out.println("==========================================================================");
        System.out.print("Please enter your choice (1-4): ");
    }

    private void handleAddStudent() {
        Student student = createStudentFromInput();
        enterGrades(student);
        student.SetId(nextStudentId);
        studentsMap.put(nextStudentId,student);
        nextStudentId++;
    }

    private void handleViewAllStudents() {
        if(studentsMap.isEmpty()) {
            System.out.println("\n-->No students currently on the list, please add at least one!<--");
        }

        else {
            for (Student s : studentsMap.values())
            {
                System.out.println("================");
                s.GetFullDetails();
            }
        }
    }

    private void handleStudentLookUp() {
        if(studentsMap.isEmpty()) {
            System.out.println("\n-->No students currently on the list, please add at least one!<--");
        }

        else {
            System.out.println("\nPlease enter the student ID here:");
            String userInput = scanner.nextLine().trim();
            try {
                int studentId = Integer.parseInt(userInput);
                Student s = studentsMap.get(studentId);

                if(s != null) {
                    s.GetFullDetails();
                } else {
                    System.out.println("\n-->No student was found with this ID!<--");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n-->Invalid ID. Please enter a valid numeric student ID.<--");
            }

        }
    }

    private void saveStudentsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))){
            for (Student s : studentsMap.values()) {
                writer.println(s.toFileString());
            }
            System.out.println("Student safely stored to a file called: " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Error saving student data: "+ e.getMessage());
        }
    }

    private void loadStudentsFromFile() {
        File file = new File(FILE_NAME);

        if(!file.exists()) {
            System.out.println("No existing student file found under the name " + FILE_NAME + ", starting fresh.");
            //System.out.println("Attempting to read file at: " + file.getAbsolutePath());
            return;
        }

        int highestId = 0;

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Student s = Student.fromFileString(line);
                studentsMap.put(s.GetId(),s);

                if (s.GetId() > highestId) {
                    highestId = s.GetId();
                }
            }
            nextStudentId = highestId + 1;
            System.out.println("Successfully loaded students from file.");
        } catch (IOException e) {
            System.out.println("Error loading student data: " + e.getMessage());
        }
    }
}
