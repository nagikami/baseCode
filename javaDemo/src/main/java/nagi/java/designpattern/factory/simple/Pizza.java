package nagi.java.designpattern.factory.simple;

public abstract class Pizza {
    private String name;

    public Pizza(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public abstract void prepare();
}
