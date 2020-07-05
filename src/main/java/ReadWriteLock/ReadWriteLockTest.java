package ReadWriteLock;

public class ReadWriteLockTest {
    private final static String text = "Thisistheexampleforreadwritelock";

    public static void main(String[] args) {
        final ShareData shareData = new ShareData(50);
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int index = 0; index < text.length(); index++) {
                    try {
                        char c = text.charAt(index);
                        shareData.write(c);
                        System.out.println(Thread.currentThread() + " write " + c);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                while (true){
                    try{
                        System.out.println(Thread.currentThread() + " read " + new String(shareData.read()));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        while (true){
            System.out.println("waiting Writers : " + shareData.getWaitWriters());
            System.out.println("writing Writers : " + shareData.getWritingWriters());
            System.out.println("reading Readers : " + shareData.getReadingReaders());
        }
    }
}
