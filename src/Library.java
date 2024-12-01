import java.util.concurrent.Semaphore;
import java.util.Map;
import java.util.HashMap;

public class Library {
    private Map<String, Semaphore> bookStock = new HashMap<>(); // Tracks the stock of books with semaphores for availability
    private volatile boolean isOpen = true; // Indicates whether the library is open (volatile ensures thread visibility)

    /**
     * Constructor to initialize the library with a random stock of 10 books.
     * Each book has a random number of available copies (1 to 5).
     */
    public Library() {
        for (int i = 1; i <= 10; i++) {
            bookStock.put("Book" + i, new Semaphore((int) (Math.random() * 5 + 1), true));
            // Each book is assigned a semaphore with fair access policy
        }
    }

    /**
     * Synchronized method to toggle the library's open status.
     * Ensures only one thread can update the library's status at a time.
     * @param open Whether the library should be open or closed.
     */
    public synchronized void toggleLibraryOpen(boolean open) {
        isOpen = open;
        System.out.println("Бібліотека " + (isOpen ? "відкрита" : "закрита")); // Prints the current status of the library
    }

    /**
     * Checks if the library is open.
     * @return true if the library is open, false otherwise.
     */
    public boolean isOpen() {
        return isOpen; // Returns the current status of the library
    }

    /**
     * Allows a user to borrow a book if the library is open and the book is available.
     * @param bookName The name of the book to borrow.
     */
    public void borrowBook(String bookName) {
        if (!isOpen) {
            System.out.println("Бібліотека закрита. Ви не можете взяти книгу."); // Prevents borrowing if the library is closed
            return;
        }
        Semaphore semaphore = bookStock.get(bookName); // Retrieves the semaphore for the requested book
        if (semaphore != null && semaphore.tryAcquire()) {
            // Attempts to acquire a permit for the book
            System.out.println("Книга " + bookName + " взята."); // Confirms successful borrowing
        } else {
            System.out.println("Книга " + bookName + " недоступна або не існує.");
            // Handles cases where the book is unavailable or doesn't exist
        }
    }

    /**
     * Allows a user to return a book if the library is open.
     * @param bookName The name of the book to return.
     */
    public void returnBook(String bookName) {
        if (!isOpen) {
            System.out.println("Бібліотека закрита. Ви не можете повернути книгу."); // Prevents returning if the library is closed
            return;
        }
        Semaphore semaphore = bookStock.get(bookName); // Retrieves the semaphore for the returned book
        if (semaphore != null) {
            semaphore.release(); // Releases a permit, indicating the book is now available
            System.out.println("Книга " + bookName + " повернута."); // Confirms successful return
        } else {
            System.out.println("Невідома книга: " + bookName);
            // Handles cases where the book being returned is not recognized
        }
    }
}
