import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Library library = new Library(); // Instance of the library used for managing books
    private static Scanner scanner = new Scanner(System.in); // Scanner for reading user input
    private static Student user = new Student("Користувач-Студент", library); // Main user (student) interacting with the library
    private static List<Thread> testStudents = new ArrayList<>(); // List to hold threads for simulated test students
    private static int studentCount = 0; // Counter to track the number of test students

    public static void main(String[] args) {
        System.out.println("Ласкаво просимо в бібліотеку!"); // Welcome message
        userInterface(); // Starts the user interface loop
    }

    /**
     * Handles user interaction via a command-line interface.
     * Offers commands for borrowing and returning books, toggling library status, and creating test students.
     */
    private static void userInterface() {
        while (true) {
            // Displays the available commands to the user
            System.out.println("Команди: 1 - взяти книгу, 2 - повернути книгу, 3 - відкрити/закрити бібліотеку, 4 - створити тестових студентів, 0 - вийти");
            System.out.print("Введіть команду: ");
            String command = scanner.nextLine(); // Reads user input

            // Executes the corresponding action based on user input
            switch (command) {
                case "1":
                    handleBorrow();
                    break;
                case "2":
                    handleReturn();
                    break;
                case "3":
                    toggleLibrary();
                    break;
                case "4":
                    createTestStudents();
                    break;
                case "0":
                    shutdownTestStudents(); // Stops all test student threads before exiting
                    System.out.println("Програма завершується...");
                    System.exit(0); // Terminates the program
                    break;
                default:
                    System.out.println("Неправильна команда. Спробуйте знову."); // Handles invalid input
            }
        }
    }

    /**
     * Handles the process of borrowing a book by prompting the user for the book's name.
     */
    private static void handleBorrow() {
        System.out.print("Введіть назву книги: ");
        String bookName = scanner.nextLine(); // Reads the book's name from the user
        user.borrowBook(bookName); // Attempts to borrow the book for the user
    }

    /**
     * Handles the process of returning a book by prompting the user for the book's name.
     */
    private static void handleReturn() {
        System.out.print("Введіть назву книги: ");
        String bookName = scanner.nextLine(); // Reads the book's name from the user
        user.returnBook(bookName); // Attempts to return the book for the user
    }

    /**
     * Toggles the library's open or closed status based on user input.
     */
    private static void toggleLibrary() {
        System.out.print("Ви хочете відкрити (1) чи закрити (2) бібліотеку? ");
        String option = scanner.nextLine(); // Reads the user's choice
        boolean open = "1".equals(option); // Determines if the library should be open
        library.toggleLibraryOpen(open); // Updates the library's status
    }

    /**
     * Creates multiple simulated test students as threads to interact with the library.
     */
    private static void createTestStudents() {
        System.out.print("Введіть кількість студентів для тестування: ");
        int count = Integer.parseInt(scanner.nextLine()); // Reads the number of test students
        studentCount = count; // Updates the student count
        testStudents.clear(); // Clears any existing test students
        for (int i = 0; i < count; i++) {
            // Creates a new thread for each test student
            Thread student = new Thread(new TestStudent("Тестовий Студент " + (i + 1), library, i, count));
            student.start(); // Starts the thread
            testStudents.add(student); // Adds the thread to the list
        }
    }

    /**
     * Stops all running test student threads and clears the list.
     */
    private static void shutdownTestStudents() {
        for (Thread student : testStudents) {
            student.interrupt(); // Interrupts each thread to stop execution
        }
        testStudents.clear(); // Clears the list of test students
    }
}
