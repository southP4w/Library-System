package controller;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Objects;
import java.util.ResourceBundle;

import application.Main;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import model.books.Book;
import model.books.BookBag;
import model.users.User;
import model.users.UserBag;

/**
 * Controller class for UserBookSearchView.fxml
 */
public class UserBookSearchController implements Initializable
{
	@FXML
	private TextField textFieldSearchForBook;
	@FXML
	private Label labelCurrentUser, labelTableViewBooks;
	@FXML
	private TableView<Book> tableViewBooks;
	@FXML
	private TableColumn<Book, String> tableColumnTitle, tableColumnAuthors, tableColumnISBNs;
	@FXML
	private TableColumn<Book, Integer> tableColumnAvailableInventory, tableColumnTotalInventory;
	@FXML
	private TableView<Book> tableViewYourBorrowedBooks;
	@FXML
	private TableColumn<Book, String> tableColumnYourBorrowedTitle, tableColumnYourBorrowedOn, tableColumnYourDueOn;

	private BookBag library;
	private UserBag userBag;
	private User currentUser;

	/**
	 * Called immediately upon entering the UserBookSearch page of the application. Retrieves the Books, Users, current
	 * User from the Main class, and other information associated with the User.
	 *
	 * @param url            Filepath or web page
	 * @param resourceBundle Any bundles for locale-specific Object.
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		library = Main.getLibrary();
		userBag = Main.getUserBag();
		currentUser = MainMenuController.getCurrentUser();
		labelCurrentUser.setText("Logged in as:\n" + currentUser.getUsername());
		ObservableList<Book> booksOnUser = FXCollections.observableArrayList(currentUser.getBookSet());
		populateTableViewYourBorrowingHistory(booksOnUser);
	}

	/**
	 * Called when the 'Check Out' button is clicked. Checks out the selected book and adds it to the User's inventory.
	 *
	 * @param actionEvent 'Check Out' button was clicked.
	 */
	@FXML
	void checkOutButtonClicked(ActionEvent actionEvent) {
		Book selectedBook = tableViewBooks.getSelectionModel().getSelectedItem();
		labelTableViewBooks.setTextFill(Paint.valueOf("RED"));
		if (currentUser.isActive())
			if (selectedBook.getAvailableInventory() > 0)
				if (currentUser.getBookSet().contains(selectedBook)) {
					labelTableViewBooks.setTextFill(Paint.valueOf("BLUE"));
					labelTableViewBooks.setText(
							"Due to the overwhelming number of patrons in our library, we limit the number of copies of book per customer to one. However, you may take out as many individual books as you'd like");
				} else {
					currentUser.checkOutBook(selectedBook);
					selectedBook.decrementAvailableInventory();
					labelTableViewBooks.setTextFill(Paint.valueOf("#49bc34d1"));
					labelTableViewBooks.setText("You have taken out a copy of \"" + selectedBook.getTitle() + '"');
				}
			else
				labelTableViewBooks.setText("There are no copies of that book currently available");
		else
			labelTableViewBooks.setText("Your account is inactive. Please return any overdue books");
		labelTableViewBooks.setVisible(true);
		ObservableList<Book> booksOnUser = FXCollections.observableArrayList(currentUser.getBookSet());
		populateTableViewYourBorrowingHistory(booksOnUser);
		tableViewBooks.refresh();
		tableViewYourBorrowedBooks.refresh();
	}

	/**
	 * Called when the 'Edit Your Profile' button is clicked. Allows the User to edit the information in their profile.
	 *
	 * @param actionEvent 'Edit Your Profile' button clicked.
	 * @throws IOException if the specified filepath is invalid.
	 */
	@FXML
	void editYourProfileButtonClicked(ActionEvent actionEvent) throws IOException {
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/EditMyProfileView.fxml")));
		Scene bookCreationScene = new Scene(root);
		Stage bookCreationStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
		labelCurrentUser.setText("Logged in as:\n" + currentUser.getUsername());
		bookCreationStage.setTitle("My Profile");
		bookCreationStage.setScene(bookCreationScene);
		bookCreationStage.show();
	}

	/**
	 * Called when the 'Return' button is clicked. Returns the Book selected in the User's inventory to the Library.
	 *
	 * @param actionEvent 'Return' button was clicked.
	 */
	@FXML
	void returnButtonClicked(ActionEvent actionEvent) {
		Book selectedBook = tableViewYourBorrowedBooks.getSelectionModel().getSelectedItem();
		labelTableViewBooks.setTextFill(Paint.valueOf("RED"));
		if (selectedBook == null)
			labelTableViewBooks.setText("Please select a Book from the list below to return");
		else if (currentUser.getBookSet().contains(selectedBook)) {
			currentUser.returnBook(selectedBook);
			selectedBook.incrementAvailableInventory();
			labelTableViewBooks.setTextFill(Paint.valueOf("#49bc34d1"));
			labelTableViewBooks.setText("You returned " + '"' + selectedBook.getTitle() + '"');
		}
		labelTableViewBooks.setVisible(true);
		ObservableList<Book> booksOnUser = FXCollections.observableArrayList(currentUser.getBookSet());
		populateTableViewYourBorrowingHistory(booksOnUser);
		tableViewYourBorrowedBooks.refresh();
		tableViewBooks.refresh();
	}

	/**
	 * Called when the 'by Author' button (in the 'Search Books' section) is clicked. Allows the User to search for a
	 * book by an Author's last name.
	 *
	 * @param actionEvent 'Search' button clicked.
	 */
	@FXML
	void searchByAuthorButtonClicked(ActionEvent actionEvent) {
		String authorLastName = textFieldSearchForBook.getText();
		ObservableList<Book> authors = FXCollections.observableArrayList(library.findByAuthorLastName(authorLastName));
		tableViewBooks.setItems(authors);
		populateTableViewBookSearchResults(authors);
	}

	/**
	 * Called when the 'by ISBN' button (in the 'Search Books' section) is clicked. Allows the User to search for a
	 * book by its ISBN.
	 *
	 * @param actionEvent 'Search' button clicked.
	 */
	@FXML
	void searchByISBNButtonClicked(ActionEvent actionEvent) {
		String isbn = textFieldSearchForBook.getText();
		ObservableList<Book> book = FXCollections.observableArrayList(library.findByIsbn(isbn));
		tableViewBooks.setItems(book);
		populateTableViewBookSearchResults(book);
	}

	/**
	 * Called when the 'by Title' button (in the 'Search Books' section) is clicked. Allows the User to search for a
	 * book by its title.
	 *
	 * @param actionEvent 'Search' button clicked.
	 */
	@FXML
	void searchByTitleButtonClicked(ActionEvent actionEvent) {
		String title = textFieldSearchForBook.getText();
		LinkedList<Book> list = new LinkedList<>();
		for (Book book : library.getTreeSet())
			if (book.getTitle().contains(title))
				list.addLast(book);
		ObservableList<Book> books = FXCollections.observableArrayList(list);
		populateTableViewBookSearchResults(books);
	}

	/**
	 * Called when the 'Logout' button is clicked. Logs the current User out.
	 *
	 * @param actionEvent 'Logout' button was clicked.
	 */
	@FXML
	void logoutButtonClicked(ActionEvent actionEvent) throws IOException {
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenuView.fxml")));
		Scene mainScene = new Scene(root);
		Stage mainSceneStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
		labelCurrentUser.setText("Logged in as:\n" + currentUser.getUsername());
		labelCurrentUser.setVisible(true);
		mainSceneStage.setTitle("Main Menu");
		mainSceneStage.setScene(mainScene);
		mainSceneStage.show();
	}

	/**
	 * Populates the TableView when searching for a Book.
	 *
	 * @param searchResults The ObservableList of Books.
	 */
	private void populateTableViewBookSearchResults(ObservableList<Book> searchResults) {
		tableColumnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
		tableColumnAuthors.setCellValueFactory(new PropertyValueFactory<>("authorsAsString"));
		tableColumnISBNs.setCellValueFactory(new PropertyValueFactory<>("isbn"));
		tableColumnAvailableInventory.setCellValueFactory(new PropertyValueFactory<>("availableInventory"));
		tableColumnTotalInventory.setCellValueFactory(new PropertyValueFactory<>("totalInventory"));
		tableViewBooks.setItems(searchResults);
	}

	/**
	 * Populates the TableView of borrowing history for the currently signed-in, non-Admin User.
	 *
	 * @param yourBookList The ObservableList of Books.
	 */
	private void populateTableViewYourBorrowingHistory(ObservableList<Book> yourBookList) {
		tableColumnYourBorrowedTitle.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		tableColumnYourBorrowedOn.setCellValueFactory(new PropertyValueFactory<Book, String>("timeBorrowedAtString"));
		tableColumnYourDueOn.setCellValueFactory(new PropertyValueFactory<Book, String>("timeDueString"));
		tableViewYourBorrowedBooks.setItems(yourBookList);
	}
}