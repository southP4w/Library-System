package application;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Random;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.books.Author;
import model.books.Book;
import model.books.BookBag;
import model.users.UserBag;
import model.users.Admin;
import model.users.ContactInfo;
import model.users.Name;
import model.users.User;

public class Main extends Application
{
	private static UserBag userBag;
	private static BookBag library;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void init() {
		try {
			userBag = (UserBag) Utilities.load("Persistent/userbag");
			library = (BookBag) Utilities.load("Persistent/library");
			if (!userBag.containsUsername("Jane"))
				userBag.insert(new Admin("Jane", "Doe", new ContactInfo(new Name("Jane", "Doe"), "N/A", "N/A", "N/A", "N/A", "N/A")));
			if (!userBag.containsUsername("John"))
				userBag.insert(new User("John", "Doe", new ContactInfo(new Name("John", "Doe"), "N/A", "N/A", "N/A", "N/A", "N/A")));
			for (String username : userBag.getAllAccounts()) {
				User user = userBag.find(username);
				for (Book book : user.getBookSet())
					if (book.getTimeDue().isBefore(LocalDateTime.now()))
						user.setActiveStatus(false);
			}
		} catch (Exception e) {
			userBag = new UserBag();
			userBag.insert(new Admin("Jane", "Doe", new ContactInfo(new Name("Jane", "Doe"), "N/A", "N/A", "N/A", "N/A", "N/A")));
			userBag.insert(new User("John", "Doe", new ContactInfo(new Name("John", "Doe"), "N/A", "N/A", "N/A", "N/A", "N/A")));
			library = new BookBag();
			try {
				fillBookBag();
				fillUserBag();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenuView.fxml"));
		Scene mainScene = new Scene(root);
		primaryStage.setTitle("Main Menu");
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

	@Override
	public void stop() {
		try {
			Utilities.save(userBag, "Persistent/userbag");
			Utilities.save(library, "Persistent/library");
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static UserBag getUserBag() { return userBag; }

	public static BookBag getLibrary() { return library; }

	private void fillBookBag() throws FileNotFoundException {
		String[] titles = Utilities.emitTitles("Input Files/textbook_titles.txt"), isbns = Utilities.emitISBNs("Input Files/textbook_isbns.txt");
		Name[] names = Utilities.emitNames("Input Files/First_Names.txt", "Input Files/Last_Names.txt");
		LinkedList<Book> books = new LinkedList<>();
		for (int i = 0 ; i < titles.length ; i++)
			books.add(new Book(titles[i], isbns[i]));
		Random randomIndex = new Random(), numberOfAuthors = new Random();
		for (Book book : books) {
			for (int i = 0 ; (numberOfAuthors.nextInt(4) + 1) > i++ ;)
				book.addAuthors((new Author(names[randomIndex.nextInt(2_000)].getFirstName(), names[randomIndex.nextInt(2_000)].getLastName())));
			library.insert(book);
		}
	}

	private void fillUserBag() throws FileNotFoundException {
		Name[] names = Utilities.emitNames("Input Files/First_Names.txt", "Input Files/Last_Names.txt");
		Random randomIndex = new Random();
		for (int i = 0 ; i < 100 ; i++)
			userBag.insert(new User("user" + i, "default", new ContactInfo(names[randomIndex.nextInt(100)])));
	}
}