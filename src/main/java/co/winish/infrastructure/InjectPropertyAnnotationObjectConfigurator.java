package co.winish.infrastructure;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.Properties;

public class InjectPropertyAnnotationObjectConfigurator implements ObjectConfigurator {

    private Properties properties;


    @SneakyThrows
    public InjectPropertyAnnotationObjectConfigurator() {
        properties = new Properties();
        properties.load(ClassLoader.getSystemClassLoader().getResourceAsStream("application.properties"));
    }


    @Override
    @SneakyThrows
    public void configure(Object object, ApplicationContext applicationContext) {
        Class<?> implClass = object.getClass();
        for (Field field : implClass.getDeclaredFields()) {
            InjectProperty annotation = field.getAnnotation(InjectProperty.class);
            if (annotation != null) {
                String value = annotation.value().isEmpty() ? properties.getProperty(field.getName()) : properties.getProperty(annotation.value());

                field.setAccessible(true);
                field.set(object, value);
            }
        }
    }
}
