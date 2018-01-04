package ovh.cerebrum.meters;

import ovh.cerebrum.meters.dagger.ApplicationComponent;
import ovh.cerebrum.meters.dagger.DaggerApplicationComponent;

import static spark.Spark.get;

public class App {


    private ApplicationComponent applicationComponent;

    public static void main(String[] args) {
        new App().run();

    }

    private void initializeDagger() {
        applicationComponent = DaggerApplicationComponent.create();

    }

    private void run(){
        initializeDagger();
        System.out.println("config = " + applicationComponent.getConfig().getString("foo.bar"));
        get("/hello", (req, res) -> "Hello world");
    }

}
