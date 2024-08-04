package org.example.locking;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class WithPessimisticLock {

    private static int count = 0;
    private static Lock lock = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(100);

        for (int i = 0; i<1000000; i++) {
            Future<Void> f = executorService.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    incCount();
                    return null;
                }
            });
        }

        executorService.shutdown();
        boolean isCompleted = executorService.awaitTermination(1, TimeUnit.SECONDS);

        System.out.println(count);
        System.out.println(isCompleted);

    }

    public synchronized static void incCount() {
        lock.lock();
        count++;
        lock.unlock();
    }
}
