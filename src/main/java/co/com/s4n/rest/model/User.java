package co.com.s4n.rest.model;

public class User {
	
	private String name;
	private String lastName;
	private int id;
	private String address;	
	
	public User(){
		
	}
	
	public User(String name, String lastName, int id, String address) {
		super();
		this.name = name;
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public int getId() {
		return id;
	}
	
	public String getLastName() {
		return lastName;
	}

	public String getAddress() {
		return address;
	}
	
	public String toString(){
		return "User = {name = "+this.name+", lastname = " +this.lastName+", id = "+ this.id + " address "+ this.address+"}";
	}

}
