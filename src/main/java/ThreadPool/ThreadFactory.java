package ThreadPool;

public interface ThreadFactory {
    Thread createThread(Runnable runnable);
}
