package model.users;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.TreeMap;

import model.books.Book;

public class User implements Serializable
{
	private static final long serialVersionUID = 1L;

	private TreeMap<String, Book> userBookBag;
	private LinkedList<Transaction> userTransactions;

	private String name, username, password, activeStatusString, administratorStatusString;
	private ContactInfo contactInfo;
	private boolean activeStatus, administratorStatus;

	public User(String username, String password, ContactInfo contactInfo) {
		userBookBag = new TreeMap<>();
		this.username = username;
		this.password = password;
		this.contactInfo = contactInfo;
		name = contactInfo.getName().toString();
		userTransactions = new LinkedList<>();
		setAdministratorStatus(false);
		setActiveStatus(true);
		activeStatusString = "Active";
		administratorStatusString = "User";
	}

	public static boolean usernameValid(String username) {
		if (!Character.isAlphabetic(username.charAt(0)))
			return false;
		int letterCount = 1;
		if (username.length() < 5)
			return false;
		int i, numberCount = 0;
		for (i = 1 ; i < username.length() ; i++)
			if (Character.isAlphabetic(username.charAt(i)))
				letterCount++;
			else if (Character.isDigit(username.charAt(i)))
				numberCount++;
			else
				return false;
		if (letterCount < 1 || numberCount < 1)
			return false;

		return true;
	}

	public static boolean passwordValid(String password) {
		if (!Character.isAlphabetic(password.charAt(0)))
			return false;
		if (password.length() < 5)
			return false;

		return true;
	}

	public static boolean phoneNumberValid(String phoneNumber) {
		if (phoneNumber.length() != 10)
			return false;
		for (int i = 0 ; i < phoneNumber.length() ; i++)
			if (!Character.isDigit(phoneNumber.charAt(i)))
				return false;

		return true;
	}

	public static boolean zipCodeValid(String zipCode) {
		if (zipCode.length() != 5)
			return false;
		for (int i = 0 ; i < zipCode.length() ; i++)
			if (!Character.isDigit(zipCode.charAt(i)))
				return false;

		return true;
	}

	public void checkOutBook(Book book) {
		userTransactions.addFirst(new Transaction(book.getTitle(), book.getIsbn(), LocalDateTime.now().plusSeconds(30)));
		book.setTimeBorrowedAt(LocalDateTime.now());
		book.setTimeDue(LocalDateTime.now().plusMinutes(5));
		userBookBag.put(book.getIsbn(), book);
	}

	public TreeMap<String, Book> getUserBookBag() { return userBookBag; }

	public void setUserBookBag(TreeMap<String, Book> userBookBag) { this.userBookBag = userBookBag; }

	public LinkedList<Transaction> getUserTransactions() { return userTransactions; }

	public void setUserTransactions(LinkedList<Transaction> userTransactions) { this.userTransactions = userTransactions; }

	public Book returnBook(Book book) {
		userTransactions.addFirst(new Transaction(book.getTitle(), book.getIsbn(), book.getTimeDue(), LocalDateTime.now()));
		Book bookRemoved = userBookBag.remove(book.getIsbn());
		if (this.hasOverDueBooks())
			setActiveStatus(false);
		else
			setActiveStatus(true);

		return userBookBag.remove(book.getIsbn());
	}

	public boolean hasOverDueBooks() {
		for (Book b : getBookSet())
			if (b.isOverDue())
				return true;

		return false;
	}

	public LinkedList<Book> getBookSet() {
		LinkedList<Book> books = new LinkedList<>();
		for (Entry<String, Book> b : userBookBag.entrySet())
			books.add(b.getValue());

		return books;
	}

	public boolean isActive() { return activeStatus; }

	public boolean getActiveStatus() { return activeStatus; }

	public boolean getAdministratorStatus() { return administratorStatus; }

	public boolean isAdministratorStatus() { return administratorStatus; }

	public void setAdministratorStatus(boolean administratorStatus) { this.administratorStatus = administratorStatus; }

	public void setActiveStatusString(String activeStatusString) { this.activeStatusString = activeStatusString; }

	public void setAdministratorStatusString(String administratorStatusString) { this.administratorStatusString = administratorStatusString; }

	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
		if (activeStatus == true)
			activeStatusString = "Active";
		else
			activeStatusString = "Suspended";
	}

	public boolean isAdministrator() { return this instanceof Admin; }

	public String getActiveStatusString() { return activeStatusString; }

	public String getAdministratorStatusString() {
		if (this instanceof Admin)
			return administratorStatusString = "Admin";
		return administratorStatusString = "User";
	}

	public String getName() { return name; }

	public String getFirstName() { return contactInfo.getName().getFirstName(); }

	public void setFirstName(String firstName) {
		contactInfo.getName().setFirstName(firstName);
	}

	public String getLastName() { return contactInfo.getName().getLastName(); }

	public void setLastName(String lastName) {
		contactInfo.getName().setLastName(lastName);
	}

	public String getUsername() { return username; }

	public void setUserName(String userName) { this.username = userName; }

	public String getPassword() { return password; }

	public void setPassword(String password) { this.password = password; }

	public String getPhoneNumber() { return contactInfo.getPhoneNumber(); }

	public void setPhoneNumber(String phoneNumber) {
		contactInfo.setPhoneNumber(phoneNumber);
	}

	public ContactInfo getContactInfo() { return contactInfo; }

	public String getUserHistoryString() {
		StringBuilder string = new StringBuilder("\nUser Borrowing History:");
		for (Transaction transaction : userTransactions)
			string.append(transaction).append('\n');

		return string.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("User:\n");
		sb.append(username).append(", password: ").append(password);
		sb.append('\n').append(getUserHistoryString());
		sb.append('\n').append(contactInfo);

		return sb.toString();
	}

	public static void main(String[] args) throws InterruptedException {
		Name name = new Name("Daniel", "Derp");
		ContactInfo dansInfo = new ContactInfo(name, "666 anywhere", "south hell", "NY", "66666", "1234567890");
		User dan = new User("Daniel1", "AdMiN", dansInfo);
		Book testBook = new Book("test", "5");
		dan.checkOutBook(testBook);

		System.out.println(dan);

		System.out.println("\n\n\n\n");

		dan.returnBook(testBook);

		System.out.println(dan);
	}
}