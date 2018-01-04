package ovh.cerebrum.meters;

import com.coreoz.plume.conf.dagger.DaggerConfModule;
import dagger.Component;

import static spark.Spark.get;

@Component(modules = DaggerConfModule.class)
public class App {

    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello world");
    }
}
