package co.winish.infrastructure;

public interface ProxyConfigurator {

    Object createProxy(Object object, Class implClass);

}
