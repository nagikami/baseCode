package nagi.java.trick;

public enum StrategyEnum {

    S1("s1") {
        @Override
        void strategy() {
            System.out.println("s1");
        }
    },
    S2("s2") {
        @Override
        void strategy() {
            System.out.println("s2");
        }
    };

    StrategyEnum(String name) {
        this.name = name;
    }

    private String name;

    abstract void strategy();
}

class Test {
    public static void main(String[] args) {
        StrategyEnum.S1.strategy();
        StrategyEnum.S2.strategy();
    }
}
