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
@Path("/users-hm")
public class HyperMediaController {
	
	@Context
	private UriInfo uriInfo;
	private UserRepo userRepo;

	public HyperMediaController(){
		super();
	}
	
	@Autowired	
	public HyperMediaController(UserRepo userRepo){				
		this.userRepo = userRepo;
		this.userRepo.getUsers();
	}
	
    @GET    
    @Produces("application/vnd.collection+json")
    public Response getUsers() {
    	List<Item> items = new ArrayList<Item>();    	
    	URI collectionUri = uriInfo.getAbsolutePath();
    	        
    	ArrayList<User> users = userRepo.getUsers();
    	
    	for(User u: users){
    		List<Property> props =  CollectionOps.<Property>of(Property.value("name", Optional.some("Name"), ValueFactory.createOptionalValue(u.getName())));
    		List<Link> links = Collections.<Link>emptyList();
    		URI uri = URI.create(collectionUri.toString()+"/"+u.getId());
    		items.add(Item.create(uri, props, links));    		
    	}
    	    	
        JsonNode collection = Collection.builder(collectionUri).addItems(items).build().asJson();        
        Response retResponse = Response.ok(collection).build();        
        return retResponse;
    }        
}
