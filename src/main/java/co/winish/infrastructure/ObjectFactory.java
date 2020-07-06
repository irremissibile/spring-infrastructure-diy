package co.winish.infrastructure;

import lombok.SneakyThrows;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ObjectFactory {

    private final ApplicationContext applicationContext;
    private List<ObjectConfigurator> objectConfigurators = new ArrayList<>();
    private List<ProxyConfigurator> proxyConfigurators = new ArrayList<>();


    @SneakyThrows
    public ObjectFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;

        for (Class<? extends ObjectConfigurator> confClass : applicationContext.getConcreteClassConfig().getReflections().getSubTypesOf(ObjectConfigurator.class))
            objectConfigurators.add(confClass.getDeclaredConstructor().newInstance());

        for (Class<? extends ProxyConfigurator> confClass : applicationContext.getConcreteClassConfig().getReflections().getSubTypesOf(ProxyConfigurator.class))
            proxyConfigurators.add(confClass.getDeclaredConstructor().newInstance());
    }


    @SneakyThrows
    public <T> T createObject(Class<T> implClass) {
        T t = implClass.getDeclaredConstructor().newInstance();

        // Give the object configurators a chance to modify the object
        configure(t);

        invokePostConstruct(implClass, t);

        // Give the proxy configurators a chance to create a proxy
        t = wrapWithProxy(implClass, t);

        return t;
    }


    private <T> void configure(T t) {
        objectConfigurators.forEach(objectConfigurator -> objectConfigurator.configure(t, applicationContext));
    }


    private <T> T wrapWithProxy(Class<T> implClass, T t) {
        for (ProxyConfigurator proxyConfigurator : proxyConfigurators)
            t = (T) proxyConfigurator.createProxy(t, implClass);

        return t;
    }


    private <T> void invokePostConstruct(Class<T> implClass, T t) throws InvocationTargetException, IllegalAccessException{
        for (Method method : implClass.getMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                method.invoke(t);
            }
        }
    }
}
