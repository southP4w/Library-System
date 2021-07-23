package model.books;

import model.users.Name;

public class Author extends Name
{
	private static final long serialVersionUID = 5L;

	public Author() {
		super();
	}

	public Author(String firstName, String lastName) {
		super(firstName, lastName);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(lastName.toUpperCase());
		sb.append(", ").append(firstName);

		return sb.toString();
	}

	public static void main(String[] args) {
		Name a = new Name("Mike", "Hunt");

		System.out.println(a);
	}
}