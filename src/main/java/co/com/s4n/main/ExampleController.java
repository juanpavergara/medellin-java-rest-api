package co.com.s4n.main;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.stereotype.*;

@Component
@Path("/health")
public class ExampleController {
    @GET
    @Produces("application/json")
    public String build() {
    	System.out.println("Ejecutando ExampleController.build");
        return  "Jersey: Up and running" ;
    }
}