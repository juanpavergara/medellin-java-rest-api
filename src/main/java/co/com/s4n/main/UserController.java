package co.com.s4n.main;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.com.s4n.rest.model.User;
import co.com.s4n.rest.model.UserRepo;
import net.hamnaberg.json.*;
import net.hamnaberg.json.Error;
import net.hamnaberg.json.parser.CollectionParser;
import net.hamnaberg.funclite.*;

@Component
@Path("/users")
public class UserController {
	
	@Context
	private UriInfo uriInfo;
	private UserRepo userRepo;

	public UserController(){
		super();
	}
	
	@Autowired	
	public UserController(UserRepo userRepo){				
		this.userRepo = userRepo;
		this.userRepo.getUsers();
	}
	
    @GET
    // Sin anotaciones adicionales se atiende el request del @Path de la clase /users
    @Produces("application/json")
    public Response getUsers() {   
    	try{
	    	ObjectMapper mapper = new ObjectMapper();    		    	
	    	String userStr = new String(mapper.writeValueAsBytes(userRepo.getUsers()));	   	    	
	    	Response retResponse = Response.ok(userStr).build();
	        return retResponse;
    	}catch(JsonProcessingException e){
    		return Response.serverError().entity("Error serializing list of all users").build();
    	}        
    }
    
    @GET   
    @Path("/{id}")
    @Produces("application/json")
    public Response getUserById(@PathParam("id") int id) {   
    	try{
	    	ObjectMapper mapper = new ObjectMapper();    	
	    	User user = userRepo.getUserById(id);	    	
	    	String userStr = new String(mapper.writeValueAsBytes(user));	    	
	    	Response retResponse = Response.ok(userStr).build();
	        return retResponse;
    	}catch(JsonProcessingException e){
    		return Response.serverError().entity("Error serializing User("+id+")").build();
    	}
    }
    
    @POST   
    @Produces("application/json")
    public Response createUser(String userStr) {
    	
    	try{
	    	ObjectMapper mapper = new ObjectMapper();    	
	    	User user = mapper.readValue(userStr, User.class); 
	    	boolean resAdd = userRepo.addUser(user);
	    	Response retResponse = null;
	    	    	
	    	if(resAdd){	    		
	    		retResponse = Response.created(new URI(uriInfo.getAbsolutePath().toString()+"/"+user.getId())).build();	    	
	    	}
        else{
        		retResponse = Response.status(Status.CONFLICT).build();	        	
        }
	    	return retResponse;
	        		
    	}catch(JsonProcessingException e){
    		return Response.serverError().entity("Error creating user "+ e.getMessage()).build();    	
        }catch(IOException e){
        	return Response.serverError().entity("Error creating user "+ e.getMessage()).build();    	
        }catch(Exception e){
        	return Response.serverError().entity("Error creating user "+ e.getMessage()).build();    	
        }
    }
    
}
