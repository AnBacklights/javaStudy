package ArtOfConcurrentProgram;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProducerAndCusumer {
    static List<Integer> list = new ArrayList<>();

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                while (true) {
                    synchronized (list) {
                        while (list.size() == 10){
                            try {
                                list.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        int r = new Random().nextInt(100);
                        list.add(r);
                        System.out.println(Thread.currentThread().getName()+" product "+r);
                        list.notifyAll();
                    }
                }
            }, "Productor-" + i).start();
        }
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                while (true) {
                    synchronized (list) {
                        while (list.isEmpty()){
                            try {
                                list.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        Integer first = list.remove(0);
                        System.out.println(Thread.currentThread().getName()+" custom "+first);
                        list.notifyAll();
                    }
                }
            }, "Customer-" + i).start();
        }
    }
}
