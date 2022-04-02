package model.books;

import model.users.Name;

/**
 * The Author class. Extends 'model.users.Name'
 */
public class Author extends Name
{
	private static final long serialVersionUID = 5L;

	/**
	 * 2-param constructor for an Author Object
	 *
	 * @param firstName The Author's first name
	 * @param lastName  The Author's last name
	 */
	public Author(String firstName, String lastName) {
		super(firstName, lastName);
	}

	/**
	 * Default constructor for an Author Object
	 */
	public Author() {
		super();
	}

	/**
	 * ToString method for the Author class
	 *
	 * @return the name of the Author in the form of a String, formatted as Last Name, First Name.
	 */
	@Override
	public String toString() {
		return lastName.toUpperCase() + ", " + firstName;
	}

	/**
	 * Testing method for the Author class
	 *
	 * @param args Command-line arguments
	 */
	public static void main(String[] args) {
		Name a = new Name("Mike", "Jones");

		System.out.println(a);
	}
}