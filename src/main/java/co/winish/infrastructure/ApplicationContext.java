package co.winish.infrastructure;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {

    @Setter
    private ObjectFactory factory;
    @Getter
    private final ConcreteClassConfig concreteClassConfig;

    private final Map<Class, Object> cachedSingletons = new ConcurrentHashMap<>();


    public ApplicationContext(ConcreteClassConfig concreteClassConfig) {
        this.concreteClassConfig = concreteClassConfig;
    }


    public <T> T getObject(Class<T> type) {
        // Maybe we already have such object?
        if (cachedSingletons.containsKey(type))
            return (T) cachedSingletons.get(type);


        // Ok, gotta create new instance
        Class<? extends T> toBeInstantiated = type;

        // Let's find out do we have the interface or the concrete implementation
        if (type.isInterface())
            toBeInstantiated = concreteClassConfig.getImplClass(type);

        // Create new instance
        T t = factory.createObject(toBeInstantiated);

        // Save if it's a singleton
        if (toBeInstantiated.isAnnotationPresent(Singleton.class))
            cachedSingletons.put(type, toBeInstantiated);

        return t;
    }
}
