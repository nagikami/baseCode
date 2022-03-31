package nagi.java.designpattern.flyweight;

/**
 * 享元模式 运用共享技术有效地支持大量细粒度的对象
 * 常用于底层系统开发 例如数据库连接池
 * FlyWeight 抽象享元角色，产品抽象类，定义对象的内部和外部状态
 * ConcreteFlyWeight 具体享元角色
 * UnSharedConcreteFlyWeight 不可共享角色，一般不会出现在享元工厂
 * FlyWeightFactory 享元工厂类，用于构建一个池容器（集合），同时提供从池中获取对象方法
 */
public class Client {
    public static void main(String[] args) {
        WebsiteFactory websiteFactory = new WebsiteFactory();
        Website blog = websiteFactory.getWebsite("Blog");
        blog.use(new User("tom"));
        Website news = websiteFactory.getWebsite("News");
        news.use(new User("tony"));
    }
}
