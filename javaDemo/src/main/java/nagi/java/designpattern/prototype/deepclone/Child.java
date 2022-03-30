package nagi.java.designpattern.prototype.deepclone;

import java.io.Serializable;

public class Child implements Serializable, Cloneable {

    private String name = "child";

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
