package ArtOfConcurrentProgram.dbpool;

import java.sql.Connection;
import java.util.LinkedList;

public class ConnectionPool {
    private LinkedList<Connection> pool = new LinkedList<>();

    public ConnectionPool(int initialSize) {
        if (initialSize > 0) {
            for (int i = 0; i < initialSize; i++) {
                pool.add(ConnectionDriver.creationConnection());
            }
        }
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            synchronized (pool) {
                pool.add(connection);
                pool.notifyAll();
            }
        }
    }

    public Connection fetchConnection(long mills) throws InterruptedException {
        synchronized (pool) {
            if (mills <= 0) {
                while (pool.isEmpty()) {
                    try {
                        pool.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return pool.removeFirst();
            } else {
                long remain = mills;
                long future = System.currentTimeMillis() + mills;
                while (pool.isEmpty() && remain > 0) {
                    pool.wait(mills);
                    remain = future - System.currentTimeMillis();
                }
                Connection result = null;
                if (!pool.isEmpty()) {
                    result = pool.removeFirst();
                }
                return result;
            }
        }

    }
}
