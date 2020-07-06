package co.winish.example;

import co.winish.infrastructure.Application;
import co.winish.infrastructure.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<Class, Class> ifcToImplClass = new HashMap<>();
        ifcToImplClass.put(Policeman.class, CasualPoliceman.class);

        ApplicationContext applicationContext = Application.run("co.winish", ifcToImplClass);
        CoronaDisinfector disinfector = applicationContext.getObject(CoronaDisinfector.class);
        disinfector.start(new Room());
    }
}
