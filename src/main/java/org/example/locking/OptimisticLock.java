package org.example.locking;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class OptimisticLock {

    private static int count = 0;

    private static AtomicInteger atomicInteger = new AtomicInteger(count);
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(100);

        for (int i = 1; i<=10000; i++) {
            Future<Void> future = executorService.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    int newValue = count + 1;
                    incCount(newValue);
                    return null;
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);
        System.out.println(count);

    }

    private static void incCount(int value) {
        if (atomicInteger.compareAndSet(count, value)) {
            count++;
        } else {
            System.out.println("Not able to update for thread-" + Thread.currentThread().getName());
        }
    }

}
