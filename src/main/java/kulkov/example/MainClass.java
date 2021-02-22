package kulkov.example;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 * Перенести приведенный ниже код в новый проект, где мы организуем гонки.
 *
 * Все участники должны стартовать одновременно, несмотря на разное время  подготовки.
 * В тоннель не может одновременно заехать больше половины участников (условность).
 *
 * Попробуйте все это синхронизировать.
 *
 * Первый участник, пересекший финишную черту, объявляется победителем (в момент пересечения этой самой черты).
 * Победитель должен быть только один (ситуация с 0 или 2+ победителями недопустима).
 * Когда все завершат гонку, нужно выдать объявление об окончании.
 *
 * Можно корректировать классы (в том числе конструктор машин) и добавлять объекты классов из пакета java.util.concurrent.
 */
public class MainClass {

    // Количество машин.
    public static final int CARS_COUNT = 4;
    public final static CountDownLatch cdl = new CountDownLatch(CARS_COUNT);
    CyclicBarrier cb = new CyclicBarrier(2);
    public final static Semaphore smp = new Semaphore(CARS_COUNT / 2);

    public static void main(String[] args) {

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");

        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];

//        CountDownLatch cd = new CountDownLatch(CARS_COUNT);

        // Инициализация массива машин.
        for (int i = 0; i < cars.length; i++) {

            /**
             * Массив машин.
             *
             */
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10));
        }

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");

        // Запуск потоков для каждой машины.
        // Для одновременного запуска потоков можем использовать синхронизацию по CountDownLatch.
        for (int i = 0; i < cars.length; i++) {

            new Thread(cars[i]).start();
        }

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
}

