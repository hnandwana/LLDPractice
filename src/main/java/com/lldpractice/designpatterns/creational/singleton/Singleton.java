package com.lldpractice.designpatterns.creational.singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Singleton {
    public static void main(String[] args) throws InterruptedException {
        // --- 1. Test Singleton Instance ---
        System.out.println("--- Singleton Verification Test ---");
        Logger logger1 = Logger.getInstance();
        Logger logger2 = Logger.getInstance();

        logger1.info("This is the first logger instance.");
        logger2.warning("This is the second logger instance.");

        if (logger1 == logger2) {
            System.out.println("SUCCESS: Both logger1 and logger2 are the same instance.\n");
        } else {
            System.out.println("FAILURE: logger1 and logger2 are different instances.\n");
        }

        // --- 2. Test Thread Safety (Concurrency) ---
        System.out.println("--- Concurrency Test ---");
        System.out.println("Starting 5 threads to log simultaneously...");

        // Using an ExecutorService is a modern way to manage threads.
        // Use a fixed thread pool for true concurrency to demonstrate the race condition.
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 1; i <= 5; i++) {
            final int threadNum = i;
            executor.submit(() -> {
                Logger threadLogger = Logger.getInstance();
                // Log a longer message multiple times to increase the chance of interleaving.
                for (int j = 0; j < 5; j++) {
                    threadLogger.info("This is a longer message from Thread " + threadNum + " to demonstrate potential interleaving issues. Iteration: " + j);
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS); // Wait for tasks to finish
        System.out.println("\nAll threads have finished logging.");
    }
}
