package ovh.cerebrum.meters.transformer;

import com.google.gson.Gson;
import spark.ResponseTransformer;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GsonTransformer implements ResponseTransformer {

    private final Gson gson;

    @Inject
    public GsonTransformer(Gson gson) {
        this.gson = gson;
    }

    @Override
    public String render(Object model) {
        return gson.toJson(model);
    }
}
