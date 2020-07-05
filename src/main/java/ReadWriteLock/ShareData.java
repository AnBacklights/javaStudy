package ReadWriteLock;

import java.util.ArrayList;
import java.util.List;

public class ShareData {
    private final List<Character> container = new ArrayList<>();
    private final ReadWriteLock readWriteLock = ReadWriteLock.readWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();
    private final int length;

    public ShareData(int length) {
        this.length = length;
        for (int i = 0; i < length; i++) {
            container.add(i, 'c');
        }
    }

    public char[] read() throws InterruptedException {
        try {
            readLock.lock();
            char[] newbuffer = new char[length];
            for (int i = 0; i < length; i++) {
                newbuffer[i] = container.get(i);
            }
            slowly();
            return newbuffer;
        } finally {
            readLock.unlock();
        }
    }

    public void write(char c) throws InterruptedException {
        try {
            writeLock.lock();
            for (int i = 0; i < length; i++) {
                this.container.add(i, c);
            }
            slowly();
        }finally {
            writeLock.unlock();
        }
    }

    private void slowly() {
        /*try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
    public int getWaitWriters(){
        return readWriteLock.getWaitingWriters();
    }
    public int getWritingWriters(){
        return readWriteLock.getWritingWriters();
    }
    public int getReadingReaders(){
        return readWriteLock.getReadingReaders();
    }
}
