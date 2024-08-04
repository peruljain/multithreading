package org.example.deadlock;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DeadLock {

    private static final ConcurrentHashMap<Integer, Lock> locks = new ConcurrentHashMap<>();
    public static void main(String[] args) throws InterruptedException {
        List<Record> records = List.of(new Record(1), new Record(2), new Record(3));
        ExecutorService executorService = Executors.newFixedThreadPool(6);

        for (int i = 1; i <= 6; i++) {
            String transactionName = "tranx-" + i;
            Future<Void> future =  executorService.submit(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    doWork(transactionName, records);
                    return null;
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(60, TimeUnit.SECONDS);

    }

    private static void doWork(String transactionName, List<Record> records) throws InterruptedException {
        Random random = new Random();

        while (true) {
            Record recordOne = records.get(random.nextInt(records.size()));
            Record recordTwo = records.get(random.nextInt(records.size()));

            if (recordOne.getId() == recordTwo.getId()) {
                continue;
            }

            Lock lockOne = getLock(recordOne.getId());
            Lock lockTwo = getLock(recordTwo.getId());

            acquireLock(transactionName, recordOne.getId(), lockOne);
            acquireLock(transactionName, recordTwo.getId(), lockTwo);

            Thread.sleep(1000);

            releaseLock(transactionName, recordOne.getId(), lockOne);
            releaseLock(transactionName, recordTwo.getId(), lockTwo);

            Thread.sleep(500);
        }

    }

    private static Lock getLock(Integer id) {
        locks.putIfAbsent(id, new ReentrantLock());
        return locks.get(id);
    }

    private static void acquireLock(String transactionName, Integer id, Lock lock) {
        System.out.println(String.format("trying lock for transactionName %s for id %s", transactionName, id));
        lock.lock();
        System.out.println(String.format("lock acquired by transactionName %s for id %s", transactionName, id));
    }

    private static void releaseLock(String transactionName, Integer id, Lock lock) {
        lock.unlock();
        System.out.println(String.format("lock released by transactionName %s for id %s", transactionName, id));
    }

}
