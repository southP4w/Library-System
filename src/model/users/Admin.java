package model.users;

/**
 * The Admin class. Extends 'models.user.User'
 */
public class Admin extends User
{
	private static final long serialVersionUID = 0L;

	/**
	 * 3-param constructor for an Admin object
	 *
	 * @param username    The Admin username
	 * @param password    The Admin password
	 * @param contactInfo The Admin contact information
	 */
	public Admin(String username, String password, ContactInfo contactInfo) {
		super(username, password, contactInfo);
		this.setAdministratorStatus(true);
	}
}