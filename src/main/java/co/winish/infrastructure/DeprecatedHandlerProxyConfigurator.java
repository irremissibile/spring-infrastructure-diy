package co.winish.infrastructure;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DeprecatedHandlerProxyConfigurator implements ProxyConfigurator {

    @Override
    public Object createProxy(Object object, Class implClass) {
        boolean shouldCreatedProxy = false;
        for (Method method : implClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Deprecated.class)) {
                shouldCreatedProxy = true;
                break;
            }
        }

        if (shouldCreatedProxy)
            return Enhancer.create(implClass, (InvocationHandler) (proxy, method, args) -> getInvocationHandlerLogic(object, method, args));
        else
            return object;
    }


    private Object getInvocationHandlerLogic(Object object, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        if (method.isAnnotationPresent(Deprecated.class))
            System.out.println("Using deprecated method!");

        return method.invoke(object, args);
    }
}
