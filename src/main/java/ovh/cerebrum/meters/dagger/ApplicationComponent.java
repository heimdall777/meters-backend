package ovh.cerebrum.meters.dagger;

import com.coreoz.plume.conf.dagger.DaggerConfModule;
import dagger.Component;
import ovh.cerebrum.meters.dao.UserDAOImpl;

import javax.inject.Singleton;

@Singleton
@Component(modules = { DaggerConfModule.class, ApplicationModule.class })
public interface ApplicationComponent {
    //Config getConfig();

    UserDAOImpl getUserDao();

}
