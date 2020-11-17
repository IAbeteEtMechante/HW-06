package space.harbour.java.hw6;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DiningPhilosophers {

    static class Philosopher implements Runnable {
        private final String name;
        private final Object leftChopstick;
        private final Object rightChopstick;

        public Philosopher(String name, Object leftChopstick, Object rightChopstick) {
            this.name = name;
            this.leftChopstick = leftChopstick;
            this.rightChopstick = rightChopstick;
        }

        private void think() throws InterruptedException {
            System.out.println(name + " thinking...");
            Thread.sleep((long) (Math.random() * 10));
        }

        private void eat() throws InterruptedException {
            System.out.println(name + " eating...");
            Thread.sleep((long) (Math.random() * 10));
        }

        @Override
        public void run() {
            try {
                while (true) {
                    //think();
                    synchronized (leftChopstick) {
                        System.out.println(name + " picking left chopstick");
                        synchronized (rightChopstick) {
                            System.out.println(name + " picking right chopstick");
                            eat();
                        }
                        System.out.println(name + " putting down right chopstick");
                    }
                    System.out.println(name + " putting down left chopstick");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }

    public static void main(String... args) {
        final int N = 5;

        ExecutorService table = Executors.newFixedThreadPool(N);

        Philosopher[] philosophers = new Philosopher[N];
        Object[] chopsticks = new Object[N];
        for (int i = 0; i < N; i++) {
            chopsticks[i] = new Object();
        }

        for (int i = 0; i < N; i++) {
            Object leftChopstick = chopsticks[i];
            Object rightChopstick = chopsticks[(i + 1) % N];

            //philosophers[i] = new Philosopher("Philosopher "
            //        + (i + 1), leftChopstick, rightChopstick);

            if (i == 0) {
                philosophers[i] = new Philosopher("Philosopher "
                        + (i + 1), rightChopstick, leftChopstick);
            } else {
                philosophers[i] = new Philosopher("Philosopher "
                        + (i + 1), leftChopstick, rightChopstick);
            }
            table.execute(philosophers[i]);
        }
    }
}
