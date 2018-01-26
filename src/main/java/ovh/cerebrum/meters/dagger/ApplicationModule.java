package ovh.cerebrum.meters.dagger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.typesafe.config.Config;
import dagger.Module;
import dagger.Provides;
import org.sql2o.Sql2o;
import org.sql2o.quirks.PostgresQuirks;
import ovh.cerebrum.meters.transformer.GsonTransformer;
import spark.ResponseTransformer;

import javax.inject.Singleton;

@Module
public class ApplicationModule {

    @Provides
    @Singleton
    public Sql2o providesSql2o(Config config) {
        return new Sql2o(config.getString("db.url"), config.getString("db.username"),
                config.getString("db.password"), new PostgresQuirks());
    }

    @Provides
    public Gson provideGson() {
        return new GsonBuilder().setPrettyPrinting().create();
    }

    @Provides
    public ResponseTransformer provideResponseTransformer(GsonTransformer gsonTransformer) {
        return gsonTransformer;
    }
}
