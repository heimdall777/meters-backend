package ovh.cerebrum.meters.dagger;

import com.typesafe.config.Config;
import dagger.Module;
import dagger.Provides;
import org.sql2o.Sql2o;
import org.sql2o.quirks.PostgresQuirks;

import javax.inject.Inject;
import javax.inject.Singleton;

@Module
public class ApplicationModule {

    @Provides
    @Singleton
    @Inject
    public Sql2o providesSql2o(Config config) {
        return new Sql2o(config.getString("db.url"), config.getString("db.username"),
                config.getString("db.password"), new PostgresQuirks());
    }
}
