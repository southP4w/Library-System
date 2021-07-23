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

public class Utilities
{
	private static Scanner titleScanner, isbnScanner, firstNameScanner, lastNameScanner, scannerStates;
	private static String[] firstNames, lastNames, titles, isbns;
	private static Name[] names;
	private static Name name;
	private static Author author;
	private static final int NUMBER_OF_BOOKS = 5_000;

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
		for (i = 0 ; i < names.length ;)
			names[i++] = new Name(firstNames[random.nextInt(firstNames.length)], lastNames[random.nextInt(lastNames.length)]);

		return names;
	}

	public static String[] emitTitles(String titleFileName) throws FileNotFoundException {
		File titleFile = new File(titleFileName);
		titleScanner = new Scanner(titleFile);
		titles = new String[NUMBER_OF_BOOKS];
		int i = 0;
		while (titleScanner.hasNextLine() && i < NUMBER_OF_BOOKS) {
			titles[i++] = titleScanner.nextLine(); //i++
		}

		return titles;
	}

	public static String[] emitISBNs(String isbnFileName) throws FileNotFoundException {
		File isbnFile = new File(isbnFileName);
		isbnScanner = new Scanner(isbnFile);
		isbns = new String[NUMBER_OF_BOOKS];
		int i = 0;
		while (isbnScanner.hasNextLine() && i < NUMBER_OF_BOOKS)
			isbns[i++] = isbnScanner.nextLine();

		return isbns;
	}

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

	public static double emitPrice() {
		Random rand = new Random();
		int dollars = 10 + rand.nextInt(190);
		double cents = rand.nextDouble();
		return dollars + cents;
	}

	public static boolean isInt(String string) {
		for (int c = 0 ; c < string.length() ; c++)
			if (!Character.isDigit(string.charAt(c)))
				return false;

		return true;
	}

	public static void save(Serializable data, String fileName) throws Exception {
		try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
			oos.writeObject(data);
		}
	}

	public static Object load(String fileName) throws Exception {
		try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {

			return ois.readObject();
		}
	}

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

		FXCollections.observableList(states);

		return FXCollections.observableList(states);
	}
}