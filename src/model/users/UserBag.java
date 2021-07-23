package model.users;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

public class UserBag implements Serializable
{
	private static final long serialVersionUID = 20L;
	private HashMap<String, User> hashMap;

	public UserBag() {
		hashMap = new HashMap<>(50_000);
	}

	public HashMap<String, User> getHashMap() { return hashMap; }

	public void setHashMap(HashMap<String, User> hashMap) { this.hashMap = hashMap; }

	public void insert(User user) {
		hashMap.put(user.getUsername(), user);
	}

	public User find(String username) {
		return hashMap.get(username);
	}

	public void remove(String username) {
		hashMap.remove(username, find(username));
	}

	public boolean containsUsername(String username) {
		return hashMap.containsKey(username);
	}

	public Set<String> getAllAccounts() { return hashMap.keySet(); }

	public int size() {
		return hashMap.size();
	}
}