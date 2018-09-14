package com.tibco.ep.samples.java;

public class Main  {

    public static void main(String []  args) {

        //
        // Application thread
        //
        Thread t = new Thread() {
            public void run() {
                System.out.println("hello from the thread");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                }
            }
        };
        t.run();

        //
        // Wait for the thread to return before exiting main
        //
        try {
             t.join();
        } catch (InterruptedException ex) {
             // handle interrupted exception
        }

        //
        // Returning from main - causes the JVM to exit
        //
        System.out.println("returning from main");
    }

}
