package co.winish.infrastructure;

import org.reflections.Reflections;

public interface ConcreteClassConfig {

    <T> Class<? extends T> getImplClass(Class<T> ifc);

    Reflections getReflections();

}
