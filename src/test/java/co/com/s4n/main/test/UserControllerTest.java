package co.com.s4n.main.test;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import org.springframework.boot.test.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.test.context.junit4.*;
import org.springframework.test.context.web.WebAppConfiguration;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;

import co.com.s4n.main.Application;
import co.com.s4n.rest.model.User;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes ={Application.class, UserControllerMockProvider.class})
@WebAppConfiguration
@IntegrationTest("server.port=9000")
public class UserControllerTest {
		
    private RestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void getUserById() {
        try{	
	        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:9000/users/1", String.class);	        
	        ObjectMapper mapper = new ObjectMapper();
	        User userRet = mapper.readValue(entity.getBody(), User.class);
	        assertTrue(entity.getStatusCode().is2xxSuccessful());
	        assertTrue(userRet.getName().equals("JUAN PABLO"));
        }catch(JsonParseException e){
        	e.printStackTrace();
        }catch(JsonMappingException e){
        	e.printStackTrace();
        }catch(IOException e){
        	e.printStackTrace();
        }
    }
    
    @Test
    public void getUsers() {
        try{	
	        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:9000/users", String.class);	        
	        ObjectMapper mapper = new ObjectMapper();	         
	        
	        JavaType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, User.class);

	        ArrayList<User> usersRet = mapper.readValue(entity.getBody(), type);
	        assertTrue(entity.getStatusCode().is2xxSuccessful());
	        assertTrue(((User)usersRet.get(1)).getId() == 2);
	        assertTrue(((User)usersRet.get(1)).getName().equals("SHIN I"));
	        
        }catch(JsonParseException e){
        	e.printStackTrace();
        }catch(JsonMappingException e){
        	e.printStackTrace();
        }catch(IOException e){
        	e.printStackTrace();
        }
    }
    
    @Test
    public void createUser() {
        try{	
        	        	
        	User body = new User("TITA", "VILLARRAGA", 31282216, "CRA 83 A # 33 99");
    		HttpHeaders headers = new HttpHeaders();    		
    		headers.setContentType(MediaType.APPLICATION_JSON);    		
    		HttpEntity<User> entity = new HttpEntity<>(body, headers);
    		
    		URI responseURI = restTemplate.postForLocation("http://localhost:9000/users", entity, String.class , 1);	        
	        ObjectMapper mapper = new ObjectMapper();	        	        
	        ResponseEntity<String> responseEntity2 = restTemplate.getForEntity(responseURI, String.class);
	        	        
	        User userRet = mapper.readValue(responseEntity2.getBody(), User.class);
	        
	        assertTrue(userRet.getName().equals("TITA"));
	        
        }catch(Exception e){
        	e.printStackTrace();
        }
    }
    
    @Test
    public void getUsersHypermedia() {
        try{	
	        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:9000/users-hm", String.class);	        
	        ObjectMapper mapper = new ObjectMapper();	
	        
	        JsonNode usersRet = mapper.readValue(entity.getBody(), JsonNode.class);	        
	        
	        System.out.println(usersRet);
	       	        
	        
	        JsonNode items = usersRet.findValue("items");	        
	        List<String> urls = new ArrayList<String>();	        	     
	        Iterator<JsonNode> it = items.elements();
	        
	        while(it.hasNext()){
	        	JsonNode next = (JsonNode)it.next();
	        	
	        	JsonNode data = next.get("href");
	        	String url = data.asText();
	        	
	        	urls.add(url);
	        }
	        
	        assertTrue(urls.get(0).equals("http://localhost:9000/users-hm/1"));
	        assertTrue(entity.getStatusCode().is2xxSuccessful());
	        
        }catch(Exception e){
        	e.printStackTrace();
        }
    }
       
}
