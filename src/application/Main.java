package application;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Objects;
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

/**
 * The starting class for the entire project. The project life-cycle is as follows:
 *
 * <br>0.) main(String[]) --> Only if using command line arguments. Can otherwise be removed/commented out.
 * <br>1.) init()
 * <br>2.) start(Stage)
 * <br>3.) stop()
 *
 * @author Daniel Gargiullo
 */
public class Main extends Application
{
	private static UserBag userBag;
	private static BookBag library;

	/*
	 * The starting point of the application. This can essentially be commented out unless using command line args.
	 *
	 * @param args Command-line arguments
	 */
//	public static void main(String[] args) {
//		launch(args);
//	}

	/**
	 * Handles the initialization of the User and Book databases ('Bags').
	 * <p>
	 * Tries to load from 'persistent/'. If the load was successful, checks whether the UserBag contains both a default
	 * Admin (Jane) and a default User (John). If either of those two users do not exist in their respective
	 * collections, this method will create them. Finally, check the status of any overdue books for all Users in
	 * userBag.
	 * </p>
	 * <p>
	 * In the case of an Exception: If the UserBag and BookBag have not yet been populated, create them and add the
	 * default User/Admin accounts (Jane and John) and create a new BookBag for the library.
	 * </p>
	 */
	@Override
	public void init() {
		try {
			userBag = (UserBag) Utilities.load("out/persistent/userbag");
			library = (BookBag) Utilities.load("out/persistent/library");
			if (!userBag.containsUsername("Jane"))
				userBag.insert(new Admin("Jane", "Doe", new ContactInfo(new Name("Jane", "Doe"), "N/A", "N/A", "N/A", "N/A", "N/A")));
			if (!userBag.containsUsername("John"))
				userBag.insert(new User("John", "Doe", new ContactInfo(new Name("John", "Doe"), "N/A", "N/A", "N/A", "N/A", "N/A")));
			/*
				The following loop will only run if 'userBag' has been populated with Users. This loop is necessary
				(for now), as it updates the status of overdue books for all Users at this time.
			 */
			for (String username : userBag.getAllAccounts()) {
				User user = userBag.find(username); // O(1)
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

	/**
	 * Handles the opening of the Login screen.
	 * @param primaryStage The stage for the Main Menu scene.
	 * @throws Exception if the file in the path specified in FXMLLoader.load() is not found.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenuView.fxml")));
		Scene mainScene = new Scene(root);
		primaryStage.setTitle("Main Menu");
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}

	/**
	 * Called when either the 'Quit' menu item is clicked, or when the program is closed out.
	 */
	@Override
	public void stop() {
		try {
			Utilities.save(userBag, "out/persistent/userbag");
			Utilities.save(library, "out/persistent/library");
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Makes the UserBag accessible across all classes.
	 * @return userBag
	 */
	public static UserBag getUserBag() {
		return userBag;
	}

	/**
	 * Makes the BookBag accessible across all classes.
	 * @return bookBag
	 */
	public static BookBag getLibrary() {
		return library;
	}

	/**
	 * Fills the BookBag using the files provided in 'Input Files'.
	 * @throws FileNotFoundException if the file in the path specified in Utilities.emitTitles() is not found.
	 */
	private void fillBookBag() throws FileNotFoundException {
		String[] titles = Utilities.emitTitles("Input Files/textbook_titles.txt"), isbns = Utilities.emitISBNs("Input Files/textbook_isbns.txt");
		Name[] names = Utilities.emitNames("Input Files/First_Names.txt", "Input Files/Last_Names.txt");
		LinkedList<Book> books = new LinkedList<>();
		for (int i = 0; i < titles.length; i++)
			books.add(new Book(titles[i], isbns[i]));
		Random randomIndex = new Random(), numberOfAuthors = new Random();
		for (Book book : books) {
			for (int i = 0; (numberOfAuthors.nextInt(4) + 1) > i++; )
				book.addAuthors((new Author(names[randomIndex.nextInt(2_000)].getFirstName(), names[randomIndex.nextInt(2_000)].getLastName())));
			library.insert(book);
		}
	}

	/**
	 * Fills the UserBag using the files provided in 'Input Files'.
	 * @throws FileNotFoundException if the file in the path specified in Utilities.emitNames() is not found.
	 */
	private void fillUserBag() throws FileNotFoundException {
		Name[] names = Utilities.emitNames("Input Files/First_Names.txt", "Input Files/Last_Names.txt");
		Random randomIndex = new Random();
		for (int i = 0; i < 100; i++)
			userBag.insert(new User("user" + i, "default", new ContactInfo(names[randomIndex.nextInt(100)])));
	}
}