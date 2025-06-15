package studentmanager;
/**
 * Entry point of the Student Grade Management System application.
 * <p>
 * This class contains the {@code main} method which initializes and
 * runs the student manager CLI interface. It is responsible for launching
 * the program and delegating functionality to {@link StudentManager}.
 */
public class Main {

    /**
     * Launches the Student Grade Management System CLI.
     *
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {

        StudentManager program = new StudentManager();

        program.run();

    }
}
