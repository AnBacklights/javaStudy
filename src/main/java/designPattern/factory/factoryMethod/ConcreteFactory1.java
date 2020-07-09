package designPattern.factory.factoryMethod;

import designPattern.factory.simplefactory.ConcreteProduct1;
import designPattern.factory.simplefactory.Product;

public class ConcreteFactory1 extends Factory {
    @Override
    public Product factoryMethod() {
        return new ConcreteProduct1();
    }
}
