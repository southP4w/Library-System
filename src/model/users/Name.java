package model.users;

import java.io.Serializable;

/**
 * The Name class
 */
public class Name implements Serializable
{
	private static final long serialVersionUID = 4L;
	protected String firstName, lastName;

	/**
	 * Default constructor for a Name object
	 */
	public Name() {

	}

	/**
	 * Two-parameter constructor for a Name object.
	 *
	 * @param firstName String to be used as the first name
	 * @param lastName  String to be used as the last name
	 */
	public Name(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	/**
	 * Get the first name.
	 *
	 * @return the first name.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Set the first name.
	 *
	 * @param firstName String to be used as the first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Get the last name.
	 *
	 * @return the last name.
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Set the last name.
	 *
	 * @param lastName String to be used as the last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Custom toString() method to display this Name's properties in the form of a String.
	 *
	 * @return a custom String displaying information about this Name object.
	 */
	@Override
	public String toString() {
		return new StringBuilder(firstName).append(' ').append(lastName).toString();
	}

	/**
	 * Test method for the Name class
	 *
	 * @param args Command-line arguments
	 */
	public static void main(String[] args) {
		Name myName = new Name("South", "Paw");

		System.out.println(myName);
	}
}