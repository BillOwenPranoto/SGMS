import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class Student {

    /**
     * Student class to handle student attributes. There are:
     * 1. studentID = A mandatory field for student's unique identifier.
     * 2. First name
     * 3. Middle name, which can be left empty.
     * 4. Last name
     * 5. Gender (M,F,U)
     * 6. And a EnumMap for the student's grades.
     */
    private int studentId;
    private String firstName;
    private String middleName;
    private String lastName;
    private Character studentGender;
    private final EnumMap<Subjects, Float> studentGrades = new EnumMap<>(Subjects.class);

    /**
     * Constructors and public functions for students
     * GetFullDetails() : Retrieve student's full name, and the average grades.
     * enterGrade(Subjects subject, float num) -> take the subject's name and the grade and assign it to a student.
     * calculateAverageGrade() : return the average grade of a student.
     * GetAverageGrade() : return a letter version of student's average grade.
     */

    public Student(Character gender) {
        if(gender == 'F')
        {
            //this.studentId += 1;
            this.firstName = "Jane";
            this.lastName = "Doe";
            this.studentGender = gender;
        }

        else if (gender == 'M')
        {
            //this.studentId += 1;
            this.firstName = "John";
            this.lastName = "Doe";
            this.studentGender = gender;
        }
    }

    public Student(int id,String fName, String lName, Character gender) {
        this.studentId = id;
        this.firstName = fName;
        this.lastName = lName;
        this.studentGender = gender;
    }

    public Student(int id, String fName, String mName, String lName, Character gender) {
        this.studentId = id;
        this.firstName = fName;
        this.middleName = mName;
        this.lastName = lName;
        this.studentGender = gender;
    }

    public void GetFullDetails() {
        System.out.println("Student ID: " + this.GetId());
        System.out.println("First Name:" + this.firstName);
        if(this.middleName == null)
        {
            System.out.println("Middle Name: -");
        }
        else {
            System.out.println("Middle Name: " + this.middleName);
        }
        System.out.println("Last Name: " + this.lastName);
        System.out.println("Gender: " + this.studentGender);
        System.out.println(this.firstName + "'s average grade: " + this.GetAverageGrade());
    }

    public void enterGrade(Subjects subject, float num) throws IllegalArgumentException {
        if (num < 0 || num > 100) {
            throw new IllegalArgumentException("Grade must be between 0 and 100.");
        }
        this.studentGrades.put(subject, num);
    }

    public void SetId(int id) {
        this.studentId = id;
    }

    public int GetId() {
        return this.studentId;
    }

    public float calculateAverageGrade() {
        float total = 0;

        if(studentGrades.isEmpty())
        {
            return total;
        }

        else {
            for(float g : studentGrades.values())
            {
                total += g;
            }
        }
        return total / studentGrades.size();
    }

    public Character GetAverageGrade() {
        char letter = ' ';

        float gradeNum = calculateAverageGrade();

        if(gradeNum >= 80.f)
        {
            return letter = 'A';
        }

        else if (gradeNum >= 60.f)
        {
            return letter = 'B';
        }

        else if (gradeNum >= 50.f)
        {
            return letter = 'C';
        }

        else if (gradeNum >= 0.f)
        {
            return letter = 'F';
        }

        else {
            return letter = 'N';
        }
    }


    // Handling File I/O

    public String toFileString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(this.studentId).append(",");
        stringBuilder.append(this.firstName).append(",");
        stringBuilder.append(this.middleName).append(",");
        stringBuilder.append(this.lastName).append(",");
        stringBuilder.append(this.studentGender).append(",");

        for(Map.Entry<Subjects, Float> entry : studentGrades.entrySet()) {
            String subjectString = entry.getKey().toString();
            String gradeString = entry.getValue().toString();

            stringBuilder.append(subjectString).append("=").append(gradeString).append(",");
        }

        if(!studentGrades.isEmpty()) {
            stringBuilder.setLength(stringBuilder.length() -1);
        }

        return stringBuilder.toString();
    }

    public static Student fromFileString(String line) {

        String[] parts = line.split(",");

        int id = Integer.parseInt(parts[0]);
        String fName = parts[1];
        String mName = parts[2];
        String lName = parts[3];
        char gender = parts[4].charAt(0);

        Student student = new Student(id,fName,mName,lName,gender);

        for (int i = 5; i < parts.length; i++) {
            String[] gradeParts = parts[i].split("=");
            Subjects subjects = Subjects.valueOf(gradeParts[0]);
            float grade = Float.parseFloat(gradeParts[1]);

            student.enterGrade(subjects,grade);
        }

        return student;
    }
}
