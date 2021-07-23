package controller;

import java.io.IOException;
import java.net.URL;
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

public class MainMenuController implements Initializable
{ // FXML variables:
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

	public static User getCurrentUser() { return currentUser; }

	@FXML
	void adminControlPanelClicked(ActionEvent actionEvent) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/view/AdminPanelView.fxml"));
		Scene accountCreationScene = new Scene(root);
		Stage accountCreationStage = (Stage) anchorPaneMain.getScene().getWindow();
		accountCreationStage.setTitle("Administrator Control Panel");
		accountCreationStage.setScene(accountCreationScene);
		accountCreationStage.setMaximized(true);
		accountCreationStage.show();
	}

	@FXML
	void createNewAccountButtonClicked(ActionEvent actionEvent) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/view/UserCreationView.fxml"));
		Scene accountCreationScene = new Scene(root);
		Stage accountCreationStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
		accountCreationStage.setTitle("Create New Account");
		accountCreationStage.setScene(accountCreationScene);
		accountCreationStage.show();
	}

	@FXML
	void loginButtonClicked(ActionEvent actionEvent) throws IOException {
		String username = textFieldEnterUsername.getText();
		String password = passwordFieldEnterPassword.getText();
		User user = userBag.find(username);
		labelAlertMessage.setVisible(false);
		labelAlertMessage.setTextFill(Paint.valueOf("RED"));
		if (username.isBlank() || password.isBlank())
			displayAlertMessage("Username and Password required");
		else if (userBag.containsUsername(username)) {
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
					Parent root = FXMLLoader.load(getClass().getResource("/view/UserBookSearchView.fxml"));
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
		} else
			displayAlertMessage("That account doesn't exist");
	}

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

	@FXML
	void saveMenuItemClicked(ActionEvent actionEvent) {
		try {
			Utilities.save(userBag, "Persistent/userbag");
			Utilities.save(library, "Persistent/bookbag");
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	@FXML
	void quitMenuItemClicked(ActionEvent actionEvent) {
		Platform.exit();
		System.exit(0);
	}

	private void displayAlertMessage(String alertMessage) {
		labelAlertMessage.setText(alertMessage);
		labelAlertMessage.setWrapText(true);
		labelAlertMessage.setVisible(true);
	}

	private void clearAllTextFields() {
		textFieldEnterUsername.clear();
		passwordFieldEnterPassword.clear();
	}
}