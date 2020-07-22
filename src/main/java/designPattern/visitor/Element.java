package designPattern.visitor;

public interface Element {
    void accept(Visitor visitor);
}
