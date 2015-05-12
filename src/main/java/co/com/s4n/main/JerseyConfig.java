package co.com.s4n.main;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(ExampleController.class);
        register(UserController.class);
        register(HyperMediaController.class);
    }
}
