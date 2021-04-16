package 软件架构设计的七大原则.接口隔离原则;


/**
 * 接口隔离原则  有点类似单一职责的思想
 * 1、一个类对一类的依赖应该建立在最小的接口之上。
 * 2、建立单一接口，不要建立庞大臃肿的接口。
 * 3、尽量细化接口，接口中的方法尽量少（不是越少越好，一定要适度）。
 * 接口隔离原则符合我们常说的高内聚低耦合的设计思想，从而使得类具有很好的可读性、可扩展性和可维护性。
 * 我们在设计接口的时候，要多花时间去思考，要考虑业务模型，包括以后有可能发生变更的地方还要做一些预判。
 * 所以，对于抽象，对业务模型的理解是非常重要的。

 */
public interface IAnimal {
    void eat();
    void fly();
    void swim();
}

//明显鸟不会游泳
class Bird implements IAnimal{

    @Override
    public void eat() {

    }

    @Override
    public void fly() {

    }

    @Override
    public void swim() {

    }
}
//狗不会飞
class Dog implements  IAnimal{

    @Override
    public void eat() {

    }

    @Override
    public void fly() {

    }

    @Override
    public void swim() {

    }
}
//所以把这三个模型提取出来
interface IEatAnimal{
    void eat();
}
interface IFlyAnimal{
    void fiy();
}
interface IswimAnimal{
    void swim();
}

class duck implements IEatAnimal,IFlyAnimal{

    @Override
    public void eat() {

    }

    @Override
    public void fiy() {

    }
}
