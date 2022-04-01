package nagi.java.designpattern.interpreter;

import java.util.HashMap;

/**
 * 抽象类表达式，通过HashMap键值对获取变量的值
 */
public abstract class Expression {
    public abstract int interpreter(HashMap<String, Integer> var);
}
