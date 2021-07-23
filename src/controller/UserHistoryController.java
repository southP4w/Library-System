package controller;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.books.BookBag;
import model.users.Admin;
import model.users.Transaction;
import model.users.User;
import model.users.UserBag;

public class UserHistoryController implements Initializable
{
	@FXML
	private TableView<Transaction> tableViewUserHistory;
	@FXML
	private TableColumn<Transaction, String> tableColumnUserBorrowedTitle, tableColumnUserBorrowedOn, tableColumnUserDueOn, tableColumnUserReturnedOn;

	private BookBag library;
	private UserBag userBag;
	private User currentUser, selectedUser;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		library = Main.getLibrary();
		userBag = Main.getUserBag();
		currentUser = MainMenuController.getCurrentUser();
		selectedUser = AdminPanelController.getSelectedUSer();
		ObservableList<Transaction> history;
		if (selectedUser != null)
			history = FXCollections.observableArrayList(selectedUser.getUserTransactions());
		else
			history = FXCollections.observableArrayList(currentUser.getUserTransactions());
		populateTableViewUserHistory(history);
	}

	@FXML
	void backButtonClicked(ActionEvent actionEvent) throws IOException {
		Parent root;
		if (currentUser instanceof Admin)
			root = FXMLLoader.load(getClass().getResource("/view/AdminPanelView.fxml"));
		else
			root = FXMLLoader.load(getClass().getResource("/view/UserBookSearchView.fxml"));
		Scene previousScene = new Scene(root);
		Stage previousStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
		previousStage.setTitle("Administrator Control Panel");
		previousStage.setScene(previousScene);
		previousStage.show();
	}

	private void populateTableViewUserHistory(ObservableList<Transaction> transactions) {
		tableColumnUserBorrowedTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
		tableColumnUserBorrowedOn.setCellValueFactory(new PropertyValueFactory<>("timeBorrowedString"));
		tableColumnUserDueOn.setCellValueFactory(new PropertyValueFactory<>("timeDueString"));
		tableColumnUserReturnedOn.setCellValueFactory(new PropertyValueFactory<>("timeReturnedString"));
		tableViewUserHistory.setItems(transactions);
	}
}