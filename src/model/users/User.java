package model.users;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.TreeMap;

import model.books.Book;

/**
 * The User class. Provides properties and methods for creating a Book object.
 */
public class User implements Serializable
{
	private static final long serialVersionUID = 1L;

	private TreeMap<String, Book> userBookBag;
	private LinkedList<Transaction> userTransactions;

	private final String name;
	private String username;
	private String password;
	private String activeStatusString;
	private String administratorStatusString;
	private final ContactInfo contactInfo;
	private boolean activeStatus, administratorStatus;

	/**
	 * 3-parameter constructor for creating a User object
	 *
	 * @param username    The username to be set to this User
	 * @param password    The password to be set to this User's username
	 * @param contactInfo This User's contact information
	 */
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

	/**
	 * Checks to see if the username is valid
	 *
	 * @param username The username being checked
	 * @return true if valid, false otherwise.
	 */
	public static boolean usernameValid(String username) {
		if (!Character.isAlphabetic(username.charAt(0)))
			return false;
		int letterCount = 1;
		if (username.length() < 5)
			return false;
		int i, numberCount = 0;
		for (i = 1; i < username.length(); i++)
			if (Character.isAlphabetic(username.charAt(i)))
				letterCount++;
			else if (Character.isDigit(username.charAt(i)))
				numberCount++;
			else
				return false;
		return letterCount >= 1 && numberCount >= 1;
	}

	/**
	 * Checks to see if the password is valid
	 *
	 * @param password The password being checked
	 * @return true if valid, false otherwise.
	 */
	public static boolean passwordValid(String password) {
		if (!Character.isAlphabetic(password.charAt(0)))
			return false;
		return password.length() >= 5;
	}

	/**
	 * Checks to see if the phone number is valid
	 *
	 * @param phoneNumber The phone number being checked
	 * @return true if valid, false otherwise.
	 */
	public static boolean phoneNumberValid(String phoneNumber) {
		if (phoneNumber.length() != 10)
			return false;
		for (int i = 0; i < phoneNumber.length(); i++)
			if (!Character.isDigit(phoneNumber.charAt(i)))
				return false;

		return true;
	}

	/**
	 * Checks to see if the zip code is valid
	 *
	 * @param zipCode The zip code being checked
	 * @return true if valid, false otherwise
	 */
	public static boolean zipCodeValid(String zipCode) {
		if (zipCode.length() != 5)
			return false;
		for (int i = 0; i < zipCode.length(); i++)
			if (!Character.isDigit(zipCode.charAt(i)))
				return false;

		return true;
	}

	/**
	 * Check out a book
	 *
	 * @param book The Book being checked out
	 */
	public void checkOutBook(Book book) {
		userTransactions.addFirst(new Transaction(book.getTitle(), book.getIsbn(), LocalDateTime.now().plusSeconds(30)));
		book.setTimeBorrowedAt(LocalDateTime.now());
		book.setTimeDue(LocalDateTime.now().plusMinutes(5));
		userBookBag.put(book.getIsbn(), book);
	}

	/**
	 * Get this User's collection of Books (BookBag)
	 *
	 * @return userBookBag
	 */
	public TreeMap<String, Book> getUserBookBag() {
		return userBookBag;
	}

	/**
	 * Set this User's BookBag to the one being passed.
	 *
	 * @param userBookBag The BookBag to be set as this User's book collection
	 */
	public void setUserBookBag(TreeMap<String, Book> userBookBag) {
		this.userBookBag = userBookBag;
	}

	/**
	 * Get a list of this User's transactions.
	 *
	 * @return a list of this User's transactions.
	 */
	public LinkedList<Transaction> getUserTransactions() {
		return userTransactions;
	}

	/**
	 * Set this User's list of Transactions.
	 *
	 * @param userTransactions The list to be set as this User's Transactions
	 */
	public void setUserTransactions(LinkedList<Transaction> userTransactions) {
		this.userTransactions = userTransactions;
	}

	/**
	 * Return a Book to the library.
	 *
	 * @param book The Book to be returned
	 * @return the removed book.
	 */
	public Book returnBook(Book book) {
		userTransactions.addFirst(new Transaction(book.getTitle(), book.getIsbn(), book.getTimeDue(), LocalDateTime.now()));
		Book bookRemoved = userBookBag.remove(book.getIsbn());
//		if (this.hasOverDueBooks())
//			setActiveStatus(false);
//		else
//			setActiveStatus(true);
		/*
		The above commented, simplified:
		 */
		setActiveStatus(!this.hasOverDueBooks());

		return userBookBag.remove(book.getIsbn());
	}

	/**
	 * Check to see if this User has overdue Books.
	 *
	 * @return true if this User has any overdue Books, false otherwise.
	 */
	public boolean hasOverDueBooks() {
		for (Book b : getBookSet())
			if (b.isOverDue())
				return true;

		return false;
	}

	/**
	 * Get the whole collection of this User's Books
	 *
	 * @return the LinkedList of this User's Books.
	 */
	public LinkedList<Book> getBookSet() {
		LinkedList<Book> books = new LinkedList<>();
		for (Entry<String, Book> b : userBookBag.entrySet())
			books.add(b.getValue());

		return books;
	}

	/**
	 * Check to see if this User is active.
	 *
	 * @return true if active, false otherwise.
	 */
	public boolean isActive() {
		return activeStatus;
	}

	/**
	 * Check to see if this User is active.
	 *
	 * @return true if active, false otherwise.
	 */
	public boolean getActiveStatus() {
		return activeStatus;
	}

	/**
	 * Check to see if this User is an Admin.
	 *
	 * @return true if Admin, false otherwise.
	 */
	public boolean getAdministratorStatus() {
		return administratorStatus;
	}

	/**
	 * Check to see if this User is an Admin.
	 *
	 * @return true if Admin, false otherwise.
	 */
	public boolean isAdministratorStatus() {
		return administratorStatus;
	}

	/**
	 * Toggle the administrative status of this User.
	 *
	 * @param administratorStatus True sets this User to an Admin, false sets to regular User.
	 */
	public void setAdministratorStatus(boolean administratorStatus) {
		this.administratorStatus = administratorStatus;
	}

	/**
	 * Set the String displaying the active status of this user.
	 *
	 * @param activeStatusString The String to be used to display this User's active status.
	 */
	public void setActiveStatusString(String activeStatusString) {
		this.activeStatusString = activeStatusString;
	}

	/**
	 * Set the String displaying the administrative status of this User.
	 *
	 * @param administratorStatusString The String to be used to display this User's active status.
	 */
	public void setAdministratorStatusString(String administratorStatusString) {
		this.administratorStatusString = administratorStatusString;
	}

	/**
	 * Toggle the active status of this User.
	 *
	 * @param activeStatus True sets this User to active, false sets it to inactive.
	 */
	public void setActiveStatus(boolean activeStatus) {
		this.activeStatus = activeStatus;
		if (activeStatus)    // true
			activeStatusString = "Active";
		else
			activeStatusString = "Suspended";
	}

	/**
	 * Check to see whether this User is an Admin.
	 *
	 * @return true if this User is an Admin, false otherwise.
	 */
	public boolean isAdministrator() {
		return this instanceof Admin;
	}

	/**
	 * Get the active status of this User in the form of a String.
	 *
	 * @return the active status of this User, in String form.
	 */
	public String getActiveStatusString() {
		return activeStatusString;
	}

	/**
	 * Get the administrative status of this User, in the form of a String.
	 *
	 * @return the administrator status of this User in the form of a String.
	 */
	public String getAdministratorStatusString() {
		if (this instanceof Admin)
			return administratorStatusString = "Admin";
		return administratorStatusString = "User";
	}

	/**
	 * Get the name (String) of this User.
	 *
	 * @return this User's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the first name of this User.
	 *
	 * @return this User's first name.
	 */
	public String getFirstName() {
		return contactInfo.getName().getFirstName();
	}

	/**
	 * Set this User's first name to the String passed.
	 *
	 * @param firstName The String to be set as this User's first name
	 */
	public void setFirstName(String firstName) {
		contactInfo.getName().setFirstName(firstName);
	}

	/**
	 * Get the last name of this User.
	 *
	 * @return this User's last name.
	 */
	public String getLastName() {
		return contactInfo.getName().getLastName();
	}

	/**
	 * Set this User's last name to the String passed.
	 *
	 * @param lastName The String to be set as this User's last name
	 */
	public void setLastName(String lastName) {
		contactInfo.getName().setLastName(lastName);
	}

	/**
	 * Get the username of this User.
	 *
	 * @return this User's username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set this User's username to the String passed.
	 *
	 * @param userName The String to be set as this User's username
	 */
	public void setUserName(String userName) {
		this.username = userName;
	}

	/**
	 * Get the password of this User.
	 *
	 * @return this User's password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set this User's password to the String passed.
	 *
	 * @param password The String to be set as this User's password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Get this User's phone number.
	 *
	 * @return this User's phone number.
	 */
	public String getPhoneNumber() {
		return contactInfo.getPhoneNumber();
	}

	/**
	 * Set this User's phone number to the String passed.
	 *
	 * @param phoneNumber The String to be set as this User's phone number
	 */
	public void setPhoneNumber(String phoneNumber) {
		contactInfo.setPhoneNumber(phoneNumber);
	}

	/**
	 * Get this User's contact information.
	 *
	 * @return this User's ContactInfo object.
	 */
	public ContactInfo getContactInfo() {
		return contactInfo;
	}

	/**
	 * Get this User's Transaction history in the form of a String.
	 *
	 * @return this User's Transaction history in the form of a String.
	 */
	public String getUserHistoryString() {
		StringBuilder string = new StringBuilder("\nUser Borrowing History:");
		for (Transaction transaction : userTransactions)
			string.append(transaction).append('\n');

		return string.toString();
	}

	/**
	 * Custom toString() method to display this User's properties in the form of a String.
	 *
	 * @return a custom String displaying information about this User.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("User:\n");
		sb.append(username).append(", password: ").append(password);
		sb.append('\n').append(getUserHistoryString());
		sb.append('\n').append(contactInfo);

		return sb.toString();
	}

	/**
	 * Test method for the User class
	 *
	 * @param args Command-line arguments
	 */
	public static void main(String[] args) {
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