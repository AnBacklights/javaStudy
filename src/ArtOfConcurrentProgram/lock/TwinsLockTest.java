package ArtOfConcurrentProgram.lock;

import ReadWriteLock.Lock;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class TwinsLockTest {
    @Test
    public void test(){
        final Lock  lock= new TwinsLock();
        class Worker extends Thread{
            @Override
            public void run() {
                while (true){
                    try {
                        lock.lock();
                        TimeUnit.SECONDS.sleep(1);
                        System.out.println(Thread.currentThread().getName());
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finally {
                        lock.unlock();
                    }
                }
            }
        }
        for (int i = 0; i < 10; i++) {
            Worker worker = new Worker();
            worker.setDaemon(true);
            worker.start();
        }
        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println();
        }
    }
}
