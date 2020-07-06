package co.winish.example;

import co.winish.infrastructure.InjectObject;

public class CoronaDisinfector {

    @InjectObject
    private Announcer announcer;

    @InjectObject
    private Policeman policeman;


    public void start(Room room) {
        announcer.announce("The disinfection is starting!");
        policeman.makePeopleLeaveTheRoom();
        disinfect(room);
        announcer.announce("The disinfection is completed");
    }


    private void disinfect(Room room) {
        System.out.println("Dropping a nuclear bomb..... KABOOM");
    }
}
