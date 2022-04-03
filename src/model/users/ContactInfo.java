package model.users;

import java.io.Serializable;

/**
 * Class for a User's contact information
 */
public class ContactInfo implements Serializable
{
	private static final long serialVersionUID = 3L;

	private Name name;
	private String streetAddress, city, state, zipCode, phoneNumber;

	/**
	 * 6-parameter constructor for a ContactInfo object
	 *
	 * @param name          The Name on this ContactInfo object
	 * @param streetAddress A String to be used as the street address
	 * @param city          A String to be used as the name of the City
	 * @param state         A String to be used as the name of the State
	 * @param zipCode       A string to be used as the zip code
	 * @param phoneNumber   A String to be used as the phone number
	 */
	public ContactInfo(Name name, String streetAddress, String city, String state, String zipCode, String phoneNumber) {
		this.name = name;
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Single-parameter constructor for a ContactInfo object
	 *
	 * @param name The Name on this ContactInfo object
	 */
	public ContactInfo(Name name) {
		this.name = name;
	}

	/**
	 * Default constructor for a ContactInfo object
	 */
	public ContactInfo() {

	}

	/**
	 * Get the Name on this ContactInfo object.
	 *
	 * @return the Name on this ContactInfo object.
	 */
	public Name getName() {
		return name;
	}

	/**
	 * Set the name on this ContactInfo object.
	 *
	 * @param name The Name to be set to this ContactInfo object
	 */
	public void setName(Name name) {
		this.name = name;
	}

	/**
	 * Get the street address.
	 *
	 * @return the street address.
	 */
	public String getStreetAddress() {
		return streetAddress;
	}

	/**
	 * Set the street address.
	 *
	 * @param streetAddress String to be used as the street address
	 */
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	/**
	 * Get the name of the city on this ContactInfo object.
	 *
	 * @return the name of the city on this ContactInfo object.
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Set the city on this ContactInfo object.
	 *
	 * @param city The String to be used as the name of the city.
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Get the US state on this ContactInfo object.
	 *
	 * @return the US state on this ContactInfo object.
	 */
	public String getState() {
		return state;
	}

	/**
	 * Set the US state on this ContactInfo object.
	 *
	 * @param state String to be used as the US state for this ContactInfo object
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Get the zip code on this ContactInfo object.
	 *
	 * @return the zip code on this ContactInfo object.
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * Set the zip code on this ContactInfo object.
	 *
	 * @param zipCode String to be used as the zip code on this ContactInfo object
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * Get the phone number on this ContactInfo object.
	 *
	 * @return the phone number on this ContactInfo object.
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Set the phone number on this ContactInfo object.
	 *
	 * @param phoneNumber The String to be used as the phone number on this ContactInfo object.
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Custom toString() method to display this ContactInfo object's properties in the form of a String.
	 *
	 * @return a custom String displaying information about this ContactInfo object.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("User Contact Information:");
		sb.append("\nName: ").append(name);
		sb.append("\nAddress: ").append(streetAddress).append(", ").append(city).append(' ').append(state).append(", ").append(zipCode);
		sb.append("\nPhone Number: ").append(phoneNumber, 0, 3).append('/').append(phoneNumber, 3, 6).append('-')
				.append(phoneNumber, 6, 10);

		return sb.toString();
	}

	/**
	 * Testing method for the ContactInfo class
	 *
	 * @param args Command-line arguments
	 */
	public static void main(String[] args) {
		ContactInfo c = new ContactInfo(new Name("derp", "derpinson"), "555 anystreet", "south hell", "NY", "66666", "1234567890");

		System.out.println(c);
	}
}