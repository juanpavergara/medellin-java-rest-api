package co.com.s4n.main.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.test.context.junit4.*;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;
import co.com.s4n.main.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port=9000")
public class ExampleControllerTest {

    private RestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void health() {
        ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:9000/health", String.class);
        System.out.println(entity.getBody());

        assertTrue(entity.getStatusCode().is2xxSuccessful());
        assertTrue(entity.getBody().equals("Jersey: Up and running"));
    }
}