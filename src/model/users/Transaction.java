package model.users;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.LinkedList;

import model.books.Book;

/**
 * Class pertaining to User borrow and return transactions.
 */
public class Transaction implements Serializable
{
	private static final long serialVersionUID = 9L;

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E, MM/dd/yyyy h:mma");
	private String title, isbn, timeBorrowedString, timeDueString, timeReturnedString, transactionType;

	/**
	 * Default constructor for a Transaction object
	 */
	public Transaction() {
	}

	/**
	 * 3-parameter constructor for a Transaction object
	 *
	 * @param title   String to be used as the (Book) title in this Transaction
	 * @param isbn    String to be used as the Book's ISBN in this transaction
	 * @param timeDue LocalDateTime object to be used for the time the Book is due
	 */
	public Transaction(String title, String isbn, LocalDateTime timeDue) {
		this.title = title;
		this.isbn = isbn;
		timeBorrowedString = dateTimeFormatter.format(LocalDateTime.now());
		timeDueString = dateTimeFormatter.format(timeDue);
		transactionType = "borrowed";
	}

	/**
	 * 4-parameter constructor for a Transaction object
	 *
	 * @param title        String to be used as the (Book) title in this Transaction
	 * @param isbn         String to be used as the Book's ISBN in this transaction
	 * @param timeDue      LocalDateTime object to be used for the time the Book is due
	 * @param timeReturned LocalDateTime object to be used for the time the Book was returned
	 */
	public Transaction(String title, String isbn, LocalDateTime timeDue, LocalDateTime timeReturned) {
		this.title = title;
		this.isbn = isbn;
		timeDueString = dateTimeFormatter.format(timeDue);
		timeReturnedString = dateTimeFormatter.format(timeReturned);
		transactionType = "returned";
	}

	/**
	 * Get the title of the Book in this Transaction.
	 *
	 * @return the title of the Book in this Transaction.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the title of the Book in this Transaction.
	 *
	 * @param title String to be used as the Book in this Transaction's title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get the ISBN of the Book on this Transaction.
	 *
	 * @return the ISBN of the Book on this Transaction.
	 */
	public String getIsbn() {
		return isbn;
	}

	/**
	 * Set the ISBN of the Book on this Transaction.
	 *
	 * @param isbn String to be used as the Book in this Transaction's ISBN
	 */
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	/**
	 * Get the time the Book on this Transaction was borrowed, in the form of a String.
	 *
	 * @return the time the Book on this Transaction was borrowed, in the form of a String.
	 */
	public String getTimeBorrowed() {
		return timeBorrowedString;
	}

	/**
	 * Set the time the Book on this Transaction was borrowed.
	 *
	 * @param timeBorrowed LocalDateTime object to be used for when the Book on this Transaction was borrowed
	 */
	public void setTimeBorrowed(LocalDateTime timeBorrowed) {
		this.timeBorrowedString = dateTimeFormatter.format(timeBorrowed);
	}

	/**
	 * Get the time the Book on this Transaction is due to be returned, in the form of a String.
	 *
	 * @return the time the Book on this Transaction is due to be returned, in the form of a String.
	 */
	public String getTimeDue() {
		return timeDueString;
	}

	/**
	 * Set the time the Book on this Transaction is due to be returned.
	 *
	 * @param timeDue LocalDateTime object to be used for when the Book on this Transaction is due to be returned
	 */
	public void setTimeDue(LocalDateTime timeDue) {
		this.timeDueString = dateTimeFormatter.format(timeDue);
	}

	/**
	 * Get the time the Book on this Transaction was returned, in the form of a String.
	 *
	 * @return the time the Book on this Transaction was returned, in the form of a String.
	 */
	public String getTimeReturned() {
		return timeReturnedString;
	}

	/**
	 * Set the time the Book on this Transaction was returned.
	 *
	 * @param timeReturned LocalDateTime object to be used for when the Book on this Transaction is due to be returned
	 */
	public void setTimeReturned(LocalDateTime timeReturned) {
		this.timeReturnedString = dateTimeFormatter.format(timeReturned);
	}

	/**
	 * Get the time the Book on this Transaction was borrowed, in the form of a String.
	 *
	 * @return the time the Book on this Transaction was borrowed, in the form of a String.
	 */
	public String getTimeBorrowedString() {
		return timeBorrowedString;
	}

	/**
	 * Set the time this Book on this transaction was borrowed, using a String.
	 *
	 * @param timeBorrowedString String to be used as the time the Book on this transaction was borrowed.
	 */
	public void setTimeBorrowedString(String timeBorrowedString) {
		this.timeBorrowedString = timeBorrowedString;
	}

	/**
	 * Get the time the Book on this Transaction is due to be returned, in the form of a String.
	 *
	 * @return the time the Book on this Transaction is due to be returned, in the form of a String.
	 */
	public String getTimeDueString() {
		return timeDueString;
	}

	/**
	 * Set the time the Book on this Transaction is due to be returned, in the form of a String.
	 *
	 * @param timeDueString String to be used as the time the Book on this Transaction is due to be returned
	 */
	public void setTimeDueString(String timeDueString) {
		this.timeDueString = timeDueString;
	}

	/**
	 * Get the time the Book on this Transaction was returned, in the form of a String.
	 *
	 * @return the time the Book on this Transaction was returned, in the form of a String.
	 */
	public String getTimeReturnedString() {
		return timeReturnedString;
	}

	/**
	 * Set the time the Book on this Transaction was returned, using a String.
	 *
	 * @param timeReturnedString String to be used as the time the Book on this Transaction was returned.
	 */
	public void setTimeReturnedString(String timeReturnedString) {
		this.timeReturnedString = timeReturnedString;
	}

	/**
	 * Custom toString() method to display this Transaction's properties in the form of a String.
	 *
	 * @return a custom String displaying information about this Transaction object.
	 */
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder("\n\"");
		string.append(title).append("\", ").append(transactionType).append(" on ");
		if (timeBorrowedString != null)
			string.append(timeBorrowedString);
		else
			string.append(timeReturnedString);
		string.append(" | Due on ").append(timeDueString);

		return string.toString();
	}
}