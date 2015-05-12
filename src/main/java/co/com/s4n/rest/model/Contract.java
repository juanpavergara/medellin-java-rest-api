package co.com.s4n.rest.model;

import java.util.ArrayList;

public class Contract {

	private ArrayList<User> users;

	public Contract() {
		super();
		this.users = new ArrayList<User>();
	}

	public Contract(ArrayList<User> users) {
		super();
		this.users = users;
	}

	public ArrayList<User> getUsers() {
		return users;
	}
	
	public User getUser(int id){
		User userReturn = null;
		
		for(User user: users)
			if(user.getId()==id) userReturn = user;
		
		return userReturn;
	}
	
	
}
