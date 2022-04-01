package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import model.books.Author;
import model.users.Name;

/**
 * <p>A class of static methods for performing various functions involving retrieving/storing information, generating
 * the database, etc.
 * </p>
 * <p>Please note, this class is not optimized and should really be rewritten.</p>
 */
public class Utilities
{
	private static Scanner titleScanner, isbnScanner, firstNameScanner, lastNameScanner, scannerStates;
	private static String[] firstNames, lastNames, titles, isbns;
	private static Name[] names;
	private static Name name;
	private static Author author;
	private static final int NUMBER_OF_BOOKS = 5_000;

	/**
	 * Generates a single name using random values from <b>firstNameFile</b> and from <b>lastNameFile</b>.
	 *
	 * @param firstNameFileName The filepath to the list of first names.
	 * @param lastNameFileName  The filepath to the list of last names.
	 * @return a Name containing a randomly-generated value.
	 */
	public static Name emitName(String firstNameFileName, String lastNameFileName) {
		File firstNameFile = new File(firstNameFileName), lastNameFile = new File(lastNameFileName);
		try {
			firstNameScanner = new Scanner(firstNameFile);
			lastNameScanner = new Scanner(lastNameFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int numberOfFirstNames = 0, numberOfLastNames = 0;
		while (firstNameScanner.hasNextLine()) {
			numberOfFirstNames++;
			firstNameScanner.nextLine();
		}
		firstNames = new String[numberOfFirstNames];
		try {
			firstNameScanner = new Scanner(firstNameFile);
			while (lastNameScanner.hasNextLine()) {
				numberOfLastNames++;
				lastNameScanner.nextLine();
			}
			lastNames = new String[numberOfLastNames];
			lastNameScanner = new Scanner(lastNameFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int i = 0;
		while (firstNameScanner.hasNextLine() && lastNameScanner.hasNextLine()) {
			firstNames[i] = firstNameScanner.nextLine();
			lastNames[i++] = lastNameScanner.nextLine();
		}
		Random random = new Random();
		name = new Name(firstNames[random.nextInt(firstNames.length)], lastNames[random.nextInt(lastNames.length)]);

		return name;
	}

	/**
	 * Generates an array of Names using random values from <b>firstNameFile</b> and from <b>lastNameFile</b>.
	 *
	 * @param firstNameFileName The filepath to the list of last names.
	 * @param lastNameFileName  The filepath to the list of last names.
	 * @return an array of Names containing randomly-generated values.
	 */
	public static Name[] emitNames(String firstNameFileName, String lastNameFileName) {
		File firstNameFile = new File(firstNameFileName), lastNameFile = new File(lastNameFileName);
		try {
			firstNameScanner = new Scanner(firstNameFile);
			lastNameScanner = new Scanner(lastNameFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int numberOfFirstNames = 0, numberOfLastNames = 0;
		while (firstNameScanner.hasNextLine()) {
			numberOfFirstNames++;
			firstNameScanner.nextLine();
		}
		firstNames = new String[numberOfFirstNames];
		try {
			firstNameScanner = new Scanner(firstNameFile);
			while (lastNameScanner.hasNextLine()) {
				numberOfLastNames++;
				lastNameScanner.nextLine();
			}
			lastNames = new String[numberOfLastNames];
			lastNameScanner = new Scanner(lastNameFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int i = 0;
		while (firstNameScanner.hasNextLine() && lastNameScanner.hasNextLine()) {
			firstNames[i] = firstNameScanner.nextLine();
			lastNames[i++] = lastNameScanner.nextLine();
		}
		Random random = new Random();
		names = new Name[numberOfFirstNames];
		for (i = 0; i < names.length; )
			names[i++] = new Name(firstNames[random.nextInt(firstNames.length)], lastNames[random.nextInt(lastNames.length)]);

		return names;
	}

	/**
	 * Generates an array of book titles using <b>titleFile</b>.
	 *
	 * @param titleFileName The filepath to the list of book titles.
	 * @return a String array of book titles.
	 * @throws FileNotFoundException if the specified path to the file containing the list of book titles is not found.
	 */
	public static String[] emitTitles(String titleFileName) throws FileNotFoundException {
		File titleFile = new File(titleFileName);
		titleScanner = new Scanner(titleFile);
		titles = new String[NUMBER_OF_BOOKS];
		int i = 0;
		while (titleScanner.hasNextLine() && i < NUMBER_OF_BOOKS)
			titles[i++] = titleScanner.nextLine(); //i++

		return titles;
	}

	/**
	 * Generates an array of ISBN's using <b>isbnFile</b>.
	 *
	 * @param isbnFileName The filepath to the list of ISBN's.
	 * @return an array of ISBN's.
	 * @throws FileNotFoundException if the specified path to the file containing the list of ISBN's is not found.
	 */
	public static String[] emitISBNs(String isbnFileName) throws FileNotFoundException {
		File isbnFile = new File(isbnFileName);
		isbnScanner = new Scanner(isbnFile);
		isbns = new String[NUMBER_OF_BOOKS];
		int i = 0;
		while (isbnScanner.hasNextLine() && i < NUMBER_OF_BOOKS)
			isbns[i++] = isbnScanner.nextLine();

		return isbns;
	}

	/**
	 * Generates a single name using random values from <b>firstNameFile</b> and from <b>lastNameFile</b>.
	 *
	 * @param firstNameFileName The filepath to the list of first names.
	 * @param lastNameFileName  The filepath to the list of last names.
	 * @return an Author containing a randomly-generated value.
	 */
	public static Author emitAuthor(String firstNameFileName, String lastNameFileName) {
		File firstNameFile = new File(firstNameFileName), lastNameFile = new File(lastNameFileName);
		try {
			firstNameScanner = new Scanner(firstNameFile);
			lastNameScanner = new Scanner(lastNameFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int numberOfFirstNames = 0, numberOfLastNames = 0;
		while (firstNameScanner.hasNextLine()) {
			numberOfFirstNames++;
			firstNameScanner.nextLine();
		}
		firstNames = new String[numberOfFirstNames];
		try {
			firstNameScanner = new Scanner(firstNameFile);
			while (lastNameScanner.hasNextLine()) {
				numberOfLastNames++;
				lastNameScanner.nextLine();
			}
			lastNames = new String[numberOfLastNames];
			lastNameScanner = new Scanner(lastNameFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		int i = 0;
		while (firstNameScanner.hasNextLine() && lastNameScanner.hasNextLine()) {
			firstNames[i] = firstNameScanner.nextLine();
			lastNames[i++] = lastNameScanner.nextLine();
		}
		Random random = new Random();
		author = new Author(firstNames[random.nextInt(firstNames.length)], lastNames[random.nextInt(lastNames.length)]);

		return author;
	}

	/**
	 * @return a randomly-generated price between $10.00 and $190.00.
	 */
	public static double emitPrice() {
		Random rand = new Random();
		int dollars = 10 + rand.nextInt(190);
		double cents = rand.nextDouble();
		return dollars + cents;
	}

	/**
	 * @param string to be determined whether an integer value.
	 * @return true if integer, false otherwise.
	 */
	public static boolean isInt(String string) {
		for (int c = 0; c < string.length(); c++)
			if (!Character.isDigit(string.charAt(c)))
				return false;

		return true;
	}

	/**
	 * Saves serialized data to a specified path.
	 *
	 * @param data     The serialized data to be saved.
	 * @param fileName The specified filepath to which the data will be saved.
	 * @throws Exception if the data is bad or the filepath is invalid.
	 */
	public static void save(Serializable data, String fileName) throws Exception {
		try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
			oos.writeObject(data);
		}
	}

	/**
	 * Loads serialized data from a specified path.
	 *
	 * @param fileName The specified filepath from which the data will be loaded.
	 * @return the data at the specified location.
	 * @throws Exception if the data is bad or the filepath is invalid.
	 */
	public static Object load(String fileName) throws Exception {
		try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {

			return ois.readObject();
		}
	}

	/**
	 * Generates the list of US States in the form of an ObservableList.
	 *
	 * @return an ObservableList of Strings.
	 */
	public static ObservableList<String> getFiftyStates() {
		File fileStates = new File("Input Files/States.txt");
		LinkedList<String> states = new LinkedList<>();
		try {
			scannerStates = new Scanner(fileStates);
			while (scannerStates.hasNextLine())
				states.add(scannerStates.nextLine());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

//		FXCollections.observableList(states);

		return FXCollections.observableList(states);
	}
}