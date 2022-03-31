package nagi.java.designpattern.facade;

/**
 * 外观模式（过程模式） 组合子系统，为子系统的一组接口提供一个一致的界面 定义高层接口屏蔽子系统细节
 * 多用于系统分层
 * MyBatis中的Configuration创建MetaObject使用外观模式
 */
public class Client {
    public static void main(String[] args) {
        TheaterFacade theaterFacade = new TheaterFacade();
        theaterFacade.ready();
        theaterFacade.end();
    }
}
