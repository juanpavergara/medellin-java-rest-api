package co.com.s4n.main.test;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.com.s4n.rest.model.User;
import co.com.s4n.rest.model.UserDao;
import co.com.s4n.rest.model.UserRepo;

@Configuration
public class UserControllerMockProvider {
	
	@Autowired
	ArrayList<User> bd;
	
	@Bean
	UserDao produceDaoMock() {
		return new UserDao(){			
			@Override
			public ArrayList<User> getUsers() {				
				return bd;
			}
			
			@Override
			public User getUserById(int id) {
				User userRet = null;
				for(User user: getUsers()) if(user.getId() == id) userRet = user;
				return userRet;
			}
			
			@Override
			public boolean addUser(User user) {
				return bd.add(user);				
			}
		};
	}	
	
	@Bean
	UserRepo produceRepo(){
		return new UserRepo(produceDaoMock());
	}
	
	@Bean
	ArrayList<User> produceBD(){
		ArrayList<User> users = new ArrayList<User>();
		users.add(new User("JUAN PABLO", "VERGARA", 1, "DIRECCION DE JUAN PABLO"));
		users.add(new User("SHIN I", "WANG", 2, "DIRECCION DE SAMMY"));
		users.add(new User("LINA MARIA", "VERGARA", 3, "DIRECCION DE LINA"));
		users.add(new User("SHIN NI", "WANG", 4, "DIRECCION DE SHIN NI"));
		users.add(new User("LI HSIU", "WANG", 5, "DIRECCION DE LI"));
		return users;
	}
}
