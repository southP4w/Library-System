package model.books;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * A container class that uses a TreeSet to manage a large collection of Book objects
 */
public class BookBag implements Serializable
{
	private static final long serialVersionUID = 1L;

	private final TreeSet<Book> treeSet;

	/**
	 * Default constructor for a BookBag object
	 */
	public BookBag() {
		treeSet = new TreeSet<>();
	}

	/**
	 * Get the whole TreeSet.
	 * @return the TreeSet of Books.
	 */
	public TreeSet<Book> getTreeSet() {
		return treeSet;
	}

	/**
	 * Insert a Book into this BookBag's TreeSet.
	 *
	 * @param book The Book being inserted into this BookBag's TreeSet
	 */
	public void insert(Book book) {
		treeSet.add(book);
	}

	/**
	 * Search for a Book in this BookBag by its title.
	 *
	 * @param title The title being searched for
	 * @return the Book, if found. Otherwise, return null.
	 */
	public Book findByTitle(String title) {
		Iterator<Book> iterator = treeSet.iterator();
		Book currentBook;
		while (iterator.hasNext()) {
			currentBook = iterator.next();
			if (currentBook.getTitle().contentEquals(title))
				return currentBook;
		}

		return null;
	}

	/**
	 * Remove a Book from this BookBag by its title.
	 *
	 * @param title The title being searched for
	 * @return and remove the Book, if found. Otherwise, return null.
	 */
	public Book removeByTitle(String title) {
		Iterator<Book> iterator = treeSet.iterator();
		Book currentBook;
		while (iterator.hasNext()) {
			currentBook = iterator.next();
			if (currentBook.getTitle().contentEquals(title)) {
				treeSet.remove(currentBook);

				return currentBook;
			}
		}

		return null;
	}

	/**
	 * Search for a Book in this BookBag by its ISBN.
	 *
	 * @param isbn The ISBN being searched for
	 * @return the Book, if found. Otherwise, return null.
	 */
	public Book findByIsbn(String isbn) {
		Iterator<Book> iterator = treeSet.iterator();
		Book currentBook;
		while (iterator.hasNext()) {
			currentBook = iterator.next();
			if (currentBook.getIsbn().contentEquals(isbn))
				return currentBook;
		}

		return null;
	}

	/**
	 * Remove a Book from this BookBag by its ISBN.
	 *
	 * @param isbn The ISBN being searched for
	 * @return and remove the Book, if found. Otherwise, return null.
	 */
	public Book removeByISBN(String isbn) {
		Iterator<Book> iterator = treeSet.iterator();
		Book currentBook;
		while (iterator.hasNext()) {
			currentBook = iterator.next();
			if (currentBook.getIsbn().contentEquals(isbn)) {
				treeSet.remove(currentBook);

				return currentBook;
			}
		}

		return null;
	}

	/**
	 * Search, by last name, for any Books created by any Authors with that last name.
	 *
	 * @param authorLastName The last name being searched for
	 * @return a LinkedList of Books of any and all Books created by all Authors with the last name that was searched
	 * for.
	 */
	public LinkedList<Book> findByAuthorLastName(String authorLastName) {
		LinkedList<Book> booksByAuthor = new LinkedList<>();
		Iterator<Book> iterator = treeSet.iterator();
		Book currentBook;
		while (iterator.hasNext()) {
			currentBook = iterator.next();
			for (Author author : currentBook.getAuthors())
				if (author.getLastName().equalsIgnoreCase(authorLastName))
					booksByAuthor.add(currentBook);
		}

		return booksByAuthor;
	}
}