package ovh.cerebrum.meters.dagger;

import com.coreoz.plume.conf.dagger.DaggerConfModule;
import com.typesafe.config.Config;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = DaggerConfModule.class)
public interface ApplicationComponent {
    Config getConfig();
}
