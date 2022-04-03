package model.users;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

/**
 * A container class that uses a HashMap to manage a large collection of User objects
 * <p>The username (String) is the Key, the User the Value.</p>
 */
public class UserBag implements Serializable
{
	private static final long serialVersionUID = 20L;
	private HashMap<String, User> hashMap;

	/**
	 * Default constructor for a UserBag object
	 */
	public UserBag() {
		hashMap = new HashMap<>(50_000);
	}

	/**
	 * Get the HashMap of Users.
	 *
	 * @return the HashMap of Users.
	 */
	public HashMap<String, User> getHashMap() {
		return hashMap;
	}

	/**
	 * Set the HashMap of this UserBag to a new one passed.
	 *
	 * @param hashMap A new HashMap to replace the current one
	 */
	public void setHashMap(HashMap<String, User> hashMap) {
		this.hashMap = hashMap;
	}

	/**
	 * Add a new User to the UserBag.
	 *
	 * @param user User to be inserted into the HashMap
	 */
	public void insert(User user) {
		hashMap.put(user.getUsername(), user);
	}

	/**
	 * Search for a User by their username.
	 *
	 * @param username String to be searched for
	 * @return the User with that username, if it exists.
	 */
	public User find(String username) {
		return hashMap.get(username);
	}

	/**
	 * Remove a User from the UserBag.
	 *
	 * @param username username of the User to be removed, if it exists.
	 */
	public void remove(String username) {
		hashMap.remove(username, find(username));
	}

	/**
	 * Check the HashMap for the String (username) passed.
	 *
	 * @param username username of the User whose existence within the HashMap is to be confirmed.
	 * @return true if that User exists in the HashMap, false otherwise.
	 */
	public boolean containsUsername(String username) {
		return hashMap.containsKey(username);
	}

	/**
	 * Get the Set of all usernames in the UserBag's HashMap.
	 *
	 * @return the Set of all usernames in the UserBag's HashMap.
	 */
	public Set<String> getAllAccounts() {
		return hashMap.keySet();
	}

	/**
	 * Get the number of elements in this HashMap.
	 *
	 * @return the number of elements in this HashMap.
	 */
	public int size() {
		return hashMap.size();
	}
}