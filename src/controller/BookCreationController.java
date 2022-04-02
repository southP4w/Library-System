package controller;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import application.Main;
import application.Utilities;
import model.books.Author;
import model.books.Book;
import model.books.BookBag;
import model.users.User;
import model.users.UserBag;

/**
 * Controller class for BookCreationView.fxml
 */
public class BookCreationController implements Initializable
{
	@FXML
	private TextField textFieldEnterTitle, textFieldEnterISBN, textFieldAuthorFirstName, textFieldAuthorLastName, textFieldEnterCopies;
	@FXML
	private ListView<String> listViewAuthorsFirstNames, listViewAuthorsLastNames;
	@FXML
	private Label labelAlertMessage, labelCurrentUser;

	private BookBag library;
	private UserBag userBag;
	private User currentUser;
	private LinkedList<Author> authors;
	private LinkedList<String> authorsFirstNames, authorsLastNames;

	/**
	 * Called immediately upon entering the BookCreation page of the application. Retrieves the Books, Users, current
	 * User from the Main class, and sets the variables for creating a Book to be added to the database.
	 *
	 * @param url            Filepath or webpage
	 * @param resourceBundle Any bundles for locale-specific Objects.
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		library = Main.getLibrary();
		userBag = Main.getUserBag();
		currentUser = MainMenuController.getCurrentUser();
		labelCurrentUser.setText("Logged in as:\n" + currentUser.getUsername() + " (admin)");
		authors = new LinkedList<>();
		authorsFirstNames = new LinkedList<>();
		authorsLastNames = new LinkedList<>();
	}

	/**
	 * Called when the 'Add Author to Book' button is clicked. Adds the information in the fields to the Book being
	 * created.
	 *
	 * @param actionEvent 'Add Author to Book' button was clicked.
	 */
	@FXML
	void buttonAddAuthorClicked(ActionEvent actionEvent) {
		String authorFirstName = textFieldAuthorFirstName.getText(), authorLastName = textFieldAuthorLastName.getText();
		authors.addLast(new Author(authorFirstName, authorLastName));
		authorsFirstNames.addLast(authorFirstName);
		authorsLastNames.addLast(authorLastName);
		ObservableList<String> firstNames = FXCollections.observableArrayList(authorsFirstNames);
		ObservableList<String> lastNames = FXCollections.observableArrayList(authorsLastNames);
		listViewAuthorsFirstNames.setItems(firstNames);
		listViewAuthorsLastNames.setItems(lastNames);
	}

	/**
	 * Called when the 'Back' button is clicked
	 *
	 * @param actionEvent 'Back' button was clicked.
	 * @throws IOException if the specified filepath isw invalid.
	 */
	@FXML
	void buttonBackClicked(ActionEvent actionEvent) throws IOException {
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AdminPanelView.fxml")));
		Scene mainScene = new Scene(root);
		Stage mainSceneStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
		labelCurrentUser.setText("Logged in as:\n" + currentUser.getUsername());
		labelCurrentUser.setVisible(true);
		mainSceneStage.setTitle("Administrator Control Panel");
		mainSceneStage.setScene(mainScene);
		mainSceneStage.show();
	}

	/**
	 * Called when the 'Done' button is clicked. Adds the new Book to the database, if it meets the requirements.
	 *
	 * @param actionEvent 'Back' button was clicked.
	 */
	@FXML
	void buttonDoneClicked(ActionEvent actionEvent) {
		String title = textFieldEnterTitle.getText(), isbn = textFieldEnterISBN.getText();
		String numberOfCopies = textFieldEnterCopies.getText();
		labelAlertMessage.setTextFill(Paint.valueOf("RED"));
		if (title.isBlank())
			labelAlertMessage.setText("Title required");
		else if (isbn.isBlank())
			labelAlertMessage.setText("ISBN required");
		else if (authors.isEmpty())
			labelAlertMessage.setText("At least one author (first & last name) is required");
		else if (numberOfCopies.isBlank())
			labelAlertMessage.setText("Please provide the number of copies of the book to be inserted");
		else {
			Book newBook;
			if (!Utilities.isInt(numberOfCopies))
				labelAlertMessage.setText("The number of copies must be an integer");
			else if (!Book.isbnValid(isbn))
				labelAlertMessage.setText("ISBN must be 13 numerical digits in length");
			else if (library.findByIsbn(isbn) != null)
				labelAlertMessage.setText("That book already exists in the library");
			else {
				newBook = new Book(title, isbn);
				newBook.setAuthors(authors);
				newBook.setTotalInventory(Integer.parseInt(numberOfCopies));
				newBook.setAvailableInventory(Integer.parseInt(numberOfCopies));
				library.insert(newBook);
				labelAlertMessage.setTextFill(Paint.valueOf("#49bc34d1"));
				labelAlertMessage.setText("Book successfully added to library!");
				authors = new LinkedList<>();
				authorsFirstNames = new LinkedList<>();
				authorsLastNames = new LinkedList<>();
				clearAllFields();
			}
		}
		labelAlertMessage.setVisible(true);
	}

	/**
	 * Method for clearing all fields in this Scene.
	 */
	private void clearAllFields() {
		textFieldAuthorFirstName.clear();
		textFieldAuthorLastName.clear();
		textFieldEnterCopies.clear();
		textFieldEnterISBN.clear();
		textFieldEnterTitle.clear();
		listViewAuthorsFirstNames.setItems(null);
		listViewAuthorsLastNames.setItems(null);
	}
}