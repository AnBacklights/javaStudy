package ArtOfConcurrentProgram.lock;


import java.util.concurrent.locks.*;


public class Temp {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        lock.lock();
        try {

        } finally {
            lock.unlock();
        }
    }
}
