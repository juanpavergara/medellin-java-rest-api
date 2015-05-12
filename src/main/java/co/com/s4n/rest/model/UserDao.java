package co.com.s4n.rest.model;

import java.util.ArrayList;

public interface UserDao {
	public User getUserById(int id);
	public ArrayList<User> getUsers();
	public boolean addUser(User user);
}
