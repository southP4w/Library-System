module gargiullo_librarysystem
{
	exports controller;
	exports model.users;
	exports application;
	exports model.books;
	
	opens controller to javafx.fxml;

	requires transitive javafx.base;
	requires transitive javafx.controls;
	requires transitive javafx.fxml;
	requires transitive javafx.graphics;
}