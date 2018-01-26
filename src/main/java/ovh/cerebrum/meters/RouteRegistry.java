package ovh.cerebrum.meters;

import com.typesafe.config.Config;
import ovh.cerebrum.meters.domain.User;
import spark.ResponseTransformer;

import javax.inject.Inject;
import javax.inject.Singleton;

import static spark.Spark.get;
import static spark.Spark.port;

@Singleton
public class RouteRegistry {

    private ResponseTransformer responseTransformer;
    private Config config;

    @Inject
    public RouteRegistry(ResponseTransformer responseTransformer, Config config) {
        this.responseTransformer = responseTransformer;
        this.config = config;
    }

    public void registerRoutes(){
        port(config.getInt("server.port"));

        get("/hello", "application/json", (request, response) -> new User("Test","Test","test"), responseTransformer);
    }
}
