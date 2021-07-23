package model.books;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

import model.users.Transaction;

public class Book implements Comparable<Book>, Serializable
{
	private static final long serialVersionUID = 6L;

	private String title, isbn, authorsAsString;
	private LinkedList<Author> authors;
	private Transaction borrowHistory;
	private LocalDateTime timeBorrowedAt, timeDue, timeReturned;
	private String timeBorrowedAtString, timeDueString, timeReturnedString;
	private int availableInventory, totalInventory;

	public Book(String title, String isbn, Author... authors) {
		this.title = title;
		this.isbn = isbn;
		this.authors = new LinkedList<>();
		for (Author author : authors)
			this.authors.addLast(author);
		updateAuthorsAsString();
		availableInventory = (totalInventory = (int) (Math.random()*(20 - 5) + 1));
	}

	public Book(String title) {
		this.title = title;
		availableInventory = (totalInventory = (int) (Math.random()*(20 - 5) + 1));
	}

	public Book() {
		availableInventory = (totalInventory = (int) (Math.random()*(20 - 5) + 1));
	}

	public void incrementAvailableInventory() {
		availableInventory++;
	}

	public void decrementAvailableInventory() {
		availableInventory--;
	}

	public void incrementTotalInventory() {
		totalInventory++;
	}

	public void decrementTotalInventory() {
		totalInventory--;
	}

	public boolean isOverDue() { return this.timeDue.isBefore(LocalDateTime.now()); }

	public String getTitle() { return title; }

	public void setTitle(String title) { this.title = title; }

	public String getIsbn() { return isbn; }

	public void setIsbn(String isbn) { this.isbn = isbn; }

	public void addAuthors(Author... authors) {
		for (Author author : authors)
			this.authors.addLast(author);
		updateAuthorsAsString();
	}

	public LinkedList<Author> getAuthors() { return authors; }

	public Author getAuthor() { return authors.peekFirst(); }

	public String getAuthorsAsString() {
		StringBuilder sb = new StringBuilder();
		for (Author author : authors)
			sb.append(author).append('\n');

		return sb.toString();
	}

	public void setAuthors(Author... authors) {
		LinkedList<Author> newAuthors = new LinkedList<>();
		for (Author author : authors)
			newAuthors.addLast(author);
		this.authors = newAuthors;
		updateAuthorsAsString();
	}

	public void setAuthors(LinkedList<Author> authors) { this.authors = authors; }

	public int getAvailableInventory() { return availableInventory; }

	public void setAvailableInventory(int availableInventory) { this.availableInventory = availableInventory; }

	public int getTotalInventory() { return totalInventory; }

	public LocalDateTime getTimeBorrowedAt() { return timeBorrowedAt; }

	public void setTimeBorrowedAt(LocalDateTime timeBorrowedAt) { this.timeBorrowedAt = timeBorrowedAt; }

	public LocalDateTime getTimeDue() { return timeDue; }

	public void setTimeDue(LocalDateTime timeDue) { this.timeDue = timeDue; }

	public LocalDateTime getTimeReturned() { return timeReturned; }

	public void setTimeReturned(LocalDateTime timeReturned) { this.timeReturned = timeReturned; }

	public String getTimeReturnedString() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E, MM/dd/yyyy h:mma");
		String string = new String(dateTimeFormatter.format(getTimeReturned()));

		return string;
	}

	public String getTimeBorrowedAtString() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E, MM/dd/yyyy h:mma");
		String string = new String(dateTimeFormatter.format(getTimeBorrowedAt()));

		return string;
	}

	public String getTimeDueString() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E, MM/dd/yyyy h:mma");
		String string = new String(dateTimeFormatter.format(getTimeDue()));

		return string;
	}

	public void setTotalInventory(int totalInventory) { this.totalInventory = totalInventory; }

	public static boolean isbnValid(String isbn) {
		if (isbn.length() != 13)
			return false;
		for (int i = 0 ; i < isbn.length() ; i++)
			if (!Character.isDigit(isbn.charAt(i)))
				return false;

		return true;
	}

	private void updateAuthorsAsString() {
		StringBuilder sb = new StringBuilder();
		for (Author author : authors)
			sb.append(author.toString()).append('\n');
		authorsAsString = sb.toString();
	}

	@Override
	public int compareTo(Book book) {
		return this.isbn.compareTo(book.isbn);
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder("{ | ").append(title).append(" | } --> ISBN: ").append(isbn).append("\nby ");
		for (Author author : authors)
			string.append(author).append("; ");
		string.append("\nTotal copies: ").append(totalInventory).append("\nAvailable copies: ").append(availableInventory);

		return string.toString();
	}

	public static void main(String[] args) {
		Book b = new Book("The Adventures of Jort McJorpface", "1", new Author("Billy", "Boot"), new Author("Bort", "Borgensen"),
				new Author("Derpy", "McDerpinsen"));

		System.out.println(b);
	}
}