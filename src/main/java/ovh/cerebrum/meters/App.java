package ovh.cerebrum.meters;

import ovh.cerebrum.meters.dagger.ApplicationComponent;
import ovh.cerebrum.meters.dagger.DaggerApplicationComponent;

public class App {

    private ApplicationComponent applicationComponent;

    public static void main(String[] args) {
        new App().run();
    }

    private void initializeDagger() {
        applicationComponent = DaggerApplicationComponent.create();

    }

    private void run() {
        initializeDagger();
        registerRoutes();
    }

    private void registerRoutes() {
        applicationComponent.routeRegistry().registerRoutes();
    }

}
