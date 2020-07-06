package co.winish.example;

import co.winish.infrastructure.InjectProperty;

public class RecommendatorImpl implements Recommendator {

    @InjectProperty
    private String alcohol;


    @Override
    public void recommend() {
        System.out.println(alcohol + " makes your life better");
    }
}
