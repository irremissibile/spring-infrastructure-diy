package co.winish.infrastructure;

import lombok.SneakyThrows;

import java.lang.reflect.Field;

public class InjectObjectAnnotationObjectConfigurator implements ObjectConfigurator {

    @Override
    @SneakyThrows
    public void configure(Object object, ApplicationContext applicationContext) {
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectObject.class)) {
                Object toBeInjected = applicationContext.getObject(field.getType());

                field.setAccessible(true);
                field.set(object, toBeInjected);
            }
        }
    }
}
