package org.example.primenumber;

public class PrimeNumberCounting {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        long count = 0;
        long n = 100*1000000;
        for (long i = 1; i < n; i++) {
            if (isPrimeNumber(i)) {
                count++;
            }
        }
        long endTime = System.currentTimeMillis();

        System.out.println("Prime Numbers Count " + count);

        System.out.println("Took time " + String.valueOf(endTime - startTime) + " ms");
    }

    public static boolean isPrimeNumber(long n) {

        if (n==1) {
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