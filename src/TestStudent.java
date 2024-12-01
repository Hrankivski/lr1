public class TestStudent extends Student {
    private static final int MAX_ACTIONS = 15; // Maximum number of actions a student can perform
    private static int[] actionsPerformed; // Shared array to track actions performed by all students
    private int studentId; // Unique identifier for this student
    private int localActions = 0; // Counter for the actions performed by this specific student

    /**
     * Constructor to initialize a test student with their name, library reference, ID, and total number of students.
     * Ensures the shared `actionsPerformed` array is initialized correctly for all students.
     * @param name The name of the student.
     * @param library The library instance the student interacts with.
     * @param studentId The unique ID of this student.
     * @param totalStudents The total number of students participating.
     */
    public TestStudent(String name, Library library, int studentId, int totalStudents) {
        super(name, library);
        this.studentId = studentId;
        if (actionsPerformed == null || actionsPerformed.length != totalStudents) {
            actionsPerformed = new int[totalStudents]; // Initializes shared action tracker if needed
        }
    }

    /**
     * The `run` method implements the student's behavior during the test.
     * The student performs up to `MAX_ACTIONS` random actions, like borrowing or returning books.
     * Some actions include test-specific behaviors, such as attempting to close the library.
     */
    @Override
    public void run() {
        while (localActions < MAX_ACTIONS) {
            synchronized (TestStudent.class) {
                // Ensures thread-safe updates to shared resources
                if (actionsPerformed[studentId] >= MAX_ACTIONS) {
                    // Exits if this student has reached their action limit
                    break;
                }

                // Specific test behaviors
                if (localActions == 0) {
                    // First action: attempt to borrow "Book1"
                    System.out.println(name + " спробує взяти Book1.");
                    borrowBook("Book1");
                } else if (localActions == 12 && studentId == 0) {
                    // The first student attempts to close the library after 12 actions
                    System.out.println(name + " закриває бібліотеку.");
                    library.toggleLibraryOpen(false);
                } else {
                    // Random borrowing or returning actions
                    String bookName = "Book" + (int) (Math.random() * 10 + 1);
                    if (Math.random() > 0.5) {
                        System.out.println(name + " хоче взяти книгу: " + bookName);
                        borrowBook(bookName);
                    } else {
                        System.out.println(name + " хоче повернути книгу: " + bookName);
                        returnBook(bookName);
                    }
                }

                localActions++; // Increment this student's action counter
                actionsPerformed[studentId]++; // Increment the global action counter for this student
            }

            try {
                // Pause for a random duration between actions to simulate real-life behavior
                Thread.sleep((int) (Math.random() * 1000 + 500));
            } catch (InterruptedException e) {
                // Handles interruption gracefully, typically during program shutdown
                System.out.println(name + " перервано.");
            }
        }
        // Indicates that this student has completed their actions
        System.out.println(name + " завершив свої " + MAX_ACTIONS + " дій.");
    }
}
