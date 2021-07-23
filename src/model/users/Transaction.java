package model.users;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.LinkedList;

import model.books.Book;

public class Transaction implements Serializable
{
	private static final long serialVersionUID = 9L;

	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E, MM/dd/yyyy h:mma");
	private String title, isbn, timeBorrowedString, timeDueString, timeReturnedString, transactionType;

	public Transaction() {
	}

	public Transaction(String title, String isbn, LocalDateTime timeDue) {
		this.title = title;
		this.isbn = isbn;
		timeBorrowedString = dateTimeFormatter.format(LocalDateTime.now());
		timeDueString = dateTimeFormatter.format(timeDue);
		transactionType = "borrowed";
	}

	public Transaction(String title, String isbn, LocalDateTime timeDue, LocalDateTime timeReturned) {
		this.title = title;
		this.isbn = isbn;
		timeDueString = dateTimeFormatter.format(timeDue);
		timeReturnedString = dateTimeFormatter.format(timeReturned);
		transactionType = "returned";
	}

	public String getTitle() { return title; }

	public void setTitle(String title) { this.title = title; }

	public String getIsbn() { return isbn; }

	public void setIsbn(String isbn) { this.isbn = isbn; }

	public String getTimeBorrowed() { return timeBorrowedString; }

	public void setTimeBorrowed(LocalDateTime timeBorrowed) { this.timeBorrowedString = dateTimeFormatter.format(timeBorrowed); }

	public String getTimeDue() { return timeDueString; }

	public void setTimeDue(LocalDateTime timeDue) { this.timeDueString = dateTimeFormatter.format(timeDue); }

	public String getTimeReturned() { return timeReturnedString; }

	public void setTimeReturned(LocalDateTime timeReturned) { this.timeReturnedString = dateTimeFormatter.format(timeReturned); }

	public String getTimeBorrowedString() { return timeBorrowedString; }

	public void setTimeBorrowedString(String timeBorrowedString) { this.timeBorrowedString = timeBorrowedString; }

	public String getTimeDueString() { return timeDueString; }

	public void setTimeDueString(String timeDueString) { this.timeDueString = timeDueString; }

	public String getTimeReturnedString() { return timeReturnedString; }

	public void setTimeReturnedString(String timeReturnedString) { this.timeReturnedString = timeReturnedString; }

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