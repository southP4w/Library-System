package model.books;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import model.users.Transaction;

/**
 * The Book class. Provides properties and methods for creating a Book object.
 */
public class Book implements Comparable<Book>, Serializable
{
	private static final long serialVersionUID = 6L;

	private String title, isbn, authorsAsString;
	private LinkedList<Author> authors;
	private Transaction borrowHistory;
	private LocalDateTime timeBorrowedAt, timeDue, timeReturned;
	private String timeBorrowedAtString, timeDueString, timeReturnedString;
	private int availableInventory, totalInventory;

	/**
	 * 3-param constructor for a Book Object
	 *
	 * @param title   The title of the Book
	 * @param isbn    The ISBN
	 * @param authors The Author or Authors
	 */
	public Book(String title, String isbn, Author... authors) {
		this.title = title;
		this.isbn = isbn;
		this.authors = new LinkedList<>();
		for (Author author : authors)
			this.authors.addLast(author);
		updateAuthorsAsString();
		availableInventory = (totalInventory = (int) (Math.random() * (20 - 5) + 1));
	}

	/**
	 * Single-param constructor for a Book Object
	 *
	 * @param title The title of the Book
	 */
	public Book(String title) {
		this.title = title;
		availableInventory = (totalInventory = (int) (Math.random() * (20 - 5) + 1));
	}

	/**
	 * Default constructor for a Book Object
	 */
	public Book() {
		availableInventory = (totalInventory = (int) (Math.random() * (20 - 5) + 1));
	}

	/**
	 * Increment this Book's available inventory by 1.
	 */
	public void incrementAvailableInventory() {
		availableInventory++;
	}

	/**
	 * Decrement this Book's available inventory by 1.
	 */
	public void decrementAvailableInventory() {
		availableInventory--;
	}

	/**
	 * Increment this Book's total inventory by 1.
	 */
	public void incrementTotalInventory() {
		totalInventory++;
	}

	/**
	 * Decrement this Book's total inventory by 1.
	 */
	public void decrementTotalInventory() {
		totalInventory--;
	}

	/**
	 * Boolean method to check whether this Book is overdue
	 *
	 * @return true if overdue, false otherwise.
	 */
	public boolean isOverDue() {
		return this.timeDue.isBefore(LocalDateTime.now());
	}

	/**
	 * Retrieve the title of this Book.
	 *
	 * @return this Book's title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set this Book's title.
	 *
	 * @param title The String to be used as this Book's title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Retrieve the title of this Book.
	 *
	 * @return this Book's ISBN.
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * Set this Book's ISBN.
	 *
	 * @param isbn The String to be used as this Book's ISBN
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	/**
	 * Add Author(s) to this Book's list of Authors.
	 *
	 * @param authors The Author or Authors to be added
	 */
	public void addAuthors(Author... authors) {
		for (Author author : authors)
			this.authors.addLast(author);
		updateAuthorsAsString();
	}

	/**
	 * Retrieve the Author(s) of this Book.
	 *
	 * @return the LinkedList of Authors for this Book.
	 */
	public LinkedList<Author> getAuthors() {
		return authors;
	}

	/**
	 * Retrieve the main Author of this Book.
	 *
	 * @return the first (lead) Author of this Book.
	 */
	public Author getAuthor() {
		return authors.peekFirst();
	}

	/**
	 * Retrieve the Author(s) of this Book in the form of a String.
	 *
	 * @return this Book's list of Authors in the form of a String.
	 */
	public String getAuthorsAsString() {
		StringBuilder sb = new StringBuilder();
		for (Author author : authors)
			sb.append(author).append('\n');

		return sb.toString();
	}

	/**
	 * Set this Book's Author or Authors using variable-length arguments.
	 *
	 * @param authors The Author or Authors to be assigned to this Book
	 */
	public void setAuthors(Author... authors) {
		LinkedList<Author> newAuthors = new LinkedList<>();
		for (Author author : authors)
			newAuthors.addLast(author);
		this.authors = newAuthors;
		updateAuthorsAsString();
	}

	/**
	 * Set this Book's Author or Authors using a LinkedList.
	 *
	 * @param authors The Author or Authors to be assigned to this Book
	 */
	public void setAuthors(LinkedList<Author> authors) {
		this.authors = authors;
	}

	/**
	 * Get the number of available copies of this Book remaining in the library.
	 *
	 * @return the number of available copies of this Book remaining in the library.
	 */
	public int getAvailableInventory() {
		return availableInventory;
	}

	/**
	 * Set the number of available copies of this Book.
	 *
	 * @param availableInventory The number of copies available of this Book
	 */
	public void setAvailableInventory(int availableInventory) {
		this.availableInventory = availableInventory;
	}

	/**
	 * Get the total number of copies of this Book remaining in the library.
	 *
	 * @return the total number of copies of this Book remaining in the library.
	 */
	public int getTotalInventory() {
		return totalInventory;
	}

	/**
	 * Get the time at which this Book was borrowed.
	 *
	 * @return the time at which this Book was borrowed.
	 */
	public LocalDateTime getTimeBorrowedAt() {
		return timeBorrowedAt;
	}

	/**
	 * Set the time at which this Book was borrowed.
	 *
	 * @param timeBorrowedAt The time at which to set when this Book was borrowed
	 */
	public void setTimeBorrowedAt(LocalDateTime timeBorrowedAt) {
		this.timeBorrowedAt = timeBorrowedAt;
	}

	/**
	 * Get the time this Book is due to be returned.
	 *
	 * @return the time this Book is due to be returned.
	 */
	public LocalDateTime getTimeDue() {
		return timeDue;
	}

	/**
	 * Set the time at which this Book is to be returned.
	 *
	 * @param timeDue The time at which this Book is to be returned
	 */
	public void setTimeDue(LocalDateTime timeDue) {
		this.timeDue = timeDue;
	}

	/**
	 * Get the time this Book was returned.
	 *
	 * @return the time this Book was returned.
	 */
	public LocalDateTime getTimeReturned() {
		return timeReturned;
	}

	/**
	 * Set the time this Book was returned.
	 *
	 * @param timeReturned The time this Book was returned
	 */
	public void setTimeReturned(LocalDateTime timeReturned) {
		this.timeReturned = timeReturned;
	}

	/**
	 * Get the time this Book was returned, in the form of a String.
	 *
	 * @return the time this Book was returned, in the form of a String.
	 */
	public String getTimeReturnedString() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E, MM/dd/yyyy h:mma");

		return dateTimeFormatter.format(getTimeReturned());
	}

	/**
	 * Get the time this Book was borrowed, in the form of a String.
	 *
	 * @return the time this Book was borrowed, in the form of a String.
	 */
	public String getTimeBorrowedAtString() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E, MM/dd/yyyy h:mma");

		return dateTimeFormatter.format(getTimeBorrowedAt());
	}

	/**
	 * Get the time this Book is due to be returned, in the form of a String.
	 *
	 * @return the time this Book is due to be returned, in the form of a String.
	 */
	public String getTimeDueString() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E, MM/dd/yyyy h:mma");

		return dateTimeFormatter.format(getTimeDue());
	}

	/**
	 * Set the total number of copies of this Book that this Library has on file.
	 *
	 * @param totalInventory The total number of copies of this Book that this Library has on file.
	 */
	public void setTotalInventory(int totalInventory) {
		this.totalInventory = totalInventory;
	}

	/**
	 * Check whether the String passed is a valid ISBN, ignoring dashes for ease-of-use :)
	 *
	 * @param isbn The String to be used as this Book's ISBN
	 * @return true if ISBN is valid.
	 */
	public static boolean isbnValid(String isbn) {
		if (isbn.length() != 13)
			return false;
		for (int i = 0; i < isbn.length(); i++)
			if (!Character.isDigit(isbn.charAt(i)))
				return false;

		return true;
	}

	/**
	 * Update the String-form of this Book's list of Authors.
	 */
	private void updateAuthorsAsString() {
		StringBuilder sb = new StringBuilder();
		for (Author author : authors)
			sb.append(author.toString()).append('\n');
		authorsAsString = sb.toString();
	}

	/**
	 * Compare (lexicographically) this Book's ISBN to that of the Book being passed.
	 *
	 * @param book The Book whose ISBN that this Book's will be compared to
	 * @return <b>0</b> if the ISBNs are lexicographically equal, a <b>negative</b> integer if this Book's ISBN is
	 * lexicographically less than that of the Book being passed, and a <b>positive</b> integer if this Book's ISBN is
	 * lexicographically greater than that of the Book being passed.
	 */
	@Override
	public int compareTo(Book book) {
		return this.isbn.compareTo(book.isbn);
	}

	/**
	 * Custom toString() method to display this Book's properties in the form of a String.
	 *
	 * @return a custom String displaying information about this Book.
	 */
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder("{ | ").append(title).append(" | } --> ISBN: ").append(isbn).append("\nby ");
		for (Author author : authors)
			string.append(author).append("; ");
		string.append("\nTotal copies: ").append(totalInventory).append("\nAvailable copies: ").append(availableInventory);

		return string.toString();
	}

	/**
	 * Testing method for the Book class
	 *
	 * @param args Command-line arguments
	 */
	public static void main(String[] args) {
		Book b = new Book("The Adventures of Jort McJorpface", "1", new Author("Billy", "Boot"), new Author("Bort", "Borgensen"),
				new Author("Derpy", "McDerpinsen"));

		System.out.println(b);
	}
}