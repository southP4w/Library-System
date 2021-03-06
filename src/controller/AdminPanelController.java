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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import application.Main;
import model.books.Book;
import model.books.BookBag;
import model.users.Admin;
import model.users.User;
import model.users.UserBag;

/**
 * Controller class for AdminPanelView.fxml
 */
public class AdminPanelController implements Initializable
{
	@FXML
	private TableView<Book> tableViewBooks;
	@FXML
	private TableView<Book> tableViewYourBorrowedBooks, tableViewUserBorrowedBooks;
	@FXML
	private TableColumn<Book, String> tableColumnTitle, tableColumnAuthors, tableColumnISBNs;
	@FXML
	private TableColumn<Book, Integer> tableColumnAvailableInventory, tableColumnTotalInventory;
	@FXML
	private TableColumn<Book, String> tableColumnYourBorrowedTitle, tableColumnYourBorrowedOn, tableColumnYourDueOn, tableColumnUserBorrowedTitle,
			tableColumnUserBorrowedOn, tableColumnUserDueOn;
	@FXML
	private TableView<User> tableViewUsers;
	@FXML
	private TableColumn<User, String> tableColumnUsername, tableColumnName;
	@FXML
	private TableColumn<User, String> tableColumnRole, tableColumnActiveStatus;
	@FXML
	private TextField textFieldSearchForBook, textFieldSearchByUsername;
	@FXML
	private Label labelTableViewBooks, labelTableViewUsers, labelCurrentUser;

	private static User selectedUser;
	private BookBag library;
	private UserBag userBag;
	private User currentUser;

	/**
	 * Called immediately upon entering the AdminPanel page of the application. Retrieves the Books, Users, current
	 * User from the Main class, and other information associated with the current User.
	 *
	 * @param url            Filepath or web page
	 * @param resourceBundle Any bundles for locale-specific Objects.
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		library = Main.getLibrary();
		userBag = Main.getUserBag();
		currentUser = MainMenuController.getCurrentUser();
		labelCurrentUser.setText("Logged in as:\n" + currentUser.getUsername() + " (admin)");
		ObservableList<Book> booksOnUser = FXCollections.observableArrayList(currentUser.getBookSet());
		populateTableViewYourBorrowingHistory(booksOnUser);
		tableViewUsers.getSelectionModel().selectedItemProperty().addListener((e) -> {
			ObservableList<Book> userBookList = FXCollections.observableArrayList(tableViewUsers.getSelectionModel().getSelectedItem().getBookSet());
			tableViewUserBorrowedBooks.setItems(userBookList);
			populateTableViewUserBorrowingHistory(userBookList);
		});
	}

	/**
	 * Retrieve the User selected in the list of Users.
	 *
	 * @return the selected User.
	 */
	public static User getSelectedUser() {
		return selectedUser;
	}

	/**
	 * Called when the 'Back' button is clicked. Returns to MainMenu.
	 *
	 * @param actionEvent 'Back' button clicked.
	 * @throws IOException if the specified filepath is invalid.
	 */
	@FXML
	void backButtonClicked(ActionEvent actionEvent) throws IOException {
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
	 * Called when the 'Add Book' button is clicked. Opens up the BookCreation scene to allow the administrator to add
	 * a new book to the database.
	 *
	 * @param actionEvent 'Add Book' button clicked.
	 * @throws IOException if the specified filepath is invalid.
	 */
	@FXML
	void addBookButtonClicked(ActionEvent actionEvent) throws IOException {
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/BookCreationView.fxml")));
		Scene bookCreationScene = new Scene(root);
		Stage bookCreationStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
		labelCurrentUser.setText("Logged in as:\n" + currentUser.getUsername() + " (admin)");
		bookCreationStage.setTitle("Add Book to Library");
		bookCreationStage.setScene(bookCreationScene);
		bookCreationStage.show();
	}

	/**
	 * Called when the 'Check Out' button is clicked. Allows the User to check out the selected Book.
	 *
	 * @param actionEvent 'Check Out' button clicked.
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
	 * Called when the 'Delete Book' button is clicked. Allows the Admin to delete the selected Book.
	 *
	 * @param actionEvent 'Delete Book' button clicked.
	 */
	@FXML
	void deleteBookButtonClicked(ActionEvent actionEvent) {
		Book selectedBook = tableViewBooks.getSelectionModel().getSelectedItem();
		labelTableViewBooks.setTextFill(Paint.valueOf("RED"));
		if (selectedBook == null)
			labelTableViewBooks.setText("Please select a book from the list to the left to delete");
		else {
			library.removeByISBN(selectedBook.getIsbn());
			labelTableViewBooks.setTextFill(Paint.valueOf("#49bc34d1"));
			labelTableViewBooks.setText("You deleted " + '"' + selectedBook.getTitle() + '"');
			tableViewBooks.refresh();
		}
		labelTableViewBooks.setVisible(true);
		ObservableList<Book> searchResults = FXCollections.observableArrayList(tableViewBooks.getItems());
		populateTableViewBookSearchResults(searchResults);
	}

	/**
	 * Called when the 'Delete User' button is clicked. Allows the Admin to delete the selected User.
	 *
	 * @param actionEvent 'Delete User' button clicked.
	 */
	@FXML
	void deleteUserButtonClicked(ActionEvent actionEvent) {
		User selectedUser = tableViewUsers.getSelectionModel().getSelectedItem();
		labelTableViewUsers.setTextFill(Paint.valueOf("RED"));
		if (selectedUser == null)
			labelTableViewUsers.setText("Please select a user from the list to the left to delete");
		else {
			userBag.remove(selectedUser.getUsername());
			labelTableViewUsers.setTextFill(Paint.valueOf("#49bc34d1"));
			labelTableViewUsers.setText("You deleted user '" + selectedUser.getUsername() + '\'');
			tableViewUsers.refresh();
		}
		labelTableViewUsers.setVisible(true);
		ObservableList<User> searchResults = FXCollections.observableArrayList(tableViewUsers.getItems());
		populateTableViewUserSearchResults(searchResults);
	}

	/**
	 * Called when the 'Suspend/Unsuspend User' button is clicked. Allows the Admin to suspend or unsuspend the
	 * selected User's account.
	 *
	 * @param actionEvent 'Suspend/Unsuspend User' button clicked.
	 */
	@FXML
	void suspendUnsuspendButtonClicked(ActionEvent actionEvent) {
		User selectedUser = tableViewUsers.getSelectionModel().getSelectedItem();
		labelTableViewUsers.setTextFill(Paint.valueOf("RED"));
		if (selectedUser == null)
			labelTableViewUsers.setText("Please select a user from the list to the left whose account you'd like to suspend/unsuspend");
		else {
			userBag.find(selectedUser.getUsername()).setActiveStatus(!selectedUser.getActiveStatus());
			labelTableViewUsers.setTextFill(Paint.valueOf("BLUE"));
			labelTableViewUsers.setText("Changed user '" + selectedUser.getUsername() + "' active status to " + selectedUser.getActiveStatusString());
		}
		labelTableViewUsers.setVisible(true);
		searchByUsernameButtonClicked(actionEvent);
		tableViewUsers.refresh();
	}

	/**
	 * Called when the 'Change User Role' button is clicked. Allows the Admin to change the role of the selected User
	 * to and from 'User' and 'Admin'.
	 *
	 * @param actionEvent 'Change User Role' button clicked.
	 */
	@FXML
	void changeUserRoleButtonClicked(ActionEvent actionEvent) {
		User selectedUser = tableViewUsers.getSelectionModel().getSelectedItem();
		User newUser;
		labelTableViewUsers.setTextFill(Paint.valueOf("RED"));
		if (selectedUser == null)
			labelTableViewUsers.setText("Please select a user from the list to the left whose role you'd like to change");
		else {
			if (selectedUser instanceof Admin) {
				newUser = new User(selectedUser.getUsername(), selectedUser.getPassword(), selectedUser.getContactInfo());
				newUser.setAdministratorStatus(false);
			} else {
				newUser = new Admin(selectedUser.getUsername(), selectedUser.getPassword(), selectedUser.getContactInfo());
				newUser.setAdministratorStatus(true);
			}
			newUser.setActiveStatus(selectedUser.getActiveStatus());
			newUser.setActiveStatusString(selectedUser.getActiveStatusString());
			newUser.setAdministratorStatusString(selectedUser.getAdministratorStatusString());
			newUser.setUserTransactions(selectedUser.getUserTransactions());
			newUser.setUserBookBag(selectedUser.getUserBookBag());
			userBag.remove(selectedUser.getUsername());
			userBag.insert(newUser);
			labelTableViewUsers.setTextFill(Paint.valueOf("BLUE"));
			labelTableViewUsers.setText("Changed user '" + newUser.getUsername() + "' to " + newUser.getAdministratorStatusString());
		}
		labelTableViewUsers.setVisible(true);
		searchByUsernameButtonClicked(actionEvent);
		tableViewUsers.refresh();
	}

	/**
	 * Called when the 'Return' button is clicked. Allows the User to return the selected Book from their list of
	 * currently checked-out books.
	 *
	 * @param actionEvent 'Return' button clicked.
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
		labelCurrentUser.setText("Logged in as:\n" + currentUser.getUsername() + " (admin)");
		bookCreationStage.setTitle("My Profile");
		bookCreationStage.setScene(bookCreationScene);
		bookCreationStage.show();
	}

	/**
	 * Called when the 'Search' button (in the 'Search and Update Users' section) is clicked. Allows the Admin to search
	 * for a User by username.
	 *
	 * @param actionEvent 'Search' button clicked.
	 */
	@FXML
	void searchByUsernameButtonClicked(ActionEvent actionEvent) {
		String username = textFieldSearchByUsername.getText();
		if (username == null) {
			labelTableViewUsers.setTextFill(Paint.valueOf("RED"));
			labelTableViewUsers.setText("Please enter a username to search for");
			labelTableViewUsers.setVisible(true);
		}
		ObservableList<User> userSearchResults = FXCollections.observableArrayList(userBag.find(username));
		tableViewUsers.setItems(userSearchResults);
		populateTableViewUserSearchResults(userSearchResults);
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
	 * Called when 'View User Borrow History' button is clicked. Allows the Admin to view a User's borrowing history.
	 *
	 * @param actionEvent 'View User Borrow History' button clicked.
	 * @throws IOException if the specified filepath is invalid.
	 */
	@FXML
	void viewUserHistoryButtonClicked(ActionEvent actionEvent) throws IOException {
		selectedUser = tableViewUsers.getSelectionModel().getSelectedItem();
		labelTableViewUsers.setTextFill(Paint.valueOf("RED"));
		if (selectedUser == null)
			labelTableViewUsers.setText("Please select a user from the table to the left");
		else {
			Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/UserHistoryView.fxml")));
			Scene userHistoryScene = new Scene(root);
			Stage userHistoryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
			userHistoryStage.setTitle("User History");
			userHistoryStage.setScene(userHistoryScene);
			userHistoryStage.show();
		}
	}

	/**
	 * Displays the search results when searching for a User by username.
	 *
	 * @param searchResults The ObservableList of Users.
	 */
	private void populateTableViewUserSearchResults(ObservableList<User> searchResults) {
		tableColumnUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnRole.setCellValueFactory(new PropertyValueFactory<>("administratorStatusString"));
		tableColumnActiveStatus.setCellValueFactory(new PropertyValueFactory<>("activeStatusString"));
		tableViewUsers.setItems(searchResults);
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
	 * Populates the TableView when viewing your borrowing history.
	 *
	 * @param yourBookList The ObservableList of Books.
	 */
	private void populateTableViewYourBorrowingHistory(ObservableList<Book> yourBookList) {
		tableColumnYourBorrowedTitle.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		tableColumnYourBorrowedOn.setCellValueFactory(new PropertyValueFactory<Book, String>("timeBorrowedAtString"));
		tableColumnYourDueOn.setCellValueFactory(new PropertyValueFactory<Book, String>("timeDueString"));
		tableViewYourBorrowedBooks.setItems(yourBookList);
	}

	/**
	 * Populates the TableView when viewing a User's borrowing history.
	 *
	 * @param userBookList The ObservableList of Books.
	 */
	private void populateTableViewUserBorrowingHistory(ObservableList<Book> userBookList) {
		tableColumnUserBorrowedTitle.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		tableColumnUserBorrowedOn.setCellValueFactory(new PropertyValueFactory<Book, String>("timeBorrowedAtString"));
		tableColumnUserDueOn.setCellValueFactory(new PropertyValueFactory<Book, String>("timeDueString"));
		tableViewUserBorrowedBooks.setItems(userBookList);
	}
}