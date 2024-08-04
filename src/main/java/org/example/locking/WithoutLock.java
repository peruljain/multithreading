package org.example.locking;

import java.util.concurrent.*;

public class WithoutLock {

    private static int count = 0;
    public static void main(String[] args) {


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

        System.out.println(count);

    }

    public static void incCount() {
        count++;
    }

}
