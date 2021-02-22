package kulkov.example;

import java.util.concurrent.CountDownLatch;

import static kulkov.example.MainClass.cdl;

/**
 * Класс машины.
 *
 * CARS_COUNT - статическое поле, инициализируется по умолчанию "0", инкременируется при конструктуировании нового объекта.
 *
 */
public class Car implements Runnable {

    private static int CARS_COUNT;
    private Race race;
    private int speed;

    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }

    // Constructor.
    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {

            System.out.println(this.name + " готовится");

            // Время на подготовку машины.
            Thread.sleep(500 + (int)(Math.random() * 800));

            System.out.println(this.name + " готов");
            cdl.countDown();

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {

            race.getStages().get(i).go(this);

        }
        System.out.println(this.name + " Finish!");
    }
}
