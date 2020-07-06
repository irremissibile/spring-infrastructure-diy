package co.winish.example;

import co.winish.infrastructure.InjectObject;

public class ConsoleAnnouncer implements Announcer {

    @InjectObject
    private Recommendator recommendator;


    @Override
    public void announce(String message) {
        System.out.println(message);
        recommendator.recommend();
    }
}
