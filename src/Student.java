public class Student implements Runnable {
    protected String name; // The name of the student
    protected Library library; // Reference to the library the student interacts with

    /**
     * Constructor to initialize a student with a name and a library reference.
     * @param name The name of the student.
     * @param library The library instance the student will use.
     */
    public Student(String name, Library library) {
        this.name = name;
        this.library = library;
    }

    /**
     * The run method required by the Runnable interface.
     * This is intentionally left empty as this class serves as a base for other student types.
     */
    @Override
    public void run() {
        // No implementation for base class; functionality is defined in subclasses
    }

    /**
     * Allows the student to attempt borrowing a book if the library is open.
     * @param bookName The name of the book to borrow.
     */
    public void borrowBook(String bookName) {
        if (library.isOpen()) {
            library.borrowBook(bookName); // Tries to borrow the book from the library
        } else {
            System.out.println(name + ": Бібліотека зараз закрита."); // Displays a message if the library is closed
        }
    }

    /**
     * Allows the student to attempt returning a book if the library is open.
     * @param bookName The name of the book to return.
     */
    public void returnBook(String bookName) {
        if (library.isOpen()) {
            library.returnBook(bookName); // Tries to return the book to the library
        } else {
            System.out.println(name + ": Бібліотека зараз закрита."); // Displays a message if the library is closed
        }
    }
}
