package model.books;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

public class BookBag implements Serializable
{
	private static final long serialVersionUID = 1L;
	private TreeSet<Book> treeSet;

	public BookBag() {
		treeSet = new TreeSet<>();
	}

	public TreeSet<Book> getTreeSet() { return treeSet; }

	public void insert(Book book) {
		treeSet.add(book);
	}

	public Book findByTitle(String title) {
		Iterator<Book> iterator = treeSet.iterator();
		Book currentBook;
		while (iterator.hasNext()) {
			currentBook = iterator.next();
			if (currentBook.getTitle().contentEquals(title)) {
				Book book = currentBook;

				return book;
			}
		}

		return null;
	}

	public Book removeByTitle(String title) {
		Iterator<Book> iterator = treeSet.iterator();
		Book currentBook;
		while (iterator.hasNext()) {
			currentBook = iterator.next();
			if (currentBook.getTitle().contentEquals(title)) {
				Book book = currentBook;
				treeSet.remove(book);

				return book;
			}
		}

		return null;
	}

	public Book findByIsbn(String isbn) {
		Iterator<Book> iterator = treeSet.iterator();
		Book currentBook;
		while (iterator.hasNext()) {
			currentBook = iterator.next();
			if (currentBook.getIsbn().contentEquals(isbn)) {
				Book book = currentBook;

				return book;
			}
		}

		return null;
	}

	public Book removeByISBN(String isbn) {
		Iterator<Book> iterator = treeSet.iterator();
		Book currentBook;
		while (iterator.hasNext()) {
			currentBook = iterator.next();
			if (currentBook.getIsbn().contentEquals(isbn)) {
				Book book = currentBook;
				treeSet.remove(book);

				return book;
			}
		}

		return null;
	}

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