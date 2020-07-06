package co.winish.infrastructure;

import java.util.Map;

public class Application {

    public static ApplicationContext run(String packageToScan, Map<Class, Class> ifcToImplClass) {
        ConcreteClassConfigImpl config = new ConcreteClassConfigImpl(packageToScan, ifcToImplClass);
        ApplicationContext applicationContext = new ApplicationContext(config);
        ObjectFactory objectFactory = new ObjectFactory(applicationContext);

        applicationContext.setFactory(objectFactory);

        return applicationContext;
    }
}
