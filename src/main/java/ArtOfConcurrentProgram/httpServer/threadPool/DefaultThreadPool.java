package ArtOfConcurrentProgram.httpServer.threadPool;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class DefaultThreadPool<Job extends Runnable> implements ThreadPool {
    private static final int MAX_WORKER_NUMBERS = 10;
    private static final int DEFAULT_WORKER_NUMBER = 5;
    private static final int MIN_WORKER_NUMBER = 1;
    private final LinkedList<Job> jobs = new LinkedList<>();
    private final List<Worker> workers = Collections.synchronizedList(new ArrayList<>());
    private int workerNum = DEFAULT_WORKER_NUMBER;
    private AtomicLong threadNum = new AtomicLong();


    public DefaultThreadPool() {
    }

    public DefaultThreadPool(int workerNum) {
        this.workerNum = workerNum > MAX_WORKER_NUMBERS ? MAX_WORKER_NUMBERS : workerNum < MIN_WORKER_NUMBER ? MIN_WORKER_NUMBER : workerNum;
        initializeWorkers(this.workerNum);
    }

    private void initializeWorkers(int workerNum) {
        for (int i = 0; i < workerNum; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            Thread thread = new Thread(worker, "ThreadPool-Worker-" + threadNum.incrementAndGet());
            thread.start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        if (runnable != null) {
            synchronized (jobs) {
                jobs.add((Job) runnable);
                jobs.notifyAll();
            }
        }
    }

    @Override
    public void shutdown() {
        workers.forEach(Worker::shutdown);
    }

    @Override
    public void addWorkers(int num) {
        synchronized (jobs) {
            if (num + this.workerNum > MAX_WORKER_NUMBERS) {
                num = MAX_WORKER_NUMBERS - this.workerNum;
            }
            initializeWorkers(num);
            this.workerNum += num;
        }
    }

    @Override
    public void removeWorker(int num) {
        synchronized (jobs) {
            if (num >= this.workerNum) {
                throw new IllegalArgumentException("Beyond WorkNum");
            }
            int count = 0;
            while (count < num) {
                Worker worker = workers.get(count);
                if (workers.remove(worker)) {
                    worker.shutdown();
                    count++;
                }
            }
            this.workerNum -= count;
        }
    }

    @Override
    public int getJobSize() {
        return jobs.size();
    }

    class Worker implements Runnable {
        private volatile boolean running = true;

        @Override
        public void run() {
            while (running) {
                Job job;
                synchronized (jobs) {
                    while (jobs.isEmpty()) {
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            e.printStackTrace();
                        }
                    }
                    job = jobs.removeFirst();
                }
                if (job != null) {
                    try {
                        job.run();
                    } catch (Exception e) {
                        //
                    }
                }
            }
        }

        public void shutdown() {
            running = false;
        }
    }
}
