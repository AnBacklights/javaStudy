package designPattern.factory.factoryMethod;

import designPattern.factory.simplefactory.ConcreteProduct2;
import designPattern.factory.simplefactory.Product;

public class ConcreteFactory2 extends Factory {
    @Override
    public Product factoryMethod() {
        return new ConcreteProduct2();
    }
}
