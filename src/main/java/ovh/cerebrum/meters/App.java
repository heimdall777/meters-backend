package ovh.cerebrum.meters;

import static spark.Spark.get;

public class App {



    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello world");
    }
}
