package model.users;

public class Admin extends User
{
	private static final long serialVersionUID = 0L;

	public Admin(String username, String password, ContactInfo contactInfo) {
		super(username, password, contactInfo);
		this.setAdministratorStatus(true);
	}
}