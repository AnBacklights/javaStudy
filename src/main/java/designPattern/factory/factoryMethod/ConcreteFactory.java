package designPattern.factory.factoryMethod;

import designPattern.factory.simplefactory.ConcreteProduct;
import designPattern.factory.simplefactory.Product;

public class ConcreteFactory extends Factory {
    @Override
    public Product factoryMethod() {
        return new ConcreteProduct();
    }
}
