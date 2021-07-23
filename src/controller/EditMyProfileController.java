package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.Utilities;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import model.books.BookBag;
import model.users.Admin;
import model.users.User;
import model.users.UserBag;

public class EditMyProfileController implements Initializable {
    @FXML
    private Label labelCurrentUser, labelAlertMessage;
    @FXML
    private TextField textFieldEnterUsername, textFieldEnterFirstName, textFieldEnterLastName, textFieldEnterStreetAddress, textFieldEnterCity;
    @FXML
    private TextField textFieldEnterZipCode, textFieldEnterPhoneNumber;
    @FXML
    private ChoiceBox<String> choiceBoxState;
    @FXML
    private PasswordField passwordFieldEnterPassword, passwordFieldConfirmPassword;
    @FXML
    private Pane imageViewProfilePic;

    private BookBag library;
    private UserBag userBag;
    private User currentUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        library = Main.getLibrary();
        userBag = Main.getUserBag();
        currentUser = MainMenuController.getCurrentUser();
        choiceBoxState.setItems(Utilities.getFiftyStates());
        textFieldEnterUsername.setText(currentUser.getUsername());
        textFieldEnterFirstName.setText(currentUser.getContactInfo().getName().getFirstName());
        textFieldEnterLastName.setText(currentUser.getContactInfo().getName().getLastName());
        textFieldEnterStreetAddress.setText(currentUser.getContactInfo().getStreetAddress());
        textFieldEnterCity.setText(currentUser.getContactInfo().getCity());
        textFieldEnterZipCode.setText(currentUser.getContactInfo().getZipCode());
        textFieldEnterPhoneNumber.setText(currentUser.getContactInfo().getPhoneNumber());
        choiceBoxState.setValue(currentUser.getContactInfo().getState());
    }

    @FXML
    void backButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent root;
        if (currentUser instanceof Admin)
            root = FXMLLoader.load(getClass().getResource("/view/MainMenuView.fxml"));
        else
            root = FXMLLoader.load(getClass().getResource("/view/UserBookSearchView.fxml"));
        Scene mainScene = new Scene(root);
        Stage mainSceneStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        mainSceneStage.setTitle("Main Menu");
        mainSceneStage.setScene(mainScene);
        mainSceneStage.show();
    }

    @FXML
    void saveButtonClicked(ActionEvent actionEvent) {
        String password = passwordFieldEnterPassword.getText(), passwordConfirm = passwordFieldConfirmPassword.getText();
        String firstName = textFieldEnterFirstName.getText(), lastName = textFieldEnterLastName.getText();
        String address = textFieldEnterStreetAddress.getText(), state = choiceBoxState.getValue(), city = textFieldEnterCity.getText(),
                zip = textFieldEnterZipCode.getText();
        String phoneNumber = textFieldEnterPhoneNumber.getText();
        labelAlertMessage.setVisible(false);
        labelAlertMessage.setTextFill(Paint.valueOf("RED"));
        if (!password.isBlank() && !passwordConfirm.isBlank())
            if (!User.passwordValid(password) || !User.passwordValid(passwordConfirm))
                displayAlertMessage("Password must start with a letter and be at least 5 charcters long");
            else if (!password.contentEquals(passwordConfirm))
                displayAlertMessage("Passwords do not match!");
            else
                currentUser.setPassword(password);
        if (!firstName.isBlank())
            currentUser.setFirstName(firstName);
        if (!lastName.isBlank())
            currentUser.setLastName(lastName);
        if (!address.isBlank())
            currentUser.getContactInfo().setStreetAddress(address);
        if (!city.isBlank())
            currentUser.getContactInfo().setCity(city);
        if (!state.isBlank())
            currentUser.getContactInfo().setState(state);
        if (!zip.isBlank())
            if (!User.zipCodeValid(zip))
                displayAlertMessage("Zip code must only contain 5 numbers");
            else
                currentUser.getContactInfo().setZipCode(zip);
        if (!phoneNumber.isBlank())
            if (!User.phoneNumberValid(phoneNumber))
                displayAlertMessage("Phone number must be exactly 10 numerical digits long");
            else
                currentUser.getContactInfo().setPhoneNumber(phoneNumber);
        clearAllFields();
        labelAlertMessage.setTextFill(Paint.valueOf("#49bc34d1"));
        displayAlertMessage("Account updated successfully!");
    }

    @FXML
    void myBorrowingHistoryButtonClicked(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/UserHistoryView.fxml"));
        Scene userHistoryScene = new Scene(root);
        Stage userHistoryStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        userHistoryStage.setTitle("User History");
        userHistoryStage.setScene(userHistoryScene);
        userHistoryStage.show();
    }

    @FXML
    void uploadProfilePic(MouseEvent mouseEvent) {

    }

    private void displayAlertMessage(String alertMessage) {
        labelAlertMessage.setText(alertMessage);
        labelAlertMessage.setWrapText(true);
        labelAlertMessage.setVisible(true);
    }

    private void clearAllFields() {
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