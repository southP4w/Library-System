package model.users;

import java.io.Serializable;

public class Name implements Serializable
{
	private static final long serialVersionUID = 4L;
	protected String firstName, lastName;

	public Name() {

	}

	public Name(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getFirstName() { return firstName; }

	public void setFirstName(String firstName) { this.firstName = firstName; }

	public String getLastName() { return lastName; }

	public void setLastName(String lastName) { this.lastName = lastName; }

	@Override
	public String toString() {
		return new StringBuilder(firstName).append(' ').append(lastName).toString();
	}

	public static void main(String[] args) {
		Name myName = new Name("Daniel", "Gargiullo");

		System.out.println(myName);
	}
}