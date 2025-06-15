package studentmanager;
import java.util.EnumMap;
import java.util.Map;

/**
 * Represents a student with personal information and subject grades.
 * Each student is assigned a unique ID and may have grades recorded
 * for multiple predefined subjects.
 */
public class Student {

    private int studentId;
    private String firstName;
    private String middleName;
    private String lastName;
    private Character studentGender;
    private final EnumMap<Subjects, Float> studentGrades = new EnumMap<>(Subjects.class);

    /**
     * Constructs a student with full name and gender.
     *
     * @param id The unique student ID.
     * @param fName The student's first name.
     * @param mName The student's middle name (can be blank).
     * @param lName The student's last name.
     * @param gender The student's gender ('M', 'F', or 'U').
     */
    public Student(int id, String fName, String mName, String lName, Character gender) {
        this.studentId = id;
        this.firstName = fName;
        this.middleName = mName;
        this.lastName = lName;
        this.studentGender = gender;
    }

    public Student(int id,String fName, String lName, Character gender) {
        this.studentId = id;
        this.firstName = fName;
        this.lastName = lName;
        this.studentGender = gender;
    }

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

    /**
     * Adds or updates a grade for a given subject.
     *
     * @param subject The subject to assign a grade for.
     * @param num The grade value (0–100).
     * @throws IllegalArgumentException if grade is out of valid range.
     */
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

    /**
     * Determine the alphabetical grade based on the student's average grade
     * @return A letter e.g A,B,C,F or N based on the average grade.
     */
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

    /**
     * Converts the student data into a CSV-compatible string for saving to file.
     *
     * @return A single-line string representing the student and their grades.
     */
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

    /**
     * Reconstructs a Student object from a line in the file.
     *
     * @param line A comma-separated string in the expected file format.
     * @return A Student object with parsed values and grades.
     * @throws IllegalArgumentException if format is invalid or enum parsing fails.
     */
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
