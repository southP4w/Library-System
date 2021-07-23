package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import application.Main;
import application.Utilities;
import model.users.Admin;
import model.users.ContactInfo;
import model.users.Name;
import model.users.User;
import model.users.UserBag;

public class UserCreationController implements Initializable
{
	@FXML
	private Label labelCurrentUser, labelAlertMessage;
	@FXML
	private TextField textFieldEnterFirstName, textFieldEnterLastName, textFieldEnterStreetAddress, textFieldEnterCity;
	@FXML
	private TextField textFieldEnterUsername, textFieldEnterZipCode, textFieldEnterPhoneNumber;
	@FXML
	private ChoiceBox<String> choiceBoxState, choiceBoxStatus;
	@FXML
	private PasswordField passwordFieldEnterPassword, passwordFieldConfirmPassword;
	@FXML
	private Pane imageViewProfilePic;
	@FXML
	private AnchorPane anchorPaneAdminPanel;
	@FXML
	private CheckBox checkBoxIsAdmin;

	private UserBag userBag;
	private User currentUser;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		userBag = Main.getUserBag();
		currentUser = MainMenuController.getCurrentUser();
		choiceBoxState.setItems(Utilities.getFiftyStates());
		choiceBoxStatus.setAccessibleText("Status");
		choiceBoxStatus.getItems().addAll("Active", "On Hold");
		if (currentUser != null) {
			labelCurrentUser.setText("Logged in as:\n" + currentUser.getUsername());
			labelCurrentUser.setVisible(true);
		} else
			labelCurrentUser.setVisible(false);
		if (currentUser instanceof Admin) {
			labelCurrentUser.setText(labelCurrentUser.getText() + " (admin)");
			anchorPaneAdminPanel.setVisible(true);
		}
	}

	@FXML
	void backButtonClicked(ActionEvent actionEvent) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenuView.fxml"));
		Scene mainScene = new Scene(root);
		Stage mainSceneStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
		if (currentUser != null) {
			labelCurrentUser.setText("Logged in as:\n" + currentUser.getUsername());
			labelCurrentUser.setVisible(true);
		} else
			labelCurrentUser.setVisible(false);
		if (currentUser instanceof Admin)
			checkBoxIsAdmin.setVisible(true);
		mainSceneStage.setTitle("Main Menu");
		mainSceneStage.setScene(mainScene);
		mainSceneStage.show();
	}

	@FXML
	void createButtonClicked(ActionEvent actionEvent) {
		String username = textFieldEnterUsername.getText(), password = passwordFieldEnterPassword.getText(),
				passwordConfirm = passwordFieldConfirmPassword.getText();
		String firstName = textFieldEnterFirstName.getText(), lastName = textFieldEnterLastName.getText();
		String address = textFieldEnterStreetAddress.getText(), state = choiceBoxState.getValue(), city = textFieldEnterCity.getText(),
				zip = textFieldEnterZipCode.getText();
		String phoneNumber = textFieldEnterPhoneNumber.getText();
		labelAlertMessage.setVisible(false);
		labelAlertMessage.setTextFill(Paint.valueOf("RED"));
		if (username.isBlank() || password.isBlank() || passwordConfirm.isBlank() || phoneNumber.isBlank() || firstName.isBlank()
				|| lastName.isBlank() || address.isBlank() || city.isBlank() || zip.isBlank() || state == null)
			displayAlertMessage("All fields are required");
		else if (userBag.containsUsername(username))
			displayAlertMessage("That username already exists. Please try again");
		else if (!User.usernameValid(username))
			displayAlertMessage(
					"Username must start with a letter, be at least 5 characters long, contain only alphanumerics, and have at least one number");
		else if (!User.passwordValid(password) || !User.passwordValid(passwordConfirm))
			displayAlertMessage("Password must start with a letter and be at least 5 charcters long");
		else if (!password.contentEquals(passwordConfirm))
			displayAlertMessage("Passwords do not match!");
		else if (!User.phoneNumberValid(phoneNumber))
			displayAlertMessage("Phone number must be exactly 10 numerical digits long");
		else if (!User.zipCodeValid(zip))
			displayAlertMessage("Zip code must only contain 5 numbers");
		else {
			User newUser;
			Name name = new Name(firstName, lastName);
			ContactInfo contactInfo = new ContactInfo(name, address, city, state, zip, phoneNumber);
			if (checkBoxIsAdmin.isSelected())
				newUser = new Admin(username, password, contactInfo);
			else
				newUser = new User(username, password, contactInfo);
			clearAllFields();
			labelAlertMessage.setTextFill(Paint.valueOf("#49bc34d1"));
			displayAlertMessage("Account created successfully!");
			userBag.insert(newUser);
		}
	}

	@FXML
	void uploadProfilePic(MouseEvent actionEvent) {

	}

	private void displayAlertMessage(String alertMessage) {
		labelAlertMessage.setText(alertMessage);
		labelAlertMessage.setWrapText(true);
		labelAlertMessage.setVisible(true);
	}

	private void clearAllFields() {
		textFieldEnterUsername.clear();
		textFieldEnterPhoneNumber.clear();
		textFieldEnterFirstName.clear();
		textFieldEnterLastName.clear();
		textFieldEnterStreetAddress.clear();
		textFieldEnterCity.clear();
		textFieldEnterZipCode.clear();
		passwordFieldEnterPassword.clear();
		passwordFieldConfirmPassword.clear();
	}
}