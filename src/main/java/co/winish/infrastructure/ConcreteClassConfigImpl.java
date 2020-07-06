package co.winish.infrastructure;

import lombok.Getter;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

public class ConcreteClassConfigImpl implements ConcreteClassConfig {

    @Getter
    private Reflections reflections;
    private Map<Class, Class> ifcToConcreteImplClass;

    public ConcreteClassConfigImpl(String packageToScan, Map<Class, Class> ifcToConcreteImplClass) {
        this.ifcToConcreteImplClass = ifcToConcreteImplClass;
        this.reflections = new Reflections(packageToScan);
    }


    @Override
    public <T> Class<? extends T> getImplClass(Class<T> ifc) {
        return ifcToConcreteImplClass.computeIfAbsent(ifc, this::findNewClass);
    }


    private <T> Class<? extends T> findNewClass(Class<T> ifc) {
        Set<Class<? extends T>> classes = reflections.getSubTypesOf(ifc);
        if (classes.size() == 0)
            throw new RuntimeException(ifc + " has no available implementations");
        else if (classes.size() > 1)
            throw new RuntimeException(ifc + " has several available implementations");

        return classes.iterator().next();
    }
}
