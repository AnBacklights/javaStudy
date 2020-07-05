package ReadWriteLock;

public interface Lock {
    void lock() throws InterruptedException;
    void unlock();
}
