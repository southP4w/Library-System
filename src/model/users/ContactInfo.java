package model.users;

import java.io.Serializable;

public class ContactInfo implements Serializable
{
	private static final long serialVersionUID = 3L;
	private Name name;
	private String streetAddress, city, state, zipCode, phoneNumber;

	public ContactInfo() {

	}

	public ContactInfo(Name name) {
		this.name = name;
	}

	public ContactInfo(Name name, String streetAddress, String city, String state, String zipCode, String phoneNumber) {
		this.name = name;
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.phoneNumber = phoneNumber;
	}

	public Name getName() { return name; }

	public void setName(Name name) { this.name = name; }

	public String getStreetAddress() { return streetAddress; }

	public void setStreetAddress(String streetAddress) { this.streetAddress = streetAddress; }

	public String getCity() { return city; }

	public void setCity(String city) { this.city = city; }

	public String getState() { return state; }

	public void setState(String state) { this.state = state; }

	public String getZipCode() { return zipCode; }

	public void setZipCode(String zipCode) { this.zipCode = zipCode; }

	public String getPhoneNumber() { return phoneNumber; }

	public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("User Contact Information:");
		sb.append("\nName: ").append(name);
		sb.append("\nAddress: ").append(streetAddress).append(", ").append(city).append(' ').append(state).append(", ").append(zipCode);
		sb.append("\nPhone Number: ").append(phoneNumber.substring(0, 3)).append('/').append(phoneNumber.substring(3, 6)).append('-')
				.append(phoneNumber.substring(6, 10));

		return sb.toString();
	}

	public static void main(String[] args) {
		ContactInfo c = new ContactInfo(new Name("derp", "derpinson"), "555 anystreet", "south hell", "NY", "66666", "1234567890");

		System.out.println(c);
	}
}