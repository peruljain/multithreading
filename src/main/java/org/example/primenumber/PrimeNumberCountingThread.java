package org.example.primenumber;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class PrimeNumberCountingThread {

    private static AtomicInteger count = new AtomicInteger(0);

    public static void main(String[] args) {
        long n = 100 * 1000000;
        int threadSize = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadSize);
        long batchSize = n / threadSize;

        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < threadSize; i++) {
            final long start = i * batchSize + 1;
            final long end = (i + 1) * batchSize;

            Future<?> future = executorService.submit(() -> countPrimeNumbers(start, end));
            futures.add(future);
        }

        for (Future<?> future : futures) {
            try {
                future.get();  // Wait for each thread to complete
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();

        System.out.println("Total prime numbers: " + count.get());
    }

    private static void countPrimeNumbers(long start, long end) {
        long startTime = System.currentTimeMillis();
        while (start <= end) {
            if (isPrimeNumber(start)) {
                count.incrementAndGet();
            }
            start++;
        }
        long endTime = System.currentTimeMillis();
        System.out.println(Thread.currentThread().getName() + " took time " + String.valueOf(endTime - startTime) + " ms");
    }

    private static boolean isPrimeNumber(long n) {
        if (n == 1) {
            return false;
        }

        for (long i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }
}
