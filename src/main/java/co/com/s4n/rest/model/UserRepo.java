package co.com.s4n.rest.model;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

public class UserRepo {
	
	
	UserDao dao;
	
	public UserRepo(UserDao dao){
		this.dao = dao;
	}
	public User getUserById(int id){
		return dao.getUserById(id);
	}
	
	public ArrayList<User> getUsers(){
		return dao.getUsers();
	}
	
	public boolean addUser(User user){
		return dao.addUser(user);		
	}
}
