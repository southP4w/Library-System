package controller;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
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

	@FXML
	void buttonBackClicked(ActionEvent actionEvent) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/view/AdminPanelView.fxml"));
		Scene mainScene = new Scene(root);
		Stage mainSceneStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
		labelCurrentUser.setText("Logged in as:\n" + currentUser.getUsername());
		labelCurrentUser.setVisible(true);
		mainSceneStage.setTitle("Administrator Control Panel");
		mainSceneStage.setScene(mainScene);
		mainSceneStage.show();
	}

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