package ArtOfConcurrentProgram.lock;

import ReadWriteLock.Lock;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class TwinsLock implements Lock {
    private final Sync sync = new Sync(2);

    private static final class Sync extends AbstractQueuedSynchronizer {
        public Sync(int count) {
            if (count <= 0) {
                throw new IllegalArgumentException("");
            }
            setState(count);
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            for (; ; ) {
                int current = getState();
                int newCount = current + arg;
                if (compareAndSetState(current, newCount)) {
                    return true;
                }
            }
        }

        @Override
        protected int tryAcquireShared(int arg) {
            for (; ; ) {
                int current = getState();
                int newCount = current - arg;
                if (newCount < 0 || compareAndSetState(current, newCount)) {
                    return newCount;
                }
            }
        }
    }

    @Override
    public void lock() {
        sync.acquireShared(1);
    }

    @Override
    public void unlock() {
        sync.releaseShared(1);
    }
}
