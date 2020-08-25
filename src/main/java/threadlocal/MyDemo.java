package threadlocal;

public class MyDemo {
    private String content;

    ThreadLocal<String> t1 = new ThreadLocal<>();

    public void setContent(String content) {
        t1.set(content);
        this.content = content;
    }

    public String getContent() {
        return t1.get();
    }

    public static void main(String[] args) {
        MyDemo demo = new MyDemo();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
//                synchronized (demo) {
                demo.setContent(Thread.currentThread().getName() + "的数据");
                System.out.println("-------------");
                System.out.println(Thread.currentThread().getName() + "---->" + demo.getContent());
//                }

            }, "线程" + i).start();
        }
    }
}
