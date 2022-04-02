package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import application.Main;
import application.Utilities;
import model.books.BookBag;
import model.users.Admin;
import model.users.User;
import model.users.UserBag;

/**
 * Controller class for MainMenuView.fxml
 */
public class MainMenuController implements Initializable
{
	@FXML
	private AnchorPane anchorPaneMain;
	@FXML
	private Menu menuAdministrator;
	@FXML
	private MenuItem menuItemAdminPanel;
	@FXML
	private TextField textFieldEnterUsername;
	@FXML
	private PasswordField passwordFieldEnterPassword;
	@FXML
	private Button buttonLogout;
	@FXML
	private Label labelAlertMessage, labelCurrentUser;

	private BookBag library;
	private UserBag userBag;
	private static User currentUser;

	/**
	 * Called immediately upon entering the MainMenu page of the application. If the current user is an Admin, enable
	 * the Administrator menu.
	 *
	 * @param url            Filepath or webpage
	 * @param resourceBundle Any bundles for locale-based Objects.
	 */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		library = Main.getLibrary();
		userBag = Main.getUserBag();
		currentUser = getCurrentUser();
		if (currentUser != null) {
			buttonLogout.setVisible(true);
			labelCurrentUser.setText("Logged in as:\n" + currentUser.getUsername());
			labelCurrentUser.setVisible(true);
		}
		if (currentUser instanceof Admin) {
			menuAdministrator.setDisable(false);
			labelCurrentUser.setText(labelCurrentUser.getText() + " (admin)");
		}
	}

	/**
	 * Retrieve the User selected in the list of Users.
	 *
	 * @return the selected User.
	 */
	public static User getCurrentUser() {
		return currentUser;
	}

	/**
	 * Called when the User clicks on the Administrator Panel menu option.
	 *
	 * @param actionEvent Administrator Panel menu option was clicked.
	 * @throws IOException if the specified filepath is invalid.
	 */
	@FXML
	void adminControlPanelClicked(ActionEvent actionEvent) throws IOException {
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/AdminPanelView.fxml")));
		Scene accountCreationScene = new Scene(root);
		Stage accountCreationStage = (Stage) anchorPaneMain.getScene().getWindow();
		accountCreationStage.setTitle("Administrator Control Panel");
		accountCreationStage.setScene(accountCreationScene);
		accountCreationStage.setMaximized(true);
		accountCreationStage.show();
	}

	/**
	 * Called when the 'Create New Account' button is clicked.
	 *
	 * @param actionEvent 'Create New Account' button is clicked.
	 * @throws IOException if the specified filepath invalid.
	 */
	@FXML
	void createNewAccountButtonClicked(ActionEvent actionEvent) throws IOException {
		Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/UserCreationView.fxml")));
		Scene accountCreationScene = new Scene(root);
		Stage accountCreationStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
		accountCreationStage.setTitle("Create New Account");
		accountCreationStage.setScene(accountCreationScene);
		accountCreationStage.show();
	}

	/**
	 * Called when the 'Login' button is clicked. Sets currentUser to the account being logged into.
	 *
	 * @param actionEvent 'Login' button was clicked.
	 * @throws IOException if the specified filepath is invalid.
	 */
	@FXML
	void loginButtonClicked(ActionEvent actionEvent) throws IOException {
		String username = textFieldEnterUsername.getText();
		String password = passwordFieldEnterPassword.getText();
		User user = userBag.find(username);
		labelAlertMessage.setVisible(false);
		labelAlertMessage.setTextFill(Paint.valueOf("RED"));
		if (username.isBlank() || password.isBlank())
			displayAlertMessage("Username and Password required");
		else if (userBag.containsUsername(username))
			if (user.getPassword().equals(password)) {
				labelAlertMessage.setTextFill(Paint.valueOf("#49bc34d1"));
				clearAllTextFields();
				displayAlertMessage("Login successful!");
				currentUser = user;
				labelCurrentUser.setText("Logged in as:\n" + currentUser.getUsername());
				labelCurrentUser.setVisible(true);
				if (currentUser instanceof Admin) {
					menuAdministrator.setDisable(false);
					labelCurrentUser.setText(labelCurrentUser.getText() + " (admin)");
				} else {
					menuAdministrator.setDisable(true);
					Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/UserBookSearchView.fxml")));
					Scene bookSearchScene = new Scene(root);
					Stage bookSearchStage = (Stage) anchorPaneMain.getScene().getWindow();
					bookSearchStage.setTitle("Search Books");
					bookSearchStage.setScene(bookSearchScene);
					bookSearchStage.setMaximized(true);
					bookSearchStage.show();
				}
				buttonLogout.setVisible(true);
			} else {
				passwordFieldEnterPassword.clear();
				displayAlertMessage("Invalid username and/or password");
			}
		else
			displayAlertMessage("That account doesn't exist");
	}

	/**
	 * Called when the 'Logout' button is clicked. Logs the current User out.
	 *
	 * @param actionEvent 'Logout' button was clicked.
	 */
	@FXML
	void logoutButtonClicked(ActionEvent actionEvent) {
		buttonLogout.setVisible(false);
		menuAdministrator.setDisable(true);
		currentUser = null;
		labelCurrentUser.setVisible(false);
		labelAlertMessage.setTextFill(Paint.valueOf("BLUE"));
		clearAllTextFields();
		displayAlertMessage("You have successfully signed out");
		labelCurrentUser.setText("Logged in as: ");
	}

	/**
	 * Called when the 'Save' menu option is clicked.
	 *
	 * @param actionEvent 'Save' menu option was clicked.
	 */
	@FXML
	void saveMenuItemClicked(ActionEvent actionEvent) {
		try {
			Utilities.save(userBag, "Persistent/userbag");
			Utilities.save(library, "Persistent/bookbag");
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Called when the 'Quit' menu option is clicked. Quits the application.
	 */
	@FXML
	void quitMenuItemClicked(ActionEvent actionEvent) {
		Platform.exit();
		System.exit(0);
	}

	/**
	 * Method for displaying Label text.
	 *
	 * @param alertMessage The message to be displayed
	 */
	private void displayAlertMessage(String alertMessage) {
		labelAlertMessage.setText(alertMessage);
		labelAlertMessage.setWrapText(true);
		labelAlertMessage.setVisible(true);
	}

	/**
	 * Method to clear all text fields in the scene.
	 */
	private void clearAllTextFields() {
		textFieldEnterUsername.clear();
		passwordFieldEnterPassword.clear();
	}
}