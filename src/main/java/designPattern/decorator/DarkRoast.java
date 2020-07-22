package designPattern.decorator;

/**
 * 深度烘焙咖啡
 */
public class DarkRoast implements Beverage {
    @Override
    public double cost() {
        return 1;
    }
}
