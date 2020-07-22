package designPattern.Null;

public class RealOperation extends AbstractOperation {
    @Override
    void request() {
        System.out.println("do something");
    }
}

