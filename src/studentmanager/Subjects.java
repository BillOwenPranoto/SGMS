package studentmanager;
/**
 * Enum representing all supported school subjects
 * available for grading in the system.
 */
public enum Subjects {
    MATH("Mathematics"),
    ENGLISH("English"),
    PE("PE"),
    HISTORY("History"),
    SCIENCE("Science"),
    ART("Art"),
    IT("Computer Programming (IT)");

    private final String displayName;

    Subjects(String displayName)
    {
        this.displayName = displayName;
    }

    public String GetSubjectName()
    {
        return this.displayName;
    }
}
