package ThreadPool;

public class ThreadTask {
    Thread thread;
    InternalTask internalTask;

    public ThreadTask(Thread thread, InternalTask internalTask) {
        this.thread = thread;
        this.internalTask = internalTask;
    }
}
