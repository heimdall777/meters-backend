package ovh.cerebrum.meters.dagger;

import com.coreoz.plume.conf.dagger.DaggerConfModule;
import dagger.Component;
import ovh.cerebrum.meters.RouteRegistry;
import ovh.cerebrum.meters.dao.impl.UserDAOImpl;

import javax.inject.Singleton;

@Singleton
@Component(modules = { DaggerConfModule.class, ApplicationModule.class })
public interface ApplicationComponent {

    UserDAOImpl getUserDao();

    RouteRegistry routeRegistry();
}
